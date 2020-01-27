package service;

import ejb.MateriaEJBLocal;
import entity.Materia;
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
 * Clase de servicio RESTful de la entidad Materia.
 * @author Luis
 */
@Path("materia")
public class MateriaFacadeREST {
    /**
     * Objeto que maneja la lógica del CRUD de Materia.
     */
    @EJB
    private MateriaEJBLocal ejb;
    /**
     * Método para crear una materia en la base de datos.
     * @param materia Objeto materia para insertar en la base de datos.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Materia materia) {
        try{
            materia.setIdMateria(null);
            ejb.createMateria(materia);
        }catch(CreateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("MateriaREST create()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * Método para editar una materia de la base de datos.
     * @param materia Objeto materia editado.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void edit(Materia materia) {
        try{
            ejb.editMateria(materia);
        }catch(UpdateException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("MateriaREST edit()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * Método para eliminar una materia de la base de datos.
     * @param id Id de la materia que se desea eliminar.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try{
            ejb.removeMateria(ejb.findMateria(id));
        }catch(DeleteException | SelectException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("MateriaREST remove()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    /**
     * Método para buscar una materia de la base de datos.
     * @param id Id de la materia que se desea buscar.
     * @return Objeto materia buscado.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Materia find(@PathParam("id") Integer id) {
        Materia materia = null;
        try{
            materia = ejb.findMateria(id);
        }catch(SelectException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("MateriaREST find()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return materia;
    }
    /**
     * Método para buscar todas las materias de la base de datos.
     * @return Colección con todas las materias.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Set<Materia> findAll() {
        Set<Materia> materias = null;
        try{
            materias = ejb.findAllMateria();
        }catch(SelectCollectionException e){
            Logger.getLogger(ApunteFacadeREST.class.getName()).severe("MateriaREST findAll()"+e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return materias;
    }
}
