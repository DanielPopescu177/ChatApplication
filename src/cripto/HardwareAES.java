/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author popescda
 */
public class HardwareAES {

    public static int getDecimal(String hex){  
    String digits = "0123456789ABCDEF";  
             hex = hex.toUpperCase();  
             int val = 0;  
             for (int i = 0; i < hex.length(); i++)  
             {  
                 char c = hex.charAt(i);  
                 int d = digits.indexOf(c);  
                 val = 16*val + d;  
             }  
             return val;  
    }  
    
    
    private static final String SCRIPTPATH = "C:\\Users\\daniel.popescu1709\\Desktop\\Final Licenta\\AES_RAM_APB_MASTER\\";

    public static String encrypt(String key, String data) throws IOException {
        String encryptedString = "";
        //ProcessBuilder pb = new ProcessBuilder("quartus_stp","-t",SCRIPTPATH+"aes_test.tcl");
        List builders = Arrays.asList(
                new ProcessBuilder("C:\\altera\\13.0sp1\\quartus\\bin\\quartus_stp", "-t", SCRIPTPATH + "automatic.tcl"));

        
        if (key.length() != 16) {
            System.out.println("Cheia trebuie sa aiba 128 biti ! (16 caractere)");
            return null;
        }
        int temp = data.length();
        if (data.length() < 16) {
            for(int i=0 ;i< 16-temp;i++)
                data += " ";
        }
        byte[] dataBytes = data.getBytes(StandardCharsets.US_ASCII);
        byte[] keyBytes = key.getBytes(StandardCharsets.US_ASCII);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCRIPTPATH + "automatic.tcl"))) {
            writer.write("set usb [lindex [get_hardware_names] 0]\n");
            writer.write("set device_name [lindex [get_device_names -hardware_name $usb] 0]\n");

            writer.write("proc hex2bits {i} {\n");
            writer.write("  set res \"\"\n");
            writer.write("  while {$i>0} {\n");
            writer.write("      set res [expr {$i%2}]$res\n");
            writer.write("      set i [expr {$i/2}]\n");
            writer.write("  }\n");
            writer.write("  if {$res==\"\"} {set res 0}\n");
            writer.write("  split $res \"\"\n");
            writer.write("  set res [regsub -all \" \" $res \"\"]\n");
            writer.write("  return $res\n");
            writer.write("}\n");

            writer.write("proc bits2hex {bits} {\n");
            writer.write("set res 0\n");
            writer.write("foreach i $bits {\n");
            writer.write("set res [expr {$res*2+$i}]\n");
            writer.write("}\n");
            writer.write("return $res\n");
            writer.write("}\n");

            writer.write("proc bin2hex bin {\n");
            writer.write("  array set t {\n");
            writer.write("0000 0 0001 1 0010 2 0011 3 0100 4\n");
            writer.write("0101 5 0110 6 0111 7 1000 8 1001 9\n");
            writer.write("1010 a 1011 b 1100 c 1101 d 1110 e 1111 f\n");
            writer.write("}\n");
            writer.write("  set diff [expr {4-[string length $bin] %4}]\n");
            writer.write("  if {$diff != 4} {\n");
            writer.write("      set bin [format %0${diff}d$bin 0]\n");
            writer.write("  }\n");
            writer.write("  regsub -all .... $bin {$t(&)} hex\n");
            writer.write("  return [subst $hex]\n");
            writer.write("}\n");

            writer.write("proc write {value offset} {\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("  write_source_data -instance_index 1 -value [hex2bits $offset]\n");
            writer.write("  write_source_data -instance_index 0 -value [hex2bits $value]\n");
            writer.write("  write_source_data -instance_index 2 -value 0xe -value_in_hex\n");
            writer.write("  write_source_data -instance_index 2 -value 0xf -value_in_hex\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("}\n");

            writer.write("proc read {offset} {\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("  write_source_data -instance_index 1 -value [hex2bits $offset]\n");
            writer.write("  write_source_data -instance_index 2 -value 0xc -value_in_hex\n");
            writer.write("  write_source_data -instance_index 2 -value 0xd -value_in_hex\n");
            writer.write("  set res [read_probe_data -instance_index 0]\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("  return [bin2hex $res]\n");
            writer.write("}\n");

            writer.write("start_insystem_source_probe -device_name $device_name -hardware_name $usb\n");

            writer.write("\n\n");

            

            //writer.write("write [expr 0x0001] [expr 0x0004]\n"); //criptare 128 setare mod
            writer.write("puts \"first\"\n");

            //selectare mod 128 biti criptare
            writer.write("write [expr 0x0000] [expr 0x0004]\n");
            //scriere cheie
            for (int i = 0; i < keyBytes.length; i += 2) {
                writer.write("write [expr 0x" + String.valueOf(Integer.toHexString(keyBytes[i] & 0xFF)) + String.valueOf(Integer.toHexString(keyBytes[i + 1] & 0xFF)) + "] [expr 0x0008]\n");
            }
            //scriere date
            for (int i = 0; i < dataBytes.length; i += 2) {
                writer.write("write [expr 0x" + String.valueOf(Integer.toHexString(dataBytes[i] & 0xFF)) + String.valueOf(Integer.toHexString(dataBytes[i + 1] & 0xFF)) + "] [expr 0x0000]\n");
            }
            for (int i = 0; i < 8; i++) {
                writer.write("[read [expr 0x000C]]\n");
            }
            //citire rezultate
            for (int i = 0; i < 8; i++) {
                writer.write("puts [read [expr 0x0000]]\n");
            }

            //writer.write("puts [read [expr 0x0004]]\n");
            writer.write("end_insystem_source_probe \n");
        }

        List processes = ProcessBuilder.startPipeline(builders);

        Process last = (Process) processes.get(processes.size() - 1);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(last.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(last.getErrorStream()));
        String s;
        boolean ok = false;
        while ((s = stdInput.readLine()) != null) {
            if (!ok) {
                if ("first".equals(s)) {
                    ok = true;
                }

            } else {
                if (s.startsWith("Info")) {
                    break;
                }
                encryptedString += s;
            }

        }
        
        return encryptedString;
    }
    
    public static String decrypt(String key, String data) throws IOException {
        String decryptedString = "";
        //ProcessBuilder pb = new ProcessBuilder("quartus_stp","-t",SCRIPTPATH+"aes_test.tcl");
        List builders = Arrays.asList(
                new ProcessBuilder("C:\\altera\\13.0sp1\\quartus\\bin\\quartus_stp", "-t", SCRIPTPATH + "automatic.tcl"));
        System.out.println("DATA RECIEVED BY DECRYPT METHOD: " + data);
        byte[] keyBytes = key.getBytes(StandardCharsets.US_ASCII);
        if (key.length() != 16) {
            System.out.println("Cheia trebuie sa aiba 128 biti ! (16 caractere)");
            return null;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCRIPTPATH + "automatic.tcl"))) {
            writer.write("set usb [lindex [get_hardware_names] 0]\n");
            writer.write("set device_name [lindex [get_device_names -hardware_name $usb] 0]\n");

            writer.write("proc hex2bits {i} {\n");
            writer.write("  set res \"\"\n");
            writer.write("  while {$i>0} {\n");
            writer.write("      set res [expr {$i%2}]$res\n");
            writer.write("      set i [expr {$i/2}]\n");
            writer.write("  }\n");
            writer.write("  if {$res==\"\"} {set res 0}\n");
            writer.write("  split $res \"\"\n");
            writer.write("  set res [regsub -all \" \" $res \"\"]\n");
            writer.write("  return $res\n");
            writer.write("}\n");

            writer.write("proc bits2hex {bits} {\n");
            writer.write("set res 0\n");
            writer.write("foreach i $bits {\n");
            writer.write("set res [expr {$res*2+$i}]\n");
            writer.write("}\n");
            writer.write("return $res\n");
            writer.write("}\n");

            writer.write("proc bin2hex bin {\n");
            writer.write("  array set t {\n");
            writer.write("0000 0 0001 1 0010 2 0011 3 0100 4\n");
            writer.write("0101 5 0110 6 0111 7 1000 8 1001 9\n");
            writer.write("1010 a 1011 b 1100 c 1101 d 1110 e 1111 f\n");
            writer.write("}\n");
            writer.write("  set diff [expr {4-[string length $bin] %4}]\n");
            writer.write("  if {$diff != 4} {\n");
            writer.write("      set bin [format %0${diff}d$bin 0]\n");
            writer.write("  }\n");
            writer.write("  regsub -all .... $bin {$t(&)} hex\n");
            writer.write("  return [subst $hex]\n");
            writer.write("}\n");

            writer.write("proc write {value offset} {\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("  write_source_data -instance_index 1 -value [hex2bits $offset]\n");
            writer.write("  write_source_data -instance_index 0 -value [hex2bits $value]\n");
            writer.write("  write_source_data -instance_index 2 -value 0xe -value_in_hex\n");
            writer.write("  write_source_data -instance_index 2 -value 0xf -value_in_hex\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("}\n");

            writer.write("proc read {offset} {\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("  write_source_data -instance_index 1 -value [hex2bits $offset]\n");
            writer.write("  write_source_data -instance_index 2 -value 0xc -value_in_hex\n");
            writer.write("  write_source_data -instance_index 2 -value 0xd -value_in_hex\n");
            writer.write("  set res [read_probe_data -instance_index 0]\n");
            writer.write("  write_source_data -instance_index 2 -value 0\n");
            writer.write("  return [bin2hex $res]\n");
            writer.write("}\n");

            writer.write("start_insystem_source_probe -device_name $device_name -hardware_name $usb\n");

            writer.write("\n\n");


            //writer.write("write [expr 0x0001] [expr 0x0004]\n"); //criptare 128 setare mod
            writer.write("puts \"second\"\n");

            //selectare mod 128 biti decriptare
            writer.write("write [expr 0x0001] [expr 0x0004]\n");
            //scriere cheie

            for (int i = 0; i < keyBytes.length; i += 2) {
                writer.write("write [expr 0x" + String.valueOf(Integer.toHexString(keyBytes[i] & 0xFF)) + String.valueOf(Integer.toHexString(keyBytes[i + 1] & 0xFF)) + "] [expr 0x0008]\n");
            }
            //scriere date
            for (int i = 0; i < 32; i += 4) {
                writer.write("write [expr 0x" + data.substring(i, i+4) + "] [expr 0x0000]\n");
            }
            for (int i = 0; i < 8; i++) {
                writer.write("[read [expr 0x000C]]\n");
            }
            //citire rezultate
            for (int i = 0; i < 8; i++) {
                writer.write("puts [read [expr 0x0000]]\n");
            }

            //writer.write("puts [read [expr 0x0004]]\n");
            writer.write("end_insystem_source_probe \n");
        }

        List processes = ProcessBuilder.startPipeline(builders);

        Process last = (Process) processes.get(processes.size() - 1);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(last.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(last.getErrorStream()));
        String s;
        boolean ok = false;
        while ((s = stdInput.readLine()) != null) {
            if (!ok) {
                if ("second".equals(s)) {
                    ok = true;
                }

            } else {
                if (s.startsWith("Info")) {
                    break;
                }
                decryptedString += (char)(getDecimal(s.substring(0, 2)));
                decryptedString += (char)(getDecimal(s.substring(2, 4)));

            }

        }
        return decryptedString;
    }
    
    
}
