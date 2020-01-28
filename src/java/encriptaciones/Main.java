package encriptaciones;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Crea la clave privada como la publica.
 * @author Ricardo Peinado Lastra
 */
public class Main {
    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator =  KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair kp = keyPairGenerator.generateKeyPair();
            
            PrivateKey privateKey = kp.getPrivate();
            PublicKey publicKey = kp.getPublic();
            
            guardarClavePrivada(privateKey);
            guardarClavePublica(publicKey);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Guarda la clave privada.
     * @param privateKey  La clave privada.
     */
    private static void guardarClavePrivada(PrivateKey privateKey) {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        try {
            FileOutputStream fos = new FileOutputStream("private.key");
            fos.write(spec.getEncoded());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /**
     * Guarda la clabe publica.
     * @param publicKey La clave publica.
     */
    private static void guardarClavePublica(PublicKey publicKey) {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(publicKey.getEncoded());
        try {
            FileOutputStream fos = new FileOutputStream("public.key");
            fos.write(spec.getEncoded());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
