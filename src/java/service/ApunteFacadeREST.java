/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import ejb.ApunteEJBLocal;
import entity.Apunte;
import entity.Cliente;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
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
 * Es la clase de servicio Web RESTful que expone las operaciones CRUD de {@link  Apunte} Entity.
 * @author Ricardo Peinado Lastra
 */
@Path("apunte")
public class ApunteFacadeREST  {
    private static final Logger LOGGER =
            Logger.getLogger("javafxserverside");
    /**
     * La referencia al objeto que maneja la logica de negocio de {@link Apunte}.
     */
    @EJB
    private ApunteEJBLocal ejb;
    
    /**
     * Metodo RESTFull para crear un {@link Apunte}.
     * @param apunte El objeto Apunte con sus datos.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Apunte apunte) {
        try {
            
            ejb.createApunte(apunte);
        } catch (CreateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> create() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para editar un {@link  Apunte}.
     * @param apunte El objeto Apunte con sus datos.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit( Apunte apunte) {
        try {
            ejb.editApunte(apunte);
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> edit() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para eliminar un {@link Apunte}.
     * @param id El ID de un {@link Apunte}.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find apunte by id={0}.",id);
            try {
                ejb.removeApunte(ejb.findApunte(id));
            } catch (SelectException ex) {
                LOGGER.log(Level.SEVERE,
                        "UserRESTful service: Exception reading apunte by id, {0}",
                        ex.getMessage());
            throw new InternalServerErrorException(ex);
            }
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                        "UserRESTful service: Exception deleting apunte by id, {0}",
                        ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para buscar un {@link Apunte} por su ID.
     * @param id El ID de un {@link Apunte}.
     * @return Retorna un objeto Apunte con sus datos.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Apunte find(@PathParam("id") Integer id) {
        Apunte resultado = null;
        try {
            resultado=ejb.findApunte(id);
        } catch (SelectException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> find() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
        
    }
    /**
     * Metodo RESTFul para buscar todos los Apuntes.
     * @return Retorna una lista de {@link Apunte} existentes.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Apunte> findAll() {
        Set <Apunte> resultado = null;
        try {
            resultado=ejb.findAllApuntes();
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> findAll() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
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
    /**
     * Metodo RESTFul que busca los apuntes creados por un {@link Cliente}.
     * @param id El ID de un{@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     */
    @GET
    @Path("creador/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Apunte> getApuntesByCreador(@PathParam("id") Integer id) {
        Set <Apunte> resultado = null;
        try {
            resultado=ejb.getApuntesByCreador(id);
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> getApuntesByCreador() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que busca los apuntes de un comprado.
     * @param id El ID de un{@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     */
    @GET
    @Path("cliente/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Set<Apunte> getApuntesByComprador(@PathParam("id") Integer id) {
        Set <Apunte> resultado = null;
        try {
            resultado=ejb.getApuntesByComprador(id);
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> getApuntesByComprador() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que inserta una votación de un {@link Apunte}.
     * @param idCliente El ID de un {@link Cliente}.
     * @param like 1: Si es un like | -1: Si es dislike.
     * @param apunte Es un objeto {@link Apunte} con sus datos.
     */
    @PUT
    @Path("votar/{idCliente}/{like}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void votacion(@PathParam("idCliente") Integer idCliente,@PathParam("like") Integer like, Apunte apunte) {
        try {
            ejb.votacion(idCliente, like, apunte);
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ApunteFacadeRESTful -> votacion() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /*
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
    
    @DELETE
    @Path("borrarApunte/{id}")
    public void borrarApunte(@PathParam("id") Integer id) {
    try {
    ejb.borrarApunte(id);
    } catch (DeleteException ex) {
    Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
    } catch (YaEstaVendidoException ex) {
    Logger.getLogger(ApunteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    */
    
    /* DEL CLIENTE !!!!!
    @GET
    @Path("votantes/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public List <Cliente> getVotantesId(@PathParam("id") Integer id) {
    return super.getVotantesId(id);
    }
    */
    
    
    
}
