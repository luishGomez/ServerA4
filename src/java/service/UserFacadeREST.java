/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.UpdateException;
import exception.UserNoExistException;
import exception.WrongPasswordException;
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
 *
 * @author 2dam
 */
@Path("user")
public class UserFacadeREST {
    private static final Logger LOGGER = Logger.getLogger("servera4db.service.UserFacadeRest");
    
    @EJB
    private UsuarioEJBLocal ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void createUser(User usuario) {
        try {
            ejb.createUser(usuario);
        } catch (CreateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).severe(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    @PUT
    @Path("actualizar")
    @Consumes({MediaType.APPLICATION_XML})
    public void updateUser(User usuario) {
        try {
            ejb.updateUser(usuario);
        } catch (UpdateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).severe(ex.getMessage());
        }
    }
    @GET
    @Path("buscarPorLogin/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public User findUserByLogin(@PathParam("login") String login) {
        User usuario = null;
        try {
            usuario = ejb.findUserByLogin(login);
        } catch (UserNoExistException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).severe(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return usuario;
    }
    
    @GET
    @Path("contrasenia/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public User contraseniaCorrecta(@PathParam("login") String login,@PathParam("contrasenia")String contrasenia) throws WrongPasswordException {
        User usuarioComprobado = null;
       
            try {
                usuarioComprobado.setLogin(login);
                usuarioComprobado.setContrasenia(contrasenia);
                usuarioComprobado = ejb.contraseniaCorrecta(usuarioComprobado);
            } catch (WrongPasswordException ex) {
                LOGGER.log(Level.SEVERE,
                "UserRESTful service: Exception deleting user by id, {0}",
                ex.getMessage());
                throw new InternalServerErrorException(ex);
            }  
       
        return usuarioComprobado;
    }
}
