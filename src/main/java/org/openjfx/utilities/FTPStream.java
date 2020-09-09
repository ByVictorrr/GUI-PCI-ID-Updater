package org.openjfx.utilities;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.net.SocketException;

import static org.openjfx.adapters.ConnectionAdapter.*;


public class FTPStream {

    protected FTPClient client;
    protected String host, username, password;
    protected int port;

    public FTPStream(String host, String username, String password, int port){
       this.client = new FTPClient();
       this.host = host;
       this.username = username;
       this.password = password;
       this.port = port;
    }
    public FTPClient connect(){
        try {
            this.client.connect(this.host, this.port);
            return this.client;
        }catch (IOException i){
            i.printStackTrace();
        }
        return null;
    }
    public FTPClient login() throws IOException {
        if(this.client.login(this.username, this.password)){
            return this.client;
        }
        return null;
    }

    public FTPClient getClient() {
        return client;
    }

    public int findLatestVersion(String parentDir)throws Exception{
        FTPFile[] dirs = client.listDirectories(parentDir);
        // Step 1 - Get the newest version
        int largest = -1;
        int curr;
        for (FTPFile dir: dirs){
            String dir_name = dir.getName();
            curr = Integer.parseInt(dir_name.substring(1, dir_name.length()));
            if( curr > largest ){
                largest = curr;
            }
        }
        return largest;
    }
}
