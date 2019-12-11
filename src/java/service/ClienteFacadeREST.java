/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Apunte;
import entity.Cliente;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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

@Path("cliente")
public class ClienteFacadeREST  {
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.ClienteFacadeREST");
    
    @EJB
    private ClienteEJBLocal ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cliente cliente) {
        try {
            ejb.createCliente(cliente);
        } catch (CreateException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit( Cliente cliente) {
        try {
            ejb.editCliente(cliente);
        } catch (UpdateException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ejb.removeCliente(id);
        } catch (DeleteException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cliente find(@PathParam("id") Integer id) {
        Cliente resultado=null;
        try {
           resultado= ejb.findCliente(id);
        } catch (SelectException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Cliente> findAll() {
        Set <Cliente> resultado=null;
        try {
           resultado= ejb.findAllClientes();
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    /*
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    */
    
    @GET
    @Path("votantes/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public List <Cliente> getVotantesId(@PathParam("id") Integer id) {
        List <Cliente> resultado =null;
        try {
            resultado=ejb.getVotantesId(id);
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    @PUT
    @Path("password")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void actualizarContrasenia(Cliente cliente) {
        try {
            ejb.actualizarContrasenia(cliente);
        } catch (UpdateException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void actualizarCliente(Cliente cliente) {
        try {
            ejb.actualizarCliente(cliente);
        } catch (UpdateException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    /* SE TRANSALDO
    @GET
    @Path("misApuntes/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set <Apunte> getMisApuntes(@PathParam("id") Integer id) {
        return super.getMisApuntes(id);
    }
    
    */
    
    
}
