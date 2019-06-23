package com.dax.api.io;

import org.rspeer.script.Script;
import org.rspeer.ui.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class FileManager {

    public static String getWorkingDir(String fileName) {
        String file = String.format("%s%sdax%s%s", Script.getDataDirectory(), File.separator, File.separator, fileName);
        new File(file).getParentFile().mkdirs();
        return file;
    }

    public static String getWorkingDir(String fileName, String category) {
        String file = String.format("%s%sdax%s%s%s%s", Script.getDataDirectory(), File.separator, File.separator, category, File.separator, fileName);
        new File(file).getParentFile().mkdirs();
        return file;
    }

    public static String open(String fileName) {
        try {
            return Files.lines(Paths.get(getWorkingDir(fileName))).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            Log.log(Level.SEVERE, "FileManager", "Failed to open " + fileName);
            return null;
        }
    }

    public static boolean writeToFile(File file, String contents) {
        try {
            Files.write(Paths.get(file.getPath()), contents.getBytes());
            return true;
        } catch (IOException e) {
            Log.log(Level.SEVERE, "FileManager", "Failed to write " + file.getName());
            return false;
        }
    }

    public static boolean writeToFile(String fileName, String contents) {
        try {
            Files.write(Paths.get(getWorkingDir(fileName)), contents.getBytes());
            return true;
        } catch (IOException e) {
            Log.log(Level.SEVERE, "FileManager", "Failed to write " + fileName);
            return false;
        }
    }

}
