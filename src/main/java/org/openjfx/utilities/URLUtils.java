package org.openjfx.utilities;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class URLUtils{

    private static final String FTP_URL_FORMAT = "ftp://%s:%s@%s/%s;type=i";

    public static String get_FTP_URL(String host, String username, String pass, String path){
        return String.format(FTP_URL_FORMAT, username, pass, host, path);
    }

    public static boolean exists(String URLName){
        boolean result = false;
        URL url;
        try {
            url = new URL(URLName);
            InputStream inputStream = url.openStream();
            System.out.println("SUCCESS");
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    static public void copy(String from, String to){
        final int BUFFER_SIZE = 4096;
        try {
            FTPInputStream i = new FTPInputStream(from);
            FTPOutputStream o = new FTPOutputStream(to);
            OutputStream os = o.getOutputStream();
            InputStream is = i.getInputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
