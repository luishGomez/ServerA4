package ejb;

import entity.Materia;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Interfaz EJB Local que maneja el CRUD de Materia.
 * @author Luis
 */
@Stateless
public class MateriaEJB implements MateriaEJBLocal{
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.MateriaEJB");
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;
    /**
     * Crea una materia.
     * @param materia Objeto materia para crear.
     * @throws CreateException Salta si hay algun error en el proceso de la creación.
     */
    @Override
    public void createMateria(Materia materia) throws CreateException {
        try{
            em.persist(materia);
        }catch (Exception e){
            LOGGER.severe("createMateria()" + e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * Edita una materia.
     * @param materia Objeto materia editado.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    @Override
    public void editMateria(Materia materia) throws UpdateException {
        try{
            em.merge(materia);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("editMateria()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Elimina una materia.
     * @param materia Objeto materia para eliminar.
     * @throws DeleteException Salta si hay algun error al borrar.
     */
    @Override
    public void removeMateria(Materia materia) throws DeleteException {
        try{
            em.remove(em.merge(materia));
            em.flush();
        }catch (Exception e){
            LOGGER.severe("removeMateria()" + e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    /**
     * Busca una materia.
     * @param idMateria Id de la materia.
     * @return Objeto materia buscado.
     * @throws SelectException Salta si a ocurrido un error en la búsqueda.
     */
    @Override
    public Materia findMateria(Integer idMateria) throws SelectException {
        Materia materia = null;
        try{
            materia = em.find(Materia.class, idMateria);
        }catch(Exception e){
            LOGGER.severe("findMateria()" + e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return materia;
    }
    /**
     * Busca todas las materias.
     * @return Collección con todas las materias.
     * @throws SelectCollectionException Salta si hay un error en la búsqueda de más de un dato.
     */
    @Override
    public Set<Materia> findAllMateria() throws SelectCollectionException {
        Set<Materia> materias = null;
        try{
            materias = new HashSet<>(em.createNamedQuery("findAllMateria").getResultList());
        }catch(Exception e){
            LOGGER.severe("findAllMateria()" + e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return materias;
    }
}
