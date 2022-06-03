package org.reftch.parser;

import java.util.Arrays;
import java.util.Optional;

public enum XmlTag {

    Sect1("sect1"),
    Title("title"),
    Paragraph("para"),
    GuiLabel("guilabel"),
    GuiMenu("guimenu"),
    GuiSubMenu("guisubmenu"),
    Note("note"),
    ListItem("listitem"),
    ItemizedList("itemizedlist"),
    ProductName("productname"),
    Primary("primary"),
    Secondary("secondary"),
    ImageObject("imageobject"),
    ImageData("imagedata"),
    Unknown("unknown");

    private String tag;

    XmlTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static Optional<XmlTag> get(String tag) {
        return Arrays.stream(XmlTag.values())
                .filter(e -> e.tag.equals(tag))
                .findFirst();
    }
}
