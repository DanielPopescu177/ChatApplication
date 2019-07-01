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
public final class PrivateMessage extends Message implements Serializable {

    //private String senderName;
    //private String messageText;
    private String recipientName;

    public PrivateMessage(String _senderName, String _messageText, String _recipientName) {
        super(_senderName, _messageText);
        recipientName = _recipientName;
    }

    public String getRecipient() {
        return recipientName;
    }

    @Override
    public String getMessage() {
        return (super.getMessage());
    }
}
