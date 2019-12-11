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

@Path("apunte")
public class ApunteFacadeREST  {
     private static final Logger LOGGER = Logger.getLogger("ServerA4.service.ApunteFacadeREST");
    @EJB
    private ApunteEJBLocal ejb;

    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Apunte apunte) {
         try {
             ejb.createApunte(apunte);
         } catch (CreateException ex) {
             LOGGER.log(Level.SEVERE, null, ex);
         }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit( Apunte apunte) {
         try {
             ejb.editApunte(apunte);
         } catch (UpdateException ex) {
             Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
         try {
             ejb.removeApunte(id);
         } catch (DeleteException ex) {
             Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Apunte find(@PathParam("id") Integer id) {
        Apunte resultado = null;
         try {
             resultado=ejb.findApunte(id);
         } catch (SelectException ex) {
             Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
         }
        return resultado;

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Apunte> findAll() {
        Set <Apunte> resultado = null;
         try {
             resultado=ejb.findAllApuntes();
         } catch (SelectCollectionException ex) {
             Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
         }
        return resultado;
    }
    /*
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Apunte> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    */
    /*
    @GET
    @Path("archivo/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public byte[] getArchivoById(@PathParam("id") Integer id) {
        return super.getArchivoById(id);
    }
    */
    @GET
    @Path("creador/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Apunte> getApuntesByCreador(@PathParam("id") Integer id) {
        Set <Apunte> resultado = null; 
        try {             
             resultado=ejb.getApuntesByCreador(id);             
         } catch (SelectCollectionException ex) {
             Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
         }
         return resultado;
    }
    @GET
    @Path("cliente/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Apunte> getApuntesByComprador(@PathParam("id") Integer id) {
        Set <Apunte> resultado = null; 
        try {
            resultado=ejb.getApuntesByComprador(id);
            } catch (SelectCollectionException ex) {
             Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
         }
         return resultado;
     }
    @GET
    @Path("misApuntes/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set <Apunte> getMisApuntes(@PathParam("id") Integer id) {
        Set <Apunte> resultado = null; 
        try {
            resultado=ejb.getMisApuntes(id);
            } catch (SelectCollectionException ex) {
             Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
         }
         return resultado;
    }
       
    
    
    
    /* DEL CLIENTE !!!!!
    @GET
    @Path("votantes/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public List <Cliente> getVotantesId(@PathParam("id") Integer id) {
        return super.getVotantesId(id);
    }
    */
    
   
    
}
