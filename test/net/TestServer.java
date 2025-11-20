package test.net;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String args[]) {
        ServerSocket server = null;
        Socket socketS = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        InetAddress iaddress = null;
        int port = 4330;
        String requestStr, responseStr;		
        try {
            server = new ServerSocket(port);
            System.out.println("Server started,waiting for request...");
        } catch (IOException e) 	{ 
            e.printStackTrace();
        }		 
        try {
            socketS = server.accept();
            iaddress = socketS.getInetAddress();
            System.out.println("get client request,client address:" + iaddress);
			
            in = new DataInputStream(socketS.getInputStream());
            out = new DataOutputStream(socketS.getOutputStream());
			
            requestStr = in.readUTF();
            System.out.println("server get message:" + requestStr);
			
            responseStr = "A01-78";
            out.writeUTF(responseStr);
            System.out.println("server response:" + responseStr);
						
            in.close();
            out.close();
            socketS.close();
            System.out.println("Disconnected!");
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
}
