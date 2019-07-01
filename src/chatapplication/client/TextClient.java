/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.client;

import chatapplication.client.gui.ClientGUI;
import chatapplication.structs.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author popescda
 */
public class TextClient extends Thread{

    JTextArea writeArea;
    Socket clientSocket;
    ClientGUI clientGUI = null;
    
    public TextClient(ClientGUI _ClientGUI,JTextArea _writeArea){
        writeArea = _writeArea;
        clientGUI = _ClientGUI;
    }
    public static void main(String[] args) {

        //TextClient tClient = new TextClient();
        //tClient.run();

    }
    public void run(){
        

        Scanner scanner = new Scanner(System.in);
        String user = "";

        while (user.equals("")) {
            System.out.println("Introdu un nume de utilizator: ");
            user = scanner.nextLine();
            if (user.length() > 15) {
                user = "";
                System.out.println("Te rugam introdu un nume cu maximum 15 caractere");
            }
        }

        try {
            clientSocket = new Socket("127.0.0.1", 9000);          
            System.out.println("Connected successfully");            
            
            
            ClientPeer client = new ClientPeer(user, clientSocket, clientGUI,writeArea);
            System.out.println("Client started");
            client.start();
            
            client.sendUserName(user);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            String endLoop = "";
            while (endLoop == "") {
                String txt;
                try {
                    txt = in.readLine();
                    if (txt.startsWith("/w")) {
                        txt = txt.replace("/w ", "");
                        String[] splits = txt.split(" ");
                        txt = txt.replace(splits[0], "");
                        client.sendMessage(txt.trim(), splits[0]);
                    } else if (txt.startsWith("/p")) {
                        endLoop = "endloop";
                    } else {
                        client.sendMessage(txt);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            System.out.println(" Could not open socket !");
        }
        
    }

}
