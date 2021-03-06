package service;

import ejb.CompraEJBLocal;
import entity.Compra;
import entity.CompraId;
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
import javax.ws.rs.core.PathSegment;

/**
 * Clase de servicio RESTful de la entidad Compra.
 * @author Luis
 */
@Path("compra")
public class CompraFacadeREST {
    /**
     * Objeto que maneja la lógica del CRUD de Compra.
     */
    @EJB
    private CompraEJBLocal ejb;
    
    private CompraId getPrimaryKey(PathSegment pathSegment) {
        /*
        * pathSemgent represents a URI path segment and any associated matrix parameters.
        * URI path part is supposed to be in form of 'somePath;clienteId=clienteIdValue;apunteId=apunteIdValue'.
        * Here 'somePath' is a result of getPath() method invocation and
        * it is ignored in the following code.
        * Matrix parameters are used as field names to build a primary key instance.
        */
        entity.CompraId key = new entity.CompraId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> clienteId = map.get("clienteId");
        if (clienteId != null && !clienteId.isEmpty()) {
            key.setClienteId(new java.lang.Integer(clienteId.get(0)));
        }
        java.util.List<String> apunteId = map.get("apunteId");
        if (apunteId != null && !apunteId.isEmpty()) {
            key.setApunteId(new java.lang.Integer(apunteId.get(0)));
        }
        return key;
    }
    /**
     * Método para crear una compra en la base de datos.
     * @param idApunte Id del apunte comprado.
     * @param idCliente Id del comprador.
     * @param compra Objeto compra.
     */
    @PUT
    @Path("comprar/{idApunte}/{idCliente}")
    @Consumes(MediaType.APPLICATION_XML)
    public void createCompra(@PathParam("idApunte") Integer idApunte,@PathParam("idCliente") Integer idCliente, Compra compra) {
        try{
            ejb.createCompra(idApunte, idCliente);
        }catch(CreateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("CompraREST create()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * Método para editar una compra de la base de datos.
     * @param compra Objeto compra editado.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void edit(Compra compra) {
        try{
            ejb.editCompra(compra);
        }catch(UpdateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("CompraREST edit()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * Método para eliminar una compra de la base de datos.
     * @param id Id de la compra que se desea eliminar.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.CompraId key = getPrimaryKey(id);
        try{
            ejb.removeCompra(ejb.findCompra(key));
        }catch(SelectException | DeleteException e){
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * Método para buscar todas las materias de la base de datos.
     * @return Colección con todas las compras.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Set<Compra> findAll() {
        Set<Compra> compras = null;
        try{
            compras = ejb.findAllCompra();
        }catch(SelectCollectionException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("CompraREST findAll()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return compras;
    }
    /**
     * Método para buscar las compras por el id del cliente de la base de datos.
     * @param idCliente Id del cliente que se desea buscar las compras.
     * @return Colección con las compras buscadas.
     */
    @GET
    @Path("byCliente/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Set<Compra> findAllCompraByClienteId(@PathParam("id") Integer idCliente) {
        Set<Compra> compras = null;
        try{
            compras = ejb.findAllCompraByClienteId(idCliente);
        }catch(SelectCollectionException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("CompraREST findAllCompraByClienteId()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return compras;
    }
}
