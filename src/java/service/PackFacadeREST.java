/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import ejb.PackEJBLocal;
import entity.Pack;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
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
 * @author Luis
 */
@Path("pack")
public class PackFacadeREST {

    @EJB
    private PackEJBLocal ejb;
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Pack pack) {
        try{
            ejb.createPack(pack);
        }catch(CreateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST create()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void edit(Pack pack) {
        try{
            ejb.editPack(pack);
        }catch(UpdateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST edit()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try{
            ejb.removePack(ejb.findPack(id));
        }catch(DeleteException | SelectException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST remove()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Pack find(@PathParam("id") Integer id) {
        Pack pack = null;
        try{
            pack = ejb.findPack(id);
        }catch(SelectException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST find()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return pack;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Set<Pack> findAll() {
        Set<Pack> packs = null;
        try{
            packs = ejb.findAllPack();
        }catch(SelectCollectionException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST findAll()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return packs;
    }
    @PUT
    @Path("insertarApunte/{idApunte}")
    @Consumes(MediaType.APPLICATION_XML)
    public void insertarApunte(Pack pack,@PathParam("idApunte") Integer idApunte) {
        try{
            ejb.insertarApunte(pack, idApunte);
        }catch(UpdateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST insertarApunte()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    @PUT
    @Path("eliminarApunte/{idApunte}")
    @Consumes(MediaType.APPLICATION_XML)
    public void eliminarApunte(Pack pack,@PathParam("idApunte") Integer idApunte) {
        try{
            ejb.eliminarApunte(pack, idApunte);
        }catch(UpdateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST eliminarApunte()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
}
