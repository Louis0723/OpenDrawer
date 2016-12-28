package lursun.opendrawer;

import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by admin on 2016/9/9.
 */
public class TCP extends Thread {
    static ServerSocket tcp;
    Handler h;
    String ip="";
    TCP(String ip){
        this.ip=ip;
    }
    @Override
    public void run() {
        try {

            Socket s = new Socket(ip, 9100);
            byte[] b = {(byte) 0x10, (byte) 0x14, (byte) 0x01, (byte) 0x00,(byte) 0x01};
            OutputStream os = s.getOutputStream();
            os.write(b);
            os.close();
            s.close();
        } catch (Exception e) {
            e = e;
        }
        super.run();
    }
}
