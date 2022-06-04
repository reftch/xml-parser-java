package org.reftch.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {

    private static final Logger LOG = LoggerFactory.getLogger(XmlHandler.class);

    private final static String NEW_LINE = "\n";

    private final StringBuilder markdown = new StringBuilder();

    private XmlTag parentTag = XmlTag.Unknown;
    private XmlTag currentTag = XmlTag.Unknown;

    private final boolean isLogging;
    private int depth = 1;

    public XmlHandler(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        var tag = XmlTag.get(qName);
        if (tag.isPresent()) {
            var presented = tag.get();
            var line = switch (presented) {
                case Title -> this.getTitle();
                case Paragraph -> this.getParagraph();
                case ImageData -> this.getImage(attributes);
                case GuiLabel -> " *";
                case GuiMenu -> " *";
                case GuiSubMenu -> " *-> ";
                case Note -> "\n::: tip";
                case ItemizedList -> "\n";
                case ProductName -> " **";
                default -> "";
            };

            this.markdown.append(line);
            if (this.isLogging) {
                LOG.info(String.format("%1$" + this.depth + "s", "+" + qName));
                this.depth += 4;
            }
            this.parentTag = this.currentTag;
            this.currentTag = presented;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        var tag = XmlTag.get(qName);
        if (tag.isPresent()) {
            var presented = tag.get();
            var line = switch (presented) {
                case Title -> XmlHandler.NEW_LINE;
                case Paragraph -> XmlHandler.NEW_LINE;
                case GuiLabel -> "* ";
                case GuiMenu -> "* ";
                case GuiSubMenu -> "* ";
                case Note -> ":::\n\n";
                case ItemizedList -> "\n";
                case ProductName -> "** ";
                default -> "";
            };

            this.markdown.append(line);
            if (this.isLogging) {
                this.depth -= 4;
                LOG.info(String.format("%1$" + this.depth + "s", "-" + qName));
            }

            this.currentTag = presented;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        var data = new String(ch, start, length).replaceAll("\n", " ");
        if (data.trim().length() > 0) {
            if (!this.currentTag.equals(XmlTag.Primary) && !this.currentTag.equals(XmlTag.Secondary)) {
                if (this.isLogging) {
                    LOG.info(String.format("%1$" + this.depth + "s", data));
                }

                var line = this.parentTag.equals(XmlTag.ListItem) && this.currentTag.equals(XmlTag.Paragraph)
                        ? "+ " + data.trim()
                        : data.trim();
                this.markdown.append(line);
            }
        }
    }

    private String getTitle() {
        return this.currentTag.equals(XmlTag.Sect1) ? "\n\n### " : "## ";
    }

    private String getParagraph() {
        return !this.currentTag.equals(XmlTag.ListItem) ? XmlHandler.NEW_LINE : "";
    }

    private String getImage(Attributes attributes) {
        var image = attributes.getValue("fileref");
        return image != null ? "\n![An image](/" + image + ")" : "";
    }

    public String getMarkdown() {
        return this.markdown.toString();
    }

    public void reset() {
        this.markdown.setLength(0);
    }
}
