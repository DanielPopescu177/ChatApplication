/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author popescda
 */
public class PythonAES {
    private static final String PYTHONPATH = "C:\\Users\\daniel.popescu1709\\AppData\\Local\\Continuum\\anaconda3\\python.exe ";
    private static final String SCRIPTPATH = "\"C:\\Users\\daniel.popescu1709\\Desktop\\Final Licenta\\ChatApplication\\Phyton software implementation\\charHandling.py\" ";
        
    public static String encrypt(String key,String data) throws IOException{
        String finalEncryptionString;
        int currentIndex = 0;
        String action = "encrypt ";
        System.out.println("Lungime mesaj: " + data.length());
        String command = PYTHONPATH + SCRIPTPATH + action + key + " \"" + data + "\"";
        Process p = Runtime.getRuntime().exec(command);
        // read the output from the command
        BufferedReader stdInput = new BufferedReader(new 
            InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new 
            InputStreamReader(p.getErrorStream()));
        String s ;
        String encryptedString = "";
        

        boolean now = false;
        String[] values = new String[16];
        int k=0;
        
            while ((s = stdInput.readLine()) != null) {
                //System.out.println(s);
                if(s.startsWith("PASS")){
                    now=true;
                }
                else if(now && k<16){
                    values[k]=s;
                    values[k]=values[k].replace("\n", "");
                    k+=1;
                }
            }
        for (int i=0;i<16;i++){
            encryptedString = encryptedString + " "+ values[i];
        }
        return encryptedString;
    }
    public static String decrypt(String key,String encryptedData) throws IOException{
        String action = "decrypt ";
        String command = PYTHONPATH + SCRIPTPATH + action + key + " " + encryptedData;
        
        boolean now = false;
        Process p2 = Runtime.getRuntime().exec(command);
        BufferedReader stdInput2 = new BufferedReader(new 
            InputStreamReader(p2.getInputStream()));
        String s2 = null;
        String decryptedMessage = null;

            while ((s2 = stdInput2.readLine()) != null) {
                //System.out.println(s2);
                if(s2.startsWith("DECODED")){
                    now=true;
                }
                else if (now){
                    decryptedMessage = s2;
                    break;
                }
            }
        
        return decryptedMessage;
    }
}
