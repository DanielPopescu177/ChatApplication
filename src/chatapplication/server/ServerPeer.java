/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.server;

import chatapplication.structs.Message;
import chatapplication.structs.PrivateMessage;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author popescda
 */
public class ServerPeer extends Thread {

    private final Socket socket;
    private final Server server;
    private ObjectOutputStream objectOutputStream;
    public String userName;

    private ObjectInputStream objInput = null;

    public ServerPeer(Server _server, Socket _socket) throws IOException {
        
        userName = "anonimul";
        socket = _socket;
        server = _server;
    }

    public Socket getSocket() {
        return socket;
    }
    public ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }

    @Override
    public synchronized void run() {
        try {
            objInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                Object obj = objInput.readObject();
                if (obj instanceof PrivateMessage) {
                    PrivateMessage msg = (PrivateMessage) obj;
                    System.out.println("Server recieved message: " + msg.getMessage());
                    sendPrivateMessage(msg, msg.getRecipient());
                } else if (obj instanceof Message) {
                    Message msg = (Message) obj;
                    System.out.println("Server recieved message: " + msg.getMessage());
                    sendMessage(msg);
                } else if (obj instanceof String) {
                    userName = (String) obj;
                    System.out.println(userName + " connected...");
                }
            } catch (EOFException ex) {
                System.out.println(" Client disconnected! ");
                break;
            } catch (IOException ex) {
                System.out.println("Client connection reset: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(" CLASSNOTFOUND EXCEPTION ");
                Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

    }

    public synchronized void sendMessage(Message msg) {
        userName = msg.getSender();
        server.dispatchAll(msg);

    }

    public synchronized void sendPrivateMessage(PrivateMessage msg, String recipient) {
        userName = msg.getSender();
        server.dispatch(msg, recipient);

    }

    public String getUsername() {
        return userName;
    }
}
