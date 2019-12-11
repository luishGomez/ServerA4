/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.UserNoExistException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("id/{id}")
    public void deleteUser(@PathParam("id") String id) {
        try {
            try {
                ejb.deleteUser(ejb.findUserByLogin(id));
                
            } catch (UserNoExistException ex) {
                Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DeleteException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //----------------
    @GET
    @Path("login/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public User findUserByLogin(@PathParam("login") String login) {
        User usuario = null;
        try {
            usuario = ejb.findUserByLogin(login);
        } catch (UserNoExistException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }
}
