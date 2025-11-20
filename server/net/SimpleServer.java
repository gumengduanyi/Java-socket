package server.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import server.parser.Parser;

public class SimpleServer {
    public void run(){
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
            
            in = new DataInputStream(socketS.getInputStream());
            out = new DataOutputStream(socketS.getOutputStream());
            
            requestStr = in.readUTF();
            System.out.println("server get message:" + requestStr);
            
            responseStr = getResponseStr(requestStr);
            out.writeUTF(responseStr);
                        
            in.close();
            out.close();
            socketS.close();
            System.out.println("Disconnected!");
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
    private String getResponseStr(String requestStr) {
        Parser par = new Parser();
        return par.run(requestStr );
    }
}
