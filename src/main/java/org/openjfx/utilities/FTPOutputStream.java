package org.openjfx.utilities;


import org.apache.commons.net.ftp.FTP;
import org.openjfx.models.Device;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;


public class FTPOutputStream extends FTPStream{
    private OutputStream os;
    public FTPOutputStream(String host, String username, String password, int port, String file_path) throws Exception{
        super(host, username, password, port);
        String file_url;
        this.client.enterLocalPassiveMode();
        this.client.setFileType(FTP.ASCII_FILE_TYPE);
        this.client.setFileTransferMode(FTP.ASCII_FILE_TYPE);
        file_url = URLUtils.get_FTP_URL(host, username, password, file_path);
        this.os = client.storeFileStream(file_url);
    }

    public OutputStream getOutputStream() {
        return os;
    }
    public OutputStreamWriter getWriter(){
       return new OutputStreamWriter(this.os);
    }
    public void write(StringBuilder chLog)throws Exception{
        OutputStreamWriter writer = this.getWriter();
        writer.write(chLog.toString());
        writer.close();
    }

    public void write(FTPInputStream r, Queue<Device> devices) throws Exception{
        String line;
        Queue<String> prevLines = new LinkedList<>();
        long numLine = 1;
        Device curr = devices.poll();
        BufferedReader reader = r.getReader();
        OutputStreamWriter writer = this.getWriter();
        while ((line = reader.readLine()) != null) {
            if(curr != null && curr.getLineNum() == numLine){
                writer.write(curr.formatOutput() + "\n");
                Logger.addEntry(curr);
                curr = devices.poll();
                prevLines.add(line);
            }else {
                while(!prevLines.isEmpty()) {
                    writer.write(prevLines.poll() + "\n");
                }
                writer.write(line + "\n");
            }
            numLine++;
        }
        writer.close();

    }
    public void close() throws Exception{
        // step 1 - close
        this.os.close();

    }

    @Override
    protected void finalize() throws Throwable {
        this.os.close();
    }
}
