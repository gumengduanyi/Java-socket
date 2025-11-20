package client.net;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SimpleClient {
    private String ip = "127.0.0.1";//server IP
    private int port = 4330; //server port
    private String requestStr = "";
    
    public SimpleClient(String ip, int port, String requestStr){
        this.ip = ip;
        this.port = port;
        this.requestStr = requestStr;
    }

    public String run(){
        Socket socketC = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        String responseStr = "None";	
		
        try {
            socketC = new Socket(ip, port);
            System.out.println("connecting OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }		
        try {
            out = new DataOutputStream(socketC.getOutputStream());
            in = new DataInputStream(socketC.getInputStream());
		
            out.writeUTF(requestStr);
		
            responseStr = in.readUTF();
            System.out.println("receive message:" + responseStr);
	
            in.close();
            out.close();
            socketC.close();
            System.out.println("Disconnected!");		
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return responseStr;
    }
}
