/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.server.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author popescda
 */
public class ServerConfig {

    private String fileName;
    private String TCP_PORT;
    private String MAX_CLIENTS;

    public ServerConfig(String _filename) {
        fileName = _filename;
        readConfig();
    }

    public ServerConfig() {
        fileName = "server.conf";
        TCP_PORT = "9000";
        MAX_CLIENTS = "100";
    }

    private void readConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("TCP_PORT")) {
                    String[] splits = line.split("=");
                    TCP_PORT = splits[1];
                } else if (line.startsWith("MAX_CLIENTS")) {
                    String[] splits = line.split("=");
                    MAX_CLIENTS = splits[1];
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Config file not found! ");
        } catch (IOException ex) {
            System.out.println("Read error! Check file format! ");
        }
    }

    public int getTCPPort() {
        return Integer.parseInt(TCP_PORT);
    }

    public int getMaxClients() {
        return Integer.parseInt(MAX_CLIENTS);
    }
}
