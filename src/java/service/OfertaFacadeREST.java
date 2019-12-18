/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import ejb.OfertaEJBLocal;
import entity.Oferta;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.List;
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
 * @author Sergio
 */

@Path("oferta")
public class OfertaFacadeREST{
    private static final Logger LOGGER =
            Logger.getLogger("javafxserverside");
    
    @EJB
    private OfertaEJBLocal ejb;
    
    /**
     * Metodo Post de Restful para crear Oferta a partir de un xml
     * @param oferta Objeto que contiene los datos de la oferta
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void createOferta(Oferta oferta) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: create {0}.",oferta);
            ejb.createOferta(oferta);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception creating oferta, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo Get de Restful para leer una Oferta a partir de un xml
     * @param idOferta Id del Objeto a leer
     * @return Objeto Oferta con sus datos
     * @throws SelectException
     */
    @GET
    @Path("{idOferta}")
    @Produces({MediaType.APPLICATION_XML})
    public Oferta findOfertaById(@PathParam("idOferta") Integer idOferta)  throws SelectException{
        Oferta oferta = null;
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",idOferta);
            oferta = ejb.findOfertaById(idOferta);
        } catch (SelectException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading oferta by idOferta, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return oferta;
    }
    /**
     * Metodo Delete de Restful para borrar una Oferta a partir de un xml
     * @param idOferta Id del Objeto a borrar
     */
    @DELETE
    @Path("{idOferta}")
    public void deleteOferta(@PathParam("idOferta") Integer idOferta) {
        try {
            try {
                LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",idOferta);
                ejb.deleteOferta(ejb.findOfertaById(idOferta));
            } catch (SelectException ex) {
                LOGGER.log(Level.SEVERE,
                        "UserRESTful service: Exception reading user by id, {0}",
                        ex.getMessage());
                throw new InternalServerErrorException(ex);
            }
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception deleting oferta by idOferta, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo Get de Restful para leer todas las Ofertas a partir de un xml
     * @return Lista de Objetos Oferta con sus datos
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Oferta> findAllOfertas(){
        List<Oferta> ofertas = null;
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.");
            ofertas = ejb.findAllOfertas();
        } catch (SelectCollectionException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading list ofertas by idOferta, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return ofertas;
    }
    /**
     * Metodo Put de Restful para actualizar todas una Oferta a partir de un xml
     * @param oferta Objeto que contiene los datos a actualizar
     */
    @PUT
    @Path("actualizar")
    @Consumes({MediaType.APPLICATION_XML})
    public void updateOferta(Oferta oferta) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",oferta);
            ejb.updateOferta(oferta);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception update oferta by Object oferta, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    @PUT
    @Path("insertarPack/{idPack}")
    @Consumes(MediaType.APPLICATION_XML)
    public void insertarPack(Oferta oferta,@PathParam("idPack") Integer idPack) {
        try{
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",oferta.getIdOferta());
            ejb.insertarPack(oferta, idPack);
        }catch(UpdateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST insertarApunte()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    @PUT
    @Path("eliminarPack/{idPack}")
    @Consumes(MediaType.APPLICATION_XML)
    public void eliminarPack(Oferta oferta,@PathParam("idPack") Integer idPack) {
        try{
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",oferta.getIdOferta());
            ejb.eliminarPack(oferta, idPack);
        }catch(UpdateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST eliminarApunte()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
}
