/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;


/**
 *
 * @author popescda
 */
public class TestArea {
    
    public static void main(String[] args) throws InterruptedException, IOException {
        //System.out.println(getDecimal("FE"));
        
        System.out.println(Integer.toHexString(43 & 0xFF));
        
        System.out.println(HardwareAES.getDecimal("8b"));
        
        System.out.println((char)9);
        
        
        //HardwareAES.encrypt("K17091996CR7cr7X", "ABCDEFGHABCDEFGH");
        /*
        
        
        String pythonPath = "C:\\Users\\popescda\\AppData\\Local\\Continuum\\anaconda3\\python.exe ";
        String scriptPath = "\"C:\\Users\\popescda\\Desktop\\Old Desktop\\AES Documentation\\Phyton software implementation\\charHandling.py\" ";
        String action = "encrypt ";
        String key = "K17091996CR7cr7X ";
        String message = "hello";
        String command = pythonPath + scriptPath + action +key + message;
        System.out.println("COMMAND: " + command);
        Process p = Runtime.getRuntime().exec(command);
        // read the output from the command
        BufferedReader stdInput = new BufferedReader(new 
            InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new 
            InputStreamReader(p.getErrorStream()));
        String s = null;
        String encryptedString = null;
        byte[] b = null;
        
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
        boolean now = false;
        String[] values = new String[16];
        int k=0;
        
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);

                if(s.startsWith("PASS")){
                    now=true;
                    //b = s.getBytes();
                    //encryptedString =  s;
                    //encryptedString = encryptedString.replace("b'", "");
                    //encryptedString = encryptedString.replace("'", "");
                    //System.out.println(b);
                    //System.out.println(encryptedString);
                }
                else if(now && k<16){
                    values[k]=s;
                    values[k]=values[k].replace("\n", "");
                    k+=1;
                }
            }
        //---------------------Try to get decryption
        command = pythonPath + scriptPath + "decrypt " +key;
        for (int i=0;i<16;i++){
            System.out.println(" AMA ADAUGAT " + values[i] + " i = " + Integer.toString(i));
            command = command + " "+ values[i];
        }
        now = false;
        Process p2 = Runtime.getRuntime().exec(command);
        BufferedReader stdInput2 = new BufferedReader(new 
            InputStreamReader(p2.getInputStream()));
        String s2 = null;
        String decryptedMessage = null;
        System.out.println("Here is the standard output of the command:\n");
            while ((s2 = stdInput2.readLine()) != null) {
                System.out.println(s2);
                if(s2.startsWith("DECODED")){
                    now=true;
                }
                else if (now){
                    decryptedMessage = s2;
                    break;
                }
            }    
        //----------end tro yto get decryption   
            
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        
        
        System.out.println("FINALLY");
        System.out.println(decryptedMessage);

*/
    }
}
