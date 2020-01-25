/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import ejb.ClienteEJBLocal;
import ejb.UsuarioEJBLocal;
import encriptaciones.Encriptador;
import encriptaciones.KeyGetter;
import entity.Apunte;
import entity.Cliente;
import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.DescriptarException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import exception.UserNoExistException;
import exception.WrongPasswordException;
import exception.YaExisteLoginException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *Es la clase de servicio Web RESTful que expone las operaciones CRUD de {@link  Cliente} Entity.
 * @author Ricardo Peinado Lastra
 */

@Path("cliente")
public class ClienteFacadeREST  {
    private Encriptador encriptador = new Encriptador();
    /**
     * La referencia al objeto que maneja la logica de negocio de {@link Apunte}
     */
    @EJB
    private ClienteEJBLocal ejb;
    @EJB
    private UsuarioEJBLocal ejbUser;
    /**
     * Metodo RESTFul para crear un {@link Cliente}.
     * @param cliente El objeto Cliente con sus datos.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Cliente cliente) {
        try{
            cliente.setId(null);
            ejbUser.findUserByLogin(cliente.getLogin());
            throw new YaExisteLoginException("Ya existe ese login");
        } catch (UserNoExistException ex) {
            
            try {
                cliente.setContrasenia(encriptador.descriptar(cliente.getContrasenia()));
                ejb.createCliente(cliente);
            } catch (CreateException ex1) {
                Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> create() ERROR: "+ex.getMessage());
                throw new InternalServerErrorException(ex.getMessage());
            } catch (DescriptarException ex1) {
                Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> create() ERROR al descriptar: "+ex.getMessage());
                throw new InternalServerErrorException(ex.getMessage());
            }
            
        } catch (SelectException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> create() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }catch (YaExisteLoginException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> create() ERROR: "+ex.getMessage());
            throw new NotAuthorizedException(ex.getMessage());
        }
    }
    /**
     * Metodo RESTFul para editar un {@link Cliente} ya existente.
     * @param cliente El bojecto Cliente con sus datos.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void edit( Cliente cliente) {
        try {
            ejb.editCliente(cliente);
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> edit() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para eliminar un {@link Cliente} ya existente.
     * @param id El ID de un objeto {@link Cliente}.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ejb.removeCliente(id);
        } catch (DeleteException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> remove() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para buscar un {@link Cliente} por su ID.
     * @param id El ID de un {@link Cliente}.
     * @return Retorna un objeto de tipo cliente con sus datos.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Cliente find(@PathParam("id") Integer id) {
        Cliente resultado=null;
        try {
            resultado= ejb.findCliente(id);
        } catch (SelectException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> find() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo para buscar todos los clientes.
     * @return Retorna una lista de {@link Cliente}-
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Set<Cliente> findAll() {
        Set <Cliente> resultado=null;
        try {
            resultado= ejb.findAllClientes();
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> findAll() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    
    /**
     * Metodo RESTFul que busca los cliente que han votado un apunte en especifico.
     * @param id El ID de un {@link Apunte}.
     * @return Retorna una lista de {@link Cliente}.
     */
    @GET
    @Path("votantes/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public List <Cliente> getVotantesId(@PathParam("id") Integer id) {
        List <Cliente> resultado =null;
        try {
            resultado=ejb.getVotantesId(id);
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> getVotantesId() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que actualiza la contraseña de un {@link Cliente}.
     * @param cliente El objeto cliente con sus datos.
     */
    @PUT
    @Path("password")
    @Consumes(MediaType.APPLICATION_XML)
    public void actualizarContrasenia(Cliente cliente) {
        try {
            cliente.setContrasenia(encriptador.descriptar(cliente.getContrasenia()));
            ejb.actualizarContrasenia(cliente);
            Date date = new Date();
            Cliente nuevo=ejb.findCliente(cliente.getId());
            nuevo.setUltimoCambioContrasenia(date);
            ejb.editCliente(nuevo);
        } catch (UpdateException | SelectException | DescriptarException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> actualizarContrasenia() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul que permite comprar un apunte.
     * @param cliente Los datos del cliente.
     * @param idApunte El numero identificativo del apunte.
     */
    @POST
    @Path("comprar/{idApunte}")
    @Consumes(MediaType.APPLICATION_XML)
    public void comprarApunte(Cliente cliente,@PathParam("idApunte") Integer idApunte) {
        
        try {
            ejb.comprarApunte(cliente, idApunte);
        } catch (CreateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> comprarApunte() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
        
    }
    /**
     * Metodo RESTFul que enuva la contraseña del usuario por una nueva creada 
     * automaticamente y informa al email del usuario.
     * @param login El login del usuario al que se refiere.
     * @return Retorna un true si todo a salido bien.
     */
    @GET
    @Path("passwordForgot/{login}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean passwordForgot(@PathParam("login") String login){
        boolean resultado=false;
        try{
            User user=ejbUser.findUserByLogin(login);
            Cliente cliente=ejb.findCliente(user.getId());
            ejb.passwordForgot(cliente);
            resultado=true;
            Date date = new Date();
            cliente.setUltimoCambioContrasenia(date);
            ejb.editCliente(cliente);
        }catch(SelectException ex){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> passwordForgot() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }catch(UserNoExistException ex){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> passwordForgot() ERROR: "+ex.getMessage());
            throw new NotFoundException(ex.getMessage());
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> passwordForgot() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que que permite iniciar sesión a un cliente.
     * @param login El logind del cliente.
     * @param contrasenia La contraseña del cliente.
     * @return Los datos del cliente
     */
    @GET
    @Path("iniciarSesion/{login}/{contrasenia}")
    @Produces({MediaType.APPLICATION_XML})
    public Cliente iniciarSesion(@PathParam("login") String login,@PathParam("contrasenia")String contrasenia)  {
        Cliente usuarioComprobado = new Cliente();
        try {
            String contrasenia2 =encriptador.descriptar(contrasenia);
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("LA CONTRASEÑA!!!!!"+contrasenia2);
            ejbUser.findUserByLogin(login);
            
            usuarioComprobado.setLogin(login);
            usuarioComprobado.setContrasenia(contrasenia2);
            usuarioComprobado = (Cliente) ejbUser.contraseniaCorrecta(usuarioComprobado);
            
        } catch (WrongPasswordException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new NotAuthorizedException (ex.getMessage());
        }catch(SelectException ex){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException (ex.getMessage());
        } catch (UserNoExistException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new NotFoundException (ex.getMessage());
        } catch (DescriptarException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException (ex.getMessage());
        }
        
        return usuarioComprobado;
    }
    /**
     * Es la versión android para iniciar sesión un cliente.
     * @param login El login del cliente.
     * @param contrasenia La contraseña de la cuenta del cliente.
     * @return  Los datos del cliente.
     */
    @GET
    @Path("iniciarSesionAndroid/{login}/{contrasenia}")
    @Produces({MediaType.APPLICATION_XML})
    public Cliente iniciarSesionAndroid(@PathParam("login") String login,@PathParam("contrasenia")String contrasenia)  {
        Cliente usuarioComprobado = new Cliente();
        try {
            String contrasenia2 =encriptador.descriptarAndroid(contrasenia);
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("LA CONTRASEÑA!!!!!"+contrasenia2);
            ejbUser.findUserByLogin(login);
            
            usuarioComprobado.setLogin(login);
            usuarioComprobado.setContrasenia(contrasenia2);
            usuarioComprobado = (Cliente) ejbUser.contraseniaCorrecta(usuarioComprobado);
            
        } catch (WrongPasswordException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new NotAuthorizedException (ex.getMessage());
        }catch(SelectException ex){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException (ex.getMessage());
        } catch (UserNoExistException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new NotFoundException (ex.getMessage());
        } catch (DescriptarException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> iniciarSesion() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException (ex.getMessage());
        }
        
        return usuarioComprobado;
    }
    /**
     * Retorna la clave publica de la aplicación.
     * @return La llave en hexadecimal
     */
    @GET
    @Path("publicKey")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPublicKey(){
        String resultado=null;
        try{
            KeyGetter kg=new KeyGetter();
            resultado=kg.getLlavePublica();
        }catch(Exception ex){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> getPublicKey() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
        return resultado;
    }
    
}
