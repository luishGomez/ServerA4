package mensajeria;

import exception.EnviarMailException;
import exception.MontajeMailException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
 * Es el hilo que controla la petición del cliente.
 * It is the thread that controls the client’s request.
 * @author Ricardo Peinado Lastra
 */
public class EmailThread extends Thread{
    
    private Properties properties = new Properties();
    private static ResourceBundle configFile=ResourceBundle.getBundle("mensajeria.mensajeConfig");
    private static byte[] salt =configFile.getString("salt").getBytes();
    private static final String clave=configFile.getString("clave");
    private Session sesion;
    private String receptor;
    private String asunto;
    private String mensaje;
    private String address=descifrarTexto(clave,configFile.getString("email"));
    private String password=descifrarTexto(clave,configFile.getString("password"));
    //private String address=descifrarTexto(clave,"/src/java/mensajeria/cuentaEmail.dat");
    //private String password=descifrarTexto(clave,"/src/java/mensajeria/cuentaPass.dat");
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
        Logger.getLogger(EmailThread.class.getName()).severe("info "+address+"  "+password);
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
    /**
     * Rellena las propiedas para poder enviar un email por Mail.com
     */
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
    /**
     * Verifica la sesión con las credenciales
     */
    private void verificarSesion() {
        
        Authenticator autenticacion=new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        };
        sesion = Session.getInstance(properties,autenticacion);
    }
    /**
     * Prepara el mensaje.
     * @param receptor A la persona que va dirijido.
     * @param asunto El asunto del mensaje.
     * @param mensaje El mensaje.
     * @throws MontajeMailException Salta si se a cometido algun error al montar el mensaje.
     * @throws EnviarMailException Salta si ocurre algun error en el envio.
     */
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
            Logger.getLogger(EmailThread.class.getName()).severe("EmailThread -> prepararMensaje() ERROR: "+e.getMessage());
            throw new MontajeMailException(e.getMessage());
        }
        try{
            Transport.send(message);
        }catch(MessagingException e){
            Logger.getLogger(EmailThread.class.getName()).severe("EmailThread -> prepararMensaje() ERROR: "+e.getMessage());
            throw new EnviarMailException(e.getMessage());
        }
    }
    /**
     * Descifra las credenciales.
     * @param clave La clave para descrifrarla.
     * @param ruta La ruta del fichero que contiene la credencial.
     * @return 
     */
    private String descifrarTexto(String clave,String ruta) {
        String retorno = null;
        byte[] fileContent = fileReader(ruta);
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            retorno = new String(decodedMessage);
        } catch (Exception e) {
            Logger.getLogger(EmailThread.class.getName()).severe("EmailThread -> descifrarTexto() ERROR: "+e.getMessage());
        }
        return retorno;
    }
    /**
     * Lee el fichero.
     * @param path
     * @return 
     */
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        //FileInputStream fis=null; VERSION FUNCIONABLE
        //NNUEVO
        InputStream in = null;
        try {
            // ret = Files.readAllBytes(file.toPath()); //bien??
            
            //ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
            //ret=(byte[]) objectInputStream.readObject();
            
            /* VERSION FUNCIONABLE
            fis=new FileInputStream(path);
            ret=new byte[fis.available()];
            fis.read(ret);
            */
            in=EmailThread.class.getClassLoader().getResourceAsStream(path);
            ret=new byte[in.available()];
            in.read(ret);
            
        } catch (IOException e) {
            Logger.getLogger(EmailThread.class.getName()).severe("EmailThread -> fileReader() ERROR: "+e.getMessage());
        }finally{
            try{
                /*VERSION FUNCIONABLE
                if(fis!=null)
                    fis.close();
                */
                //nuevo
                if(in!=null)
                    in.close();
            } catch (IOException ex) {
                Logger.getLogger(EmailThread.class.getName()).severe("EmailThread -> fileReader() ERROR Al cerrar FileInputStream: "+ex.getMessage());
            }
        }
        //catch (ClassNotFoundException ex) {
        //  Logger.getLogger(EmailThread.class.getName()).severe("EmailThread -> fileReader() ERROR casteo: "+ex.getMessage());
        //}
        return ret;
    }
    
}