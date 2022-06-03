package com.compart.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void shouldRemoveFileExtension() {
        assertEquals(Utils.removeExtension("test.xml"), "test");
        assertEquals(Utils.removeExtension("test"), "test");
        assertEquals(Utils.removeExtension(""), "");
        assertEquals(Utils.removeExtension(null), "");
    }

    @Test
    public void shouldListFiles() {
        assertNull(Utils.getFiles(""));
        assertNull(Utils.getFiles(null));
        assertNotNull(Utils.getFiles("."));
        assertTrue(Utils.getFiles(".").size() > 0);
    }

}
