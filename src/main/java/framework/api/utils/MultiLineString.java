package framework.api.utils;

import java.io.*;

public class MultiLineString {

    public static String multiLineString() {
        try {
            StackTraceElement element = new RuntimeException().getStackTrace()[1];
            String name = "src/main/java/" + element.getClassName().replace('.', '/') + ".java";
            String s = convertStreamToString(new FileInputStream(name), element.getLineNumber());
            return s.substring(s.indexOf("/*") + 2, s.indexOf("*/"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private static String convertStreamToString(InputStream is, int lineNum) {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        int i = 1;
        try {
            while ((line = reader.readLine()) != null) {
                if (i++ >= lineNum) {
                    sb.append(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
