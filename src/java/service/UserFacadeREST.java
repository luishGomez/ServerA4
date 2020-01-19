/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import ejb.UsuarioEJBLocal;
import encriptaciones.Encriptador;
import entity.User;
import exception.CreateException;
import exception.DescriptarException;
import exception.EncriptarException;
import exception.SelectException;
import exception.UpdateException;
import exception.UserNoExistException;
import exception.WrongPasswordException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
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
 *
 * @author Sergio
 */
@Path("user")
public class UserFacadeREST {
    private static final Logger LOGGER = Logger.getLogger("servera4db.service.UserFacadeRest");
    private Encriptador encriptador = new Encriptador();
    
    @EJB
    private UsuarioEJBLocal ejb;
    /**
     * Metodo Post de Restful para crear Usuario a partir de un xml
     * @param usuario  Objeto que contiene los datos del usuario
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void createUser(User usuario) {
        try {
            usuario.setId(null);
            ejb.createUser(usuario);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception create user, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo Put de Restful para actualizar Usuario a partir de un xml
     * @param usuario Objeto que contiene los datos del usuario
     */
    @PUT
    @Path("actualizar")
    @Consumes({MediaType.APPLICATION_XML})
    public void updateUser(User usuario) {
        try {
            ejb.updateUser(usuario);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception update user, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    
    /**
     * Metodo Get de Restful para buscar Usuario a partir de un xml
     * @param login Login del Objeto a leer
     * @param contrasenia Contrasenia del Objeto a leer
     * @return Usuario con login existente y contraseña correcta
     * @throws WrongPasswordException si hay una excepcion durante el proceso
     */    
    @GET
    @Path("iniciarSesion/{login}/{contrasenia}")
    @Produces({MediaType.APPLICATION_XML})
    public User iniciarSesion(@PathParam("login") String login,@PathParam("contrasenia")String contrasenia) {
        User usuarioComprobado = new User();
        try {
            String contrasenia2=encriptador.descriptar(contrasenia);
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("LA CONTRASEÑA!!!!!"+contrasenia2);
            usuarioComprobado.setLogin(login);
            usuarioComprobado.setContrasenia(contrasenia2);
            usuarioComprobado = ejb.findUserByLogin(login);
            
            usuarioComprobado = new User();
            usuarioComprobado.setLogin(login);
            usuarioComprobado.setContrasenia(contrasenia2);
            usuarioComprobado = ejb.contraseniaCorrecta(usuarioComprobado);
            
        } catch (WrongPasswordException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception (WrongPasswordException) comprobar usuario by login and contrasenia, {0}",
                    ex.getMessage());
            throw new NotAuthorizedException (ex.getMessage());
        }catch(SelectException  ex){
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception (SelectException) comprobar usuario by login and contrasenia, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException (ex.getMessage());
        } catch (UserNoExistException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception (UserNoExistException) comprobar usuario by login and contrasenia, {0}",
                    ex.getMessage());
            throw new NotFoundException (ex.getMessage());
        } catch (DescriptarException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception el descriptar",
                    ex.getMessage());
            throw new InternalServerErrorException (ex.getMessage());
        }
        
        return usuarioComprobado;
    }
}
