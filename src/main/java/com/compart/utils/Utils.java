package com.compart.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static String removeExtension(String fileName) {
        return fileName == null ? ""
                : fileName.indexOf(".") > 0 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
    }

    public static void saveFile(String fileName, String rows) throws IOException {
        Writer fileWriter = new FileWriter(Utils.removeExtension(fileName) + ".md", false);
        fileWriter.write(rows);
        fileWriter.close();
    }

    public static Set<File> getFiles(String directory) {
        if (directory != null) {
            var dir = new File(directory);
            if (dir.exists()) {
                return Stream.of(dir.listFiles())
                        .filter(file -> !file.isDirectory())
                        .collect(Collectors.toSet());
            }
        }
        return null;
    }

}
