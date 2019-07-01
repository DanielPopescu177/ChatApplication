/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.server;

import chatapplication.server.config.ServerConfig;
import chatapplication.structs.Message;
import chatapplication.structs.PrivateMessage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author popescda
 */
public class Server extends Thread{

    private ServerSocket serverSocket;
    private int connectedClients;
    private final int maxClients;
    ArrayList<ServerPeer> clients;
    ArrayList<Socket> clientSockets;

    public static void main(String[] args) {
        ServerConfig svConfig = new ServerConfig();
        Server server = new Server(svConfig.getTCPPort(), svConfig.getMaxClients());
        System.out.println("Server started successfully");
        server.run();
        
    }

    public Server(int TCP_PORT, int MAX_CLIENTS) {

        clients = new ArrayList<ServerPeer>();
        clientSockets = new ArrayList<Socket>();
        
        connectedClients = 0;
        maxClients = MAX_CLIENTS;

        try {
            serverSocket = new ServerSocket(TCP_PORT);
        } catch (IOException ex) {
            System.out.println("Server socket could not be created!");
        }

    }

    public void run() {
        while (true) {
            if (connectedClients < maxClients) {
                try {
                    Socket socket = serverSocket.accept();
                    clientSockets.add(socket);
                    ServerPeer serverPeer = new ServerPeer(this, socket);
                    clients.add(serverPeer);
                    serverPeer.start();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void dispatchAll(Message msg) {
        for (ServerPeer peer : clients) {
            //System.out.println(peer.getUsername());
            try {
                peer.getObjectOutputStream().writeObject(msg);
            } catch (IOException ex) {
               System.out.println(" IO IN SERVER EXCEPTION ");
            }
        }
    }

    public void dispatch(PrivateMessage msg, String recipient) {
        for (ServerPeer peer : clients) {
            if (peer.getUsername().equals(recipient) || peer.getUsername()==msg.getSender()) {
                try {
                    peer.getObjectOutputStream().writeObject(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void removeClient(ServerPeer serverPeer) {
        System.out.println(" User disconnected");
        clients.remove(serverPeer);
    }
}
