/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajeria;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Usuario
 */
public class CreadorDeCredenciales {
    private static ResourceBundle configFile=ResourceBundle.getBundle("mensajeria.mensajeConfig");
    private static byte[] salt =configFile.getString("salt").getBytes();
    private static final String clave=configFile.getString("clave");
     public static void main(String[] args) {
        String cifrarTexto = cifrarTexto(clave,"equipo4Deply@email.com","cuentaEmail.dat");
        String cifrarClave = cifrarTexto(clave,"abcd*1234","cuentaPass.dat");
       
    }
     public static String cifrarTexto(String clave, String mensaje,String nombreFichero) {
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");
            
            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes()); // Mensaje cifrado !!!
            byte[] iv = cipher.getIV(); // vector de inicialización por modo CBC
            
            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combined = concatArrays(iv, encodedMessage);
            
            //fileWriter("F:\\Clase 2DAM\\Cosas_print\\"+nombreFichero, combined);
             fileWriter(nombreFichero, combined);
            
            ret = new String(encodedMessage);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    private static byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }
    
    /**
     * Escribe un fichero
     *
     * @param path Path del fichero
     * @param text Texto a escibir
     */
    private static void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
}
