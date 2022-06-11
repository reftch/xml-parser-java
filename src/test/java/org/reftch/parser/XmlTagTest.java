package org.reftch.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XmlTagTest {

    @Test
    public void shouldFindEnum() {
        var tag = XmlTag.get("title");
        assertEquals(XmlTag.Title, tag);
    }
    
    @Test
    public void shouldNotFindEnum() {
        var tag = XmlTag.get("title1");
        assertEquals(XmlTag.Unknown, tag);

        tag = XmlTag.get("");
        assertEquals(XmlTag.Unknown, tag);

        tag = XmlTag.get(null);
        assertEquals(XmlTag.Unknown, tag);
    }
    
}
