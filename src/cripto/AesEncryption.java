/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

/**
 *
 * @author popescda
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Base64;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class AesEncryption {
    public static String encrypt(String key, String initVector, String value) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            //System.out.println("encrypted string: "
              //      + Base64.encodeBase64String(encrypted));

            //return Base64.encodeBase64String(encrypted);
            String s = new String(Base64.getEncoder().encode(encrypted));
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        Cheie128 key = new Cheie128("K17091996CR7cr7X");
        //String key = "K17091996CR7cr7X"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        
        long lStartTime;
        long lEndTime;
        
        System.out.println(encrypt(key.getCheie(), initVector, "sal all"));
        String x,y;
        
        //start
        lStartTime = System.nanoTime();

		//task
        
        x=encrypt(key.getCheie(), initVector, "daniel : sal all");
        
        lEndTime = System.nanoTime();
        
        y=decrypt(key.getCheie(), initVector, x);
        System.out.println(y);

		//end
        

		//time elapsed
        long output = lEndTime - lStartTime;

        System.out.println("Elapsed time in nanoseconds: " + output );
    }
    private static void calculation() throws InterruptedException {

        //Sleep 2 seconds
        TimeUnit.SECONDS.sleep(2);

    }
}
