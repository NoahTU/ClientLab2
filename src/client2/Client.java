/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client2;

/**
 *
 * @author Noah
 */
/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;*/

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
/**
 * Trivial client for the date server.
 */
public class Client {

    /**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or hostname of a host running
     * the date server, then connects to it and displays the date that
     * it serves.
     */
    /*public static void main(String[] args) throws IOException {
        String serverAddress = JOptionPane.showInputDialog(
            "Enter IP Address of a machine that is\n" +
            "running the date service on port 9090:");
        Socket s = new Socket(serverAddress, 1125);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
    }*/
    public static void main(String argv[])
      {
          
          System.out.println("Make a key: ");
                    Scanner scanner = new Scanner(System.in);
                    String switc = scanner.nextLine();
          
           Mac sha512_HMAC = null;
        String r = null;
        String key =  switc;
	   try{
               
               byte [] byteKey = key.getBytes("UTF-8");
                final String HMAC_SHA256 = "HmacSHA512";
                sha512_HMAC = Mac.getInstance(HMAC_SHA256);      
                SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA256);
                sha512_HMAC.init(keySpec);
                byte [] mac_data = sha512_HMAC.
                doFinal("My message".getBytes("UTF-8"));
                //result = Base64.encode(mac_data);
                r = bytesToHex(mac_data);
                System.out.println(r);
               
               
		    Socket socketClient= new Socket("localhost",5555);
		    System.out.println("Client: "+"Connection Established");
 
		    BufferedReader reader = 
		    		new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
 
		    BufferedWriter writer= 
	        		new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
		    String serverMsg;
                    
                                serverMsg = reader.readLine();
				System.out.println("Client: " + serverMsg);
                                System.out.println("STOP");
                                String serverMsg2 = reader.readLine();
				System.out.println("Client: " + serverMsg2);
			
                     
                    /*System.out.println("Enter Message: ");
                    //Scanner scanner = new Scanner(System.in);
                    switc = scanner.nextLine();*/
                    
		    writer.write(switc+"\r\n");
                    writer.flush();
                    writer.write(r+"\r\n");
                    writer.flush();
                    /*while((serverMsg = reader.readLine()) != null){
				System.out.println("Client: " + serverMsg);
			}*/
                    serverMsg = reader.readLine();
				System.out.println("Client: " + serverMsg);
                                
                    System.out.println("Enter Message 2: ");            
                    switc = scanner.nextLine();
                    
                    mac_data = sha512_HMAC.
                doFinal(switc.getBytes("UTF-8"));
                //result = Base64.encode(mac_data);
                r = bytesToHex(mac_data);
                    
                    
		    writer.write(switc+"\r\n");
                    writer.flush();
                    writer.write(r+"\r\n");
                    writer.flush();
			
                    
                    
                   
                    //echo
                    serverMsg = reader.readLine();
				System.out.println("Client: " + serverMsg);
                    
                    //message
                    String Msg = reader.readLine();
                    
                    //verify
                    String v = reader.readLine();
				System.out.println("Client: " + v);
                    
                    //System.out.println("TEST: " + Msg);
                    
                    /*serverMsg = reader.readLine();
				System.out.println("Client: " + serverMsg);
                                serverMsg = reader.readLine();
				System.out.println("Client: " + serverMsg);
                                serverMsg = reader.readLine();
				System.out.println("Client: " + serverMsg);*/
                    
                    
                    mac_data = sha512_HMAC.
                //doFinal("Hi A, this is B!\n".getBytes("UTF-8"));
               doFinal((Msg+"\n").getBytes("UTF-8"));
                //result = Base64.encode(mac_data);
                
                /*if (!"Hi A, this is B!\n".equals(Msg)){
                    System.out.println(Msg);
                    System.out.println("Hi A, this is B!\n");
                }*/
                
                r = bytesToHex(mac_data);
                System.out.println("Our verification: " + r);    
                if (r.equals(v)){
				System.out.println("Client: " + Msg+"\n");}
                
                else{
                    System.out.println("Last Message was not verified properly");
                }
                    
                    /*while((serverMsg = reader.readLine()) != null){
				System.out.println("Client: " + serverMsg);
			}*/
 
	   }catch(Exception e){e.printStackTrace();}
           
      }
    
    public static String bytesToHex(byte[] bytes) {
    final  char[] hexArray = "0123456789ABCDEF".toCharArray();
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = hexArray[v >>> 4];
        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
}
}