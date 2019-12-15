/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import ejb.ClienteEJBLocal;
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
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *Es la clase de servicio Web RESTful que expone las operaciones CRUD de {@link  Cliente} Entity.
 * @author Ricardo Peinado Lastra
 */

@Path("cliente")
public class ClienteFacadeREST  {
    /**
     * La referencia al objeto que maneja la logica de negocio de {@link Apunte}
     */
    @EJB
    private ClienteEJBLocal ejb;
    /**
     * Metodo RESTFul para crear un {@link Cliente}.
     * @param cliente El objeto Cliente con sus datos.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Cliente cliente) {
        try {
            ejb.createCliente(cliente);
        } catch (CreateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> create() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para editar un {@link Cliente} ya existente.
     * @param cliente El bojecto Cliente con sus datos.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void edit( Cliente cliente) {
        try {
            ejb.editCliente(cliente);
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> edit() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para eliminar un {@link Cliente} ya existente.
     * @param id El ID de un objeto {@link Cliente}.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ejb.removeCliente(id);
        } catch (DeleteException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> remove() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * Metodo RESTFul para buscar un {@link Cliente} por su ID.
     * @param id El ID de un {@link Cliente}.
     * @return Retorna un objeto de tipo cliente con sus datos.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Cliente find(@PathParam("id") Integer id) {
        Cliente resultado=null;
        try {
            resultado= ejb.findCliente(id);
        } catch (SelectException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> find() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo para buscar todos los clientes.
     * @return Retorna una lista de {@link Cliente}-
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Set<Cliente> findAll() {
        Set <Cliente> resultado=null;
        try {
            resultado= ejb.findAllClientes();
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> findAll() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
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
    /**
     * Metodo RESTFul que busca los cliente que han votado un apunte en especifico.
     * @param id El ID de un {@link Apunte}.
     * @return Retorna una lista de {@link Cliente}.
     */
    @GET
    @Path("votantes/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public List <Cliente> getVotantesId(@PathParam("id") Integer id) {
        List <Cliente> resultado =null;
        try {
            resultado=ejb.getVotantesId(id);
        } catch (SelectCollectionException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> getVotantesId() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return resultado;
    }
    /**
     * Metodo RESTFul que actualiza la contraseña de un {@link Cliente}.
     * @param cliente El objeto cliente con sus datos.
     */
    @PUT
    @Path("password")
    @Consumes(MediaType.APPLICATION_XML)
    public void actualizarContrasenia(Cliente cliente) {
        try {
            ejb.actualizarContrasenia(cliente);
        } catch (UpdateException ex) {
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("ClienteFacadeRESTful -> actualizarContrasenia() ERROR: "+ex.getMessage());
            throw new InternalServerErrorException(ex);
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
