package mensajeria;

import exception.EnviarMailException;
import exception.MontajeMailException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * Es el hilo que controla el envio de un email.
 * @author Ricardo Peinado Lastra
 */
public class EmailThread extends Thread{
    
    private static ResourceBundle configFile=ResourceBundle.getBundle("mensajeria.mensajeConfig");
    
    private static byte[] salt = configFile.getString("salt").getBytes();
    private static String clave=configFile.getString("clave");
    private static String pathPass=configFile.getString("password");
    private static String pathEmail=configFile.getString("email");
    
    private Properties properties = new Properties();
    
    private Session sesion;
    private String receptor;
    private String asunto;
    private String mensaje;
    private String address=descifrarTexto(clave,pathEmail);
    private String password=descifrarTexto(clave,pathPass);
    
    public EmailThread(String receptor,String asunto, String mensaje) throws MontajeMailException, EnviarMailException{
        this.receptor=receptor;
        this.asunto=asunto;
        this.mensaje=mensaje;
        
    }
    /**
     * Este metodo es el inicio de la ejecución del hilo.
     * This method is the start of thread execution.
     */
    public void run(){
        rellenarPropiedades();
        verificarSesion();
        try {
            prepararMensaje(receptor,asunto,mensaje);
        } catch (MontajeMailException ex) {
            Logger.getLogger(EmailThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EnviarMailException ex) {
            Logger.getLogger(EmailThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void rellenarPropiedades() {
        
        String host=configFile.getString("host");
        Integer port=Integer.parseInt(configFile.getString("port"));
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);
        properties.put("mail.imap.partialfetch", false);      
    }
    
    private void verificarSesion() {
        
        Authenticator autenticacion=new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        };
        sesion = Session.getInstance(properties,autenticacion);
    }
    
    private void prepararMensaje(String receptor, String asunto, String mensaje) throws MontajeMailException, EnviarMailException {
        Message message=null;
        try{
            message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(address));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
            message.setSubject(asunto);
            
            Multipart multipart = new MimeMultipart();
            
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mensaje, "text/html");
            multipart.addBodyPart(mimeBodyPart);
            
            message.setContent(multipart);
            
        }catch(Exception e){
            throw new MontajeMailException(e.getMessage());
        }
        try{
            Transport.send(message);
        }catch(MessagingException e){
            throw new EnviarMailException(e.getMessage());
        }
    }
    private String descifrarTexto(String clave, String path) {
        String ret = null;
        
        // Fichero leído
        byte[] fileContent = fileReader(path);
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
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16)); // La IV está aquí
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
}
