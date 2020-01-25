/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package encriptaciones;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *  Permite coger la llave publica.
 * @author Ricardo Peinado Lastra
 */
public class KeyGetter {
    private static ResourceBundle configFile=ResourceBundle.getBundle("encriptaciones.encriptadorConfig");
    private final String rutaPublica=configFile.getString("llavePublica");
    /**
     * Retorna la llave publica en hexadecimal.
     * @return  La llave en hexadecimal.
     */
    public String getLlavePublica(){
        String laClave=null;
        InputStream in = null;
        byte[] bytes=null;
        try {
            in=Encriptador.class.getClassLoader().getResourceAsStream(rutaPublica);
            bytes=new byte[in.available()];
            in.read(bytes);
        }catch(Exception ex){
            Logger.getLogger(KeyGetter.class.getName()).severe("ClienteFacadeRESTful -> create() ERROR: "+ex.getMessage());
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(KeyGetter.class.getName()).severe("ClienteFacadeRESTful -> create() ERROR: "+ex.getMessage());
                }
            }
        }
        //return DatatypeConverter.printBase64Binary(bytes);
        //return Base64.encode(bytes);
        return hexadecimal(bytes);
    }
    /**
     * Convierte un array de bytes en un String en hexadecimal.
     * @param resumen Los bytes convertir.
     * @return El string de los bytes en hexadecimal.
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
    
}
