package com.compart.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlHandlerTest {

    private SAXParser saxParser;
    private XmlHandler xmlHandler;

    @Before
    public void setUp() throws Exception {
        var factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
        xmlHandler = new XmlHandler(false);
    }

    @Test
    public void shouldParsePrimaryTitle() throws ParserConfigurationException, SAXException, IOException {
        // primary title
        saxParser.parse(new InputSource(new StringReader("<title>Test</title>")), xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), "## Test\n");
    }

    @Test
    public void shouldParseSecondaryTitle() throws ParserConfigurationException, SAXException, IOException {
        // secondary title
        saxParser.parse(
                new InputSource(new StringReader("<sect1 xml:id=\"create_application\"><title>Test</title></sect1>")),
                xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), "### Test\n");
    }

    @Test
    public void shouldParseParagraph() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(
                new InputSource(new StringReader("<para>Proceed as follows to create a new application.</para>")),
                xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), "\nProceed as follows to create a new application.\n");
    }

    @Test
    public void shouldParseParagraphInItems() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(new InputSource(new StringReader("<listitem><para>Command</para></listitem>")), xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), "+ Command\n");
    }

    @Test
    public void shouldParseGuiLabel() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(new InputSource(new StringReader("<guilabel>Applications</guilabel>")), xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), " *Applications* ");
    }

    @Test
    public void shouldParseGuiMenu() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(
                new InputSource(new StringReader("<menuchoice><guimenu>Process Configuration</guimenu></menuchoice>")),
                xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), " *Process Configuration* ");
    }

    @Test
    public void shouldParseGuiSubMenu() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(new InputSource(new StringReader(
                "<menuchoice><guisubmenu>General Settings</guisubmenu> <guisubmenu>Definitions</guisubmenu></menuchoice>")),
                xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), " *-> General Settings*  *-> Definitions* ");
    }

    @Test
    public void shouldParseNote() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(new InputSource(new StringReader("<note><para>Test</para></note>")), xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), "\n::: tip\nTest\n:::\n\n");
    }

    @Test
    public void shouldParseProductName() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(new InputSource(new StringReader("<productname>Test</productname>")), xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), " **Test** ");
    }

    @Test
    public void shouldParseImageData() throws ParserConfigurationException, SAXException, IOException {
        // validate
        saxParser.parse(new InputSource(new StringReader("<imageobject><imagedata fileref=\"images/btn_add.png\"/></imageobject>")), xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), "\n![An image](/images/btn_add.png)");

        xmlHandler.reset();
        saxParser.parse(new InputSource(new StringReader("<imageobject><imagedata/></imageobject>")), xmlHandler);
        assertEquals(xmlHandler.getMarkdown(), "");
    }

}

