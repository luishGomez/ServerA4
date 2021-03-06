package service;

import ejb.ApunteEJBLocal;
import entity.Apunte;
import entity.Cliente;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Es la clase de servicio Web RESTful que expone las operaciones CRUD de {@link  Apunte} Entity.
 * @author Ricardo Peinado Lastra
 */
@Path("apunte")
public class ApunteFacadeREST  {
    private static final Logger LOGGER =
            Logger.getLogger("ApunteFacadeREST");
    /**
     * La referencia al objeto que maneja la logica de negocio de {@link Apunte}.
     */
    @EJB
    private ApunteEJBLocal ejb;
    
    /**
     * Metodo RESTFull para crear un {@link Apunte}.
     * @param apunte El objeto Apunte con sus datos.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Apunte apunte) {
        try {
            apunte.setIdApunte(null);
            ejb.createApunte(apunte);
        } catch (CreateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> create() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para editar un {@link  Apunte}.
     * @param apunte El objeto Apunte con sus datos.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void edit( Apunte apunte) {
        try {
            ejb.editApunte(apunte);
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> edit() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para eliminar un {@link Apunte}.
     * @param id El ID de un {@link Apunte}.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find apunte by id={0}.",id);
            try {
                ejb.removeApunte(ejb.findApunte(id));
            } catch (SelectException ex) {
                LOGGER.log(Level.SEVERE,
                        "UserRESTful service: Exception reading apunte by id, {0}",
                        ex.getMessage());
                throw new InternalServerErrorException(ex);
            }
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception deleting apunte by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para buscar un {@link Apunte} por su ID.
     * @param id El ID de un {@link Apunte}.
     * @return Retorna un objeto Apunte con sus datos.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Apunte find(@PathParam("id") Integer id) {
        Apunte resultado = null;
        try {
            resultado=ejb.findApunte(id);
        } catch (SelectException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> find() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
        
    }
    /**
     * Metodo RESTFul para buscar todos los Apuntes.
     * @return Retorna una lista de {@link Apunte} existentes.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Set<Apunte> findAll() {
        Set <Apunte> resultado = null;
        try {
            resultado=ejb.findAllApuntes();
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> findAll() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que busca los apuntes creados por un {@link Cliente}.
     * @param id El ID de un{@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     */
    @GET
    @Path("creador/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Apunte> getApuntesByCreador(@PathParam("id") Integer id) {
        Set <Apunte> resultado = null;
        try {
            resultado=ejb.getApuntesByCreador(id);
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> getApuntesByCreador() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que busca los apuntes de un comprado.
     * @param id El ID de un{@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     */
    @GET
    @Path("cliente/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Apunte> getApuntesByComprador(@PathParam("id") Integer id) {
        Set <Apunte> resultado = null;
        try {
            resultado=ejb.getApuntesByComprador(id);
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> getApuntesByComprador() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que inserta una votación de un {@link Apunte}.
     * @param idCliente El ID de un {@link Cliente}.
     * @param like 1: Si es un like | -1: Si es dislike.
     * @param apunte Es un objeto {@link Apunte} con sus datos.
     */
    @PUT
    @Path("votar/{idCliente}/{like}")
    @Consumes(MediaType.APPLICATION_XML)
    public void votacion(@PathParam("idCliente") Integer idCliente,@PathParam("like") Integer like, Apunte apunte) {
        try {
            ejb.votacion(idCliente, like, apunte);
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> votacion() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul que cuentas cuantas compras a tenido un apunte.
     * @param id El ID de un {@link Apunte}
     * @return Retorna la cantidad de compras de un apunte.
     */
    @GET
    @Path("cuantasCompras/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public int cuantasCompras(@PathParam("id") Integer id) {
        int resultado = 0;
        try {
            resultado=ejb.cuantasCompras(id);
        } catch (SelectException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> cuantasCompras() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
        
    }
    /**
     * Devuelve el archivo del apunte en un String
     * @param id El codigo del apunte.
     * @return Devuelve los bytes del pdf en hexadecimal
     */
    @GET
    @Path("archivo/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getArchivoDelApunte(@PathParam("id") Integer id) {
        String resultado = null;
        try {           
            Apunte apunte=ejb.findApunte(id);
            resultado=hexadecimal(apunte.getArchivo());            
        } catch (SelectException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> find() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
        
    }
    /**
     * Permite crear un apunte desde android.
     * @param apunte Los datos del apunte.
     */
    @POST
    @Path("createApunteAndroid")
    @Consumes(MediaType.APPLICATION_XML)
    public void createApunteAndroid(Apunte apunte) {
        try {
            LOGGER.info("Se va a crear un apunte desde android: ");
            apunte.setIdApunte(null);
            ejb.createApunte(apunte);
        } catch (CreateException  ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> create() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
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
