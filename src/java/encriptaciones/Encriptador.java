package encriptaciones;


import exception.DescriptarException;
import exception.EncriptarException;
import exception.ResumirException;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 * Esta clase permite encriptar y resumir datos.
 * @author Ricardo Peinado Lastra
 */
public class Encriptador {
    /**
     * Desencripta un mensaje encriptado.
     * @param mensaje El mensaje a descriptar.
     * @return Retorna el mensaje en claro.
     * @throws DescriptarException  Salta si ocurre un error al desencriptar.
     */
    public String descriptar(String mensaje) throws DescriptarException{
        String frase =null;
        File file=new File("F:\\Clase 2DAM\\Cosas_print\\private.key");//Se tiene que cambiar
        try {
            byte[] bytes=Files.readAllBytes(file.toPath());
            byte[] bytesEncript=hexStringToByteArray(mensaje);
            //byte[] bytesEncript=stringToBytes(mensaje);
            EncodedKeySpec secretKeySpec = new  PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            
            PrivateKey privateKey = keyFactory.generatePrivate(secretKeySpec);
            
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            frase=new String(cipher.doFinal(bytesEncript));
            
        } catch (Exception ex) {
            System.out.println(ex.getCause()+" "+ex.getMessage());
            throw new DescriptarException(ex.getMessage());
        }
        return frase;
    }
    /**
     * Encripta en mensaje en claro que metas.
     * @param mensaje El mensaje en claro.
     * @return El mensaje encriptado.
     * @throws EncriptarException  Salta si ocurre un error al encriptar.
     */
    public String encriptar(String mensaje) throws EncriptarException{
        String encriptado=null;
        try {
            
            File file=new File("F:\\Clase 2DAM\\Cosas_print\\public.key");//se tiene que cambiar
            byte[] bytes=Files.readAllBytes(file.toPath());
            
            EncodedKeySpec publicKeySpec = new  X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherText = cipher.doFinal(mensaje.getBytes());
            
            encriptado= hexadecimal(cipherText);
            
            
        } catch (Exception ex) {
            throw new EncriptarException(ex.getMessage());
        }
        return encriptado;
    }
    /**
     * Resume un mensaje.
     * @param mensaje El texto a resumir.
     * @return El texto resumido.
     * @throws ResumirException  Salta si ocurre un error al resumir.
     */
    public String resumir(String mensaje) throws ResumirException{
        MessageDigest messageDigest;
        String resultado = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte dataBytes[] = mensaje.getBytes();
            messageDigest.update(dataBytes);
            byte resumen[] = messageDigest.digest();
            resultado=hexadecimal(resumen);
            
        } catch (NoSuchAlgorithmException e) {
            throw new ResumirException(e.getMessage());
        }
        return resultado;
    }
    /**
     * Convierte una lista de bytes a Hexadecimal.
     * @param resumen La colección de bytes.
     * @return Los bytes en hexadecimal.
     */
    static String hexadecimal(byte[] resumen) {
        String HEX = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1)
                HEX += "0";
            HEX += h;
        }
        return HEX.toUpperCase();
    }
    /**
     * Convierte un texto en hexadeciaml en una lista de bytes.
     * @param s El texto en hexadecimal.
     * @return La coleccion en bytes.
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    
}