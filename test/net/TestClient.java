package test.net;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String args[]) {
        Socket socketC = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        String ip = "127.0.0.1";//server IP
        int port = 4330; //server port
        String requestStr, responseStr;		
		
        try {
            socketC = new Socket(ip, port);
            System.out.println("connecting OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }		
        try {
            out = new DataOutputStream(socketC.getOutputStream());
            in = new DataInputStream(socketC.getInputStream());
		
            requestStr = "zhangsan";
            out.writeUTF(requestStr);
            System.out.println("send message:" + requestStr);
		
            responseStr = in.readUTF();
            System.out.println("receive message:" + responseStr);
	
            in.close();
            out.close();
            socketC.close();
            System.out.println("Disconnected!");		
        } catch (Exception e) {
            e.printStackTrace();
        }		
    }
}
