/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import ejb.UsuarioEJBLocal;
import entity.User;
import exception.CreateException;
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
     * @param login El Login del Usuario a ser encontrado
     * @return Objeto Usuario con sus datos
     * @throws exception.SelectException
     */
    /*
    @GET
    @Path("buscarPorLogin/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public User findUserByLogin(@PathParam("login") String login) throws SelectException{
    User usuario = null;
    try {
    usuario = ejb.findUserByLogin(login);
    } catch (UserNoExistException ex) {
    LOGGER.log(Level.SEVERE,
    "UserRESTful service: Exception find user by login, {0}",
    ex.getMessage());
    throw new InternalServerErrorException(ex);
    }
    return usuario;
    }
    */
    /**
     * Metodo Get de Restful para buscar Usuario a partir de un xml
     * @param login Login del Objeto a leer
     * @param contrasenia Contrasenia del Objeto a leer
     * @return Usuario con login existente y contraseña correcta
     * @throws WrongPasswordException si hay una excepcion durante el proceso
     */
    /*
    @GET
    @Path("contrasenia/{login}/{contrasenia}")
    @Produces({MediaType.APPLICATION_XML})
    public User contraseniaCorrecta(@PathParam("login") String login,@PathParam("contrasenia")String contrasenia) throws WrongPasswordException {
    User usuarioComprobado = new User();
    try {
    usuarioComprobado.setLogin(login);
    usuarioComprobado.setContrasenia(contrasenia);
    usuarioComprobado = ejb.contraseniaCorrecta(usuarioComprobado);
    } catch (WrongPasswordException ex) {
    LOGGER.log(Level.SEVERE,
    "UserRESTful service: Exception comprobar usuario by login and contrasenia, {0}",
    ex.getMessage());
    throw new InternalServerErrorException(ex);
    }
    
    return usuarioComprobado;
    }
    */
    @GET
    @Path("iniciarSesion/{login}/{contrasenia}")
    @Produces({MediaType.APPLICATION_XML})
    public User iniciarSesion(@PathParam("login") String login,@PathParam("contrasenia")String contrasenia) throws WrongPasswordException {
        User usuarioComprobado = new User();
        try {
            usuarioComprobado.setLogin(login);
            usuarioComprobado.setContrasenia(contrasenia);
            usuarioComprobado = ejb.findUserByLogin(login);
            
            usuarioComprobado = new User();
            usuarioComprobado.setLogin(login);
            usuarioComprobado.setContrasenia(contrasenia);
            usuarioComprobado = ejb.contraseniaCorrecta(usuarioComprobado);
            
        } catch (WrongPasswordException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception (WrongPasswordException) comprobar usuario by login and contrasenia, {0}",
                    ex.getMessage());
            throw new NotAuthorizedException (ex.getMessage());
        }catch(SelectException ex){
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception (SelectException) comprobar usuario by login and contrasenia, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException (ex.getMessage());
        } catch (UserNoExistException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception (UserNoExistException) comprobar usuario by login and contrasenia, {0}",
                    ex.getMessage());
            throw new NotFoundException (ex.getMessage());
        }
        
        return usuarioComprobado;
    }
}
