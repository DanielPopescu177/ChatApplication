/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.client;

import chatapplication.client.gui.ClientGUI;
import chatapplication.structs.Message;
import chatapplication.structs.PrivateMessage;
import cripto.AesEncryption;
import cripto.Cheie128;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author popescda
 */
public class ClientPeer extends Thread {
    private Cheie128 AesKey128 = new Cheie128("K17091996CR7cr7X");
    
    private final String initVector = "RandomInitVector"; // 16 bytes IV
    private JTextArea writeArea = null;
    ClientGUI clientGUI = null;
    private ObjectOutputStream objOutputStream = null;
    private ObjectInputStream objInputStream = null;
    private final String userName;
    Socket socket;

    public ClientPeer(String _userName, Socket _socket, ClientGUI _clientGUI,JTextArea _writeArea) {
        try {
            objOutputStream = new ObjectOutputStream(_socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientGUI = _clientGUI;
        writeArea = _writeArea;
        userName = _userName;
        socket = _socket;
    }

    public synchronized void sendMessage(String message) throws IOException {
        clientGUI.setSenderThreadId(this.getId());
        Message msg = new Message(userName, clientGUI.encryptMessage(message));
        objOutputStream.writeObject(msg);
        
    }

    public synchronized void sendMessage(String message, String recipient) throws IOException {
        PrivateMessage msg = new PrivateMessage(userName, clientGUI.encryptMessage(message), recipient);
        objOutputStream.writeObject(msg);
    }

    public synchronized void sendUserName(String user) throws IOException {
        objOutputStream.writeObject(user);
    }

    @Override
    public void run() {
        try {
            objInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                Object obj;
                if ((obj = (Object) objInputStream.readObject()) != null) {

                    if (obj instanceof PrivateMessage) {
                        PrivateMessage msg = (PrivateMessage) obj;
                        String decryptedMessage;
                        decryptedMessage = clientGUI.decryptMessage(msg.getMessage(),this.getId());
                        writeArea.append("(Privat catre " + msg.getRecipient() + " ) " + msg.getSender() + " : " + decryptedMessage + "\n");
                        //System.out.println(msg.getMessage());
                    } else if (obj instanceof Message) {
                        Message msg = (Message) obj;
                        String decryptedMessage;
                        
                        decryptedMessage = clientGUI.decryptMessage(msg.getMessage(),this.getId());
                        writeArea.append(msg.getSender() + " : " + decryptedMessage + "\n");
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {

            }
        }
    }
}
