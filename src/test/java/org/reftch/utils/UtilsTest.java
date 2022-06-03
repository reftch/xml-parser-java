package org.reftch.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;

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
        var files = new HashSet<File>();
        Utils.traverseDirectory(".*\\.java", "", files);
        assertEquals(files.size(), 0);

        Utils.traverseDirectory(".*\\.java", null, files);
        assertEquals(files.size(), 0);

        Utils.traverseDirectory(null, null, files);
        assertEquals(files.size(), 0);

        Utils.traverseDirectory(null, ".", files);
        assertEquals(files.size(), 0);

        Utils.traverseDirectory(".*\\.java", ".", files);
        assertTrue(files.size() > 0);
    }

}
