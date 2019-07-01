/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.structs;

import java.io.Serializable;

/**
 *
 * @author popescda
 */
public class Message implements Serializable{
    private String senderName;
    private String messageText;
    private final String AesKey128 = "K17091996CR7cr7X"; // 128 bit key
    private final String initVector = "RandomInitVector"; // 16 bytes IV
    
    public Message(String _senderName,String _messageText){
        senderName = _senderName;
        messageText = _messageText;
    }
    
    public String getMessage(){
        return (messageText);
    }
    public String getSender(){
        return senderName;
    }
    
}
