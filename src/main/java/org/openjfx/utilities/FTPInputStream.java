package org.openjfx.utilities;

import org.apache.commons.net.ftp.FTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FTPInputStream extends FTPStream {
    private InputStream is;

    public FTPInputStream(String host, String username, String password, int port, String file_path) throws Exception {
            super(host, username, password, port);
            String file_url;
            this.client.enterLocalPassiveMode();
            this.client.setFileType(FTP.BINARY_FILE_TYPE);
            file_url = URLUtils.get_FTP_URL(host, username, password, file_path);
            this.is = client.retrieveFileStream(file_url);
    }

    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(this.is));
    }
    public InputStream getInputStream() {
        return this.is;
    }

    public void close()throws Exception{
        if(this.is != null) {
            this.is.close();
        }
    }
    @Override
    protected void finalize() throws Throwable {
        this.is.close();
    }
}
