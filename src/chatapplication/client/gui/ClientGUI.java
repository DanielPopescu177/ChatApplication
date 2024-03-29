/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.client.gui;

import chatapplication.client.ClientPeer;
import chatapplication.client.TextClient;
import chatapplication.server.Server;
import chatapplication.server.config.ServerConfig;
import cripto.AesEncryption;
import cripto.Cheie128;
import cripto.HardwareAES;
import static cripto.HardwareAES.getDecimal;
import cripto.PythonAES;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author popescda
 */
public class ClientGUI extends javax.swing.JFrame {
    private Cheie128 AesKey128 = new Cheie128("K17091996CR7cr7X"); // 128 bit key
    private final String initVector = "RandomInitVector"; // 16 bytes IV
    ArrayList<TextClient> clients;
    private long senderThreadId = 0;
    /**
     * Creates new form ClientGUI
     */
    public ClientGUI() {
        clients = new ArrayList<TextClient>();
        initComponents();
    }
    public void setSenderThreadId(long x){
        senderThreadId = x;
    }

    public synchronized String encryptMessage(String message) throws IOException {
        long lStartTime;
        long lEndTime;
        String encryptedMessage = null;
        //start
        lStartTime = System.nanoTime();

        if (jRadioButton1.isSelected()) {
            System.out.println("Hardware encryption selected");
            encryptedMessage = HardwareAES.encrypt(AesKey128.getCheie(), message);
        } else if (jRadioButton2.isSelected()) {
            System.out.println("Java encryption selected");
            encryptedMessage = AesEncryption.encrypt(AesKey128.getCheie(), initVector, message);
        } else if (jRadioButton3.isSelected()) {
            System.out.println("Python encryption selected");
            encryptedMessage = PythonAES.encrypt(AesKey128.getCheie(), message);
        }
        else if (jRadioButton4.isSelected()) { //encrypt with hardware
            System.out.println("Hardware encryption / Python decryption is selected");
            encryptedMessage = HardwareAES.encrypt(AesKey128.getCheie(), message);
        }

        lEndTime = System.nanoTime();

        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time for encryption in microseconds: " + output/1000);

        return encryptedMessage; // for now
    }

    public synchronized String decryptMessage(String encryptedMessage, long threadId) throws IOException {
        long lStartTime;
        long lEndTime;
        String decryptedMessage = null;
        //start
        //System.out.println("DEBUG : " + encryptedMessage);
        lStartTime = System.nanoTime();
        if (jRadioButton1.isSelected()) {
            decryptedMessage = HardwareAES.decrypt(AesKey128.getCheie(), encryptedMessage);
        } else if (jRadioButton2.isSelected()) {
            decryptedMessage =  AesEncryption.decrypt(AesKey128.getCheie(), initVector, encryptedMessage);
        } else if (jRadioButton3.isSelected()) {
            decryptedMessage =  PythonAES.decrypt(AesKey128.getCheie(), encryptedMessage);
        }
        else if (jRadioButton4.isSelected()) { //encrypt with hardware
            String pythonHandling = "";
            for(int i=0;i<encryptedMessage.length();i+=2){
                pythonHandling += String.valueOf(getDecimal(encryptedMessage.substring(i, i+2))) + " ";
           
            }
            System.out.println(pythonHandling);
            decryptedMessage =  PythonAES.decrypt(AesKey128.getCheie(), pythonHandling);
        }
        lEndTime = System.nanoTime();

        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time for decryption in microseconds: " + output/1000 + "\n");
        return decryptedMessage.strip(); // for now
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(100, 160, 210));
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 204));
        jPanel2.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel2.setMinimumSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(204, 255, 255));
        jButton1.setText("Open new client");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 255, 255));
        jButton2.setText("Start server");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2MouseReleased(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 102, 102));
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(700, 600));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(700, 600));

        jTextField1.setBackground(new java.awt.Color(231, 255, 255));
        jTextField1.setText("Username");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Hardware");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Java");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Python");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Hardware-C / Python-D");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addGap(22, 22, 22)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadioButton4))
                .addGap(6, 6, 6)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased

        Socket clientSocket;
        final JTextArea messages = new JTextArea(30, 75);
        JPanel jp1 = new JPanel();
        jp1.setBackground(new java.awt.Color(231, 255, 255));
        JButton sendButton = new JButton();
        final JTextArea msg = new JTextArea(1, 75);
        try {
            clientSocket = new Socket("127.0.0.1", 9000);
            System.out.println("Connected successfully");

            final ClientPeer client = new ClientPeer(jTextField1.getText(), clientSocket, this, messages);
            System.out.println("Client started");
            client.start();

            //jp1.setBackground(new Color(0.3f,0.3f,0.7f));
            jTabbedPane1.add(jTextField1.getText(), jp1);

            messages.setEditable(false);
            //messages.setMaximumSize(new Dimension(800,500));
            // messages.setMinimumSize(new Dimension(800,500));

            // msg = new JTextArea(1, 75);
            sendButton.setText("SEND");
            sendButton.setBackground(new java.awt.Color(180, 255, 255));

            client.sendUserName(jTextField1.getText());

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    String txt;
                    txt = msg.getText();
                    if (txt.startsWith("/w")) {
                        txt = txt.replace("/w ", "");
                        String[] splits = txt.split(" ");
                        txt = txt.replace(splits[0], "");
                        try {
                            msg.setText(null);
                            client.sendMessage(txt.trim(), splits[0]);
                        } catch (IOException ex) {
                            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (txt.startsWith("/q")) {
                        //endLoop = "endloop";
                    } else {
                        try {
                            msg.setText(null);
                            client.sendMessage(txt);
                        } catch (IOException ex) {
                            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            msg.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        String txt;
                        txt = msg.getText();
                        if (txt.startsWith("/w")) {
                            txt = txt.replace("/w ", "");
                            String[] splits = txt.split(" ");
                            txt = txt.replace(splits[0], "");
                            try {
                                client.sendMessage(txt.trim(), splits[0]);
                                msg.setText(null);
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (txt.startsWith("/q")) {
                            //endLoop = "endloop";
                        } else {
                            try {
                                msg.setText(null);
                                client.sendMessage(txt);
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        evt.consume();
                    }
                }

                @Override
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        messages.append("debug");
                    }
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        //msg2.setBounds(10, 800, 300, 100);
        //msg2.setSize(75, 50);
        // adding content to client tab
        jp1.add(messages);
        jp1.add(msg);
        jp1.add(sendButton);

    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseReleased
        ServerConfig svConfig = new ServerConfig();
        Server server = new Server(svConfig.getTCPPort(), svConfig.getMaxClients());
        System.out.println("Server started successfully");
        server.start();
        jButton2.setEnabled(false);
    }//GEN-LAST:event_jButton2MouseReleased

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyPressed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
