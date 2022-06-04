package org.reftch.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Utils {

    public static String removeExtension(String fileName) {
        return fileName == null ? ""
                : fileName.indexOf(".") > 0 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
    }

    public static void saveFile(String fileName, String rows) throws IOException {
        final var fileWriter = new FileWriter(Utils.removeExtension(fileName) + ".md", false);
        fileWriter.write(rows);
        fileWriter.close();
    }

    public static void traverseDirectory(final String pattern, final String directory, Set<File> files) {
        if (pattern != null && directory != null && files != null) {
            final var dir = new File(directory);
            if (dir.exists()) {
                for (final var f : dir.listFiles()) {
                    if (f.isDirectory()) {
                        traverseDirectory(pattern, f.getAbsolutePath(), files);
                    }
                    if (f.isFile() && f.getName().matches(pattern)) {
                        files.add(f);
                    }
                }
            }
        }
    }

}
