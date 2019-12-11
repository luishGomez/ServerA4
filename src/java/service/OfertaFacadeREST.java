/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Oferta;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Stateless
@Path("oferta")
public class OfertaFacadeREST{

    @EJB
    private OfertaEJBLocal ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void createOferta(Oferta oferta) {
        try {
            ejb.createOferta(oferta);
        } catch (CreateException ex) {
            Logger.getLogger(OfertaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @GET
    @Path("{idOferta}")
    @Produces({MediaType.APPLICATION_XML})
    public Oferta findOfertaById(@PathParam("idOferta") Integer idOferta)  throws SelectException{
        Oferta oferta = null;
        try {
            oferta = ejb.findOfertaById(idOferta);
        } catch (SelectException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oferta;
    }
    @DELETE
    @Path("idOferta/{idOferta}")
    public void deleteOferta(@PathParam("idOferta") Integer idOferta) {
        try {
            try {
                ejb.deleteOferta(ejb.findOfertaById(idOferta));
            } catch (DeleteException ex) {
                Logger.getLogger(OfertaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SelectException ex) {
            Logger.getLogger(OfertaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Oferta> findAllOfertas(){
        List<Oferta> ofertas = null;
        try {
            ofertas = ejb.findAllOfertas();
        } catch (SelectCollectionException ex) {
            Logger.getLogger(OfertaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ofertas;
    }
}
