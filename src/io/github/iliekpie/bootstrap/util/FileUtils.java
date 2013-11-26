package io.github.iliekpie.bootstrap.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    /**
     * Returns a file's contents as a String.
     * @param filename The location of the file, including the filename
     * @return (String) The contents of the file
     */
    public static String loadFile(String filename) {
        String text = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            text = sb.toString();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
