package service;

import ejb.PackEJBLocal;
import entity.Oferta;
import entity.Pack;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Date;
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
 * Clase de servicio RESTful de la entidad Pack.
 * @author Luis
 */
@Path("pack")
public class PackFacadeREST {
    /**
     * Objeto que maneja la lógica del CRUD de Pack.
     */
    @EJB
    private PackEJBLocal ejb;
    /**
     * Método para crear un pack en la base de datos.
     * @param pack Objeto pack para insertar en la base de datos.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Pack pack) {
        try{
            pack.setIdPack(null);
            pack.setFechaModificacion(new Date());
            ejb.createPack(pack);
        }catch(CreateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST create()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * Método para editar un pack de la base de datos.
     * @param pack Objeto pack editado.
     */
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
    /**
     * Método para eliminar un pack de la base de datos.
     * @param id Id del pack que se desea eliminar.
     */
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
    /**
     * Método para buscar un pack de la base de datos.
     * @param id Id del pack que se desea buscar.
     * @return Objeto pack buscado.
     */
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
    /**
     * Método para buscar todos los packs de la base de datos.
     * @return Colección con todos los packs.
     */
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
    /**
     * Método para insertar un apunte a un pack.
     * @param pack Objeto pack al que se le quiere insertar un apunte.
     * @param idApunte Id del apunte que se quiere insertar.
     */
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
    /**
     * Método para eliminar un apunte de un pack.
     * @param pack Objeto pack al que se el quiere eliminar un apunte.
     * @param idApunte Id del apunte que se quiere eliminar.
     */
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
    /**
     * Método para obtener la oferta del pack.
     * @param idPack Id del pack del que queremos obtener la oferta.
     * @return Objeto oferta del pack.
     */
    @GET
    @Path("oferta/{idPack}")
    @Produces(MediaType.APPLICATION_XML)
    public Oferta dameOferta(@PathParam("idPack") Integer idPack) {
        Oferta oferta = null;
        try{
            oferta = ejb.dameOferta(idPack);
        }catch(SelectException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("PackREST dameOferta()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return oferta;
    }
}
