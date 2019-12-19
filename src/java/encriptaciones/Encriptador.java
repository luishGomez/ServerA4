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
 *
 * @author Usuario
 */
public class Encriptador {
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
    public String descriptar2(byte[] bytesEncript) throws DescriptarException{
        String frase =null;
        File file=new File("F:\\Clase 2DAM\\Cosas_print\\private.key");//Se tiene que cambiar
        try {
            byte[] bytes=Files.readAllBytes(file.toPath());
            //byte[] bytesEncript=mensaje.getBytes();
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
    
    public byte[] encriptar2(String mensaje) throws EncriptarException{
        String encriptado=null;
        byte[] resu=null;
        try {
            
            File file=new File("F:\\Clase 2DAM\\Cosas_print\\public.key");//se tiene que cambiar
            byte[] bytes=Files.readAllBytes(file.toPath());
            
            EncodedKeySpec publicKeySpec = new  X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherText = cipher.doFinal(mensaje.getBytes());
            resu=cipherText;
            //encriptado= new String(cipherText);
            encriptado= bytesToString(cipherText);
            
        } catch (Exception ex) {
            throw new EncriptarException(ex.getMessage());
        }
        return resu;
    }
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
    public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
}
    
    public String bytesToString(byte[] bytes){
        String retorno="";
        for(Byte b:bytes){
            retorno+=b;
        }
        return retorno;
    }
    public byte[] stringToBytes(String frase){
        byte[] bytes=new byte[frase.length()];
        int i=0;
        for(char c:frase.toCharArray()){
            bytes[i]=(byte) c;
            i++;
        }
        return bytes;
    }
}
