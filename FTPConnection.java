package www.unt.seniordesignappv2;

import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;

public class FTPConnection {
    private static final String TAG = "FTPConnection";
    public FTPClient mFTPClient = null;

    String host = "47.184.93.246";
    String username = "FTP-Server";
    String password = "4d8foj@";
    int port = 21;

    public boolean ftpConnect() {
        try {
            mFTPClient = new FTPClient();
            mFTPClient.connect(host, port);

            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) { // now check the reply code, if positive mean connection success
                boolean status = mFTPClient.login(username, password); // login using username & password
                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();
                Log.d(TAG, "Connected to host (FTP function) " + status);
                return status;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error: could not connect to host " + host);
            e.printStackTrace(); //error display
            System.out.println(e);
        }
        return false;
    }

    public boolean ftpDisconnect() { //FTP Server Disconnect
        try {
            mFTPClient.logout();
            mFTPClient.disconnect();
            Log.d(TAG,"Disconnected from ftp server.");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error occurred while disconnecting from ftp server.");
        }
        return false;
    }

    public boolean ftpDownload(String srcFilePath, String desFilePath) { //Downloading the file in order to read it
        boolean status = false;
        try {
            FileOutputStream desFileStream = new FileOutputStream(desFilePath);

            status = mFTPClient.retrieveFile(srcFilePath, desFileStream);
            desFileStream.close();
            Log.d(TAG, "Download succeeded");

            return status;
        } catch (Exception e) {
            e.printStackTrace(); //error display
            System.out.println(e);
            Log.d(TAG, "Download failed");
        }
        return status;
    }
}