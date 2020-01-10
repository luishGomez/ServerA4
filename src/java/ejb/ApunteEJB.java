/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb;

import entity.Apunte;
import entity.Cliente;
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
import javax.persistence.Query;

/**
 * La clase que se encarga de la logica de los <b>apuntes</b> de la aplicacion.
 * @author Ricardo Peinado Lastra
 */
@Stateless
public class ApunteEJB implements ApunteEJBLocal{
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.AbstractFacade");
    /**
     * El objeto Entity Manager
     */
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;
    
    /**
     * Crea un apunte.
     * @param apunte {@link Apunte} es el objeto que tiene los datos de un nuevo apunte.
     * @throws CreateException Salta si hay algun error en el proceso de la creación.
     */
    @Override
    public void createApunte(Apunte apunte) throws CreateException {
        try{
            em.persist(apunte);
        }catch (Exception e){
            LOGGER.severe("ApunteEJB -> createApunte() "+e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * Edita un apunte ya existente.
     * @param apunte {@link Apunte} es el objeto con los datos para modificar.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    @Override
    public void editApunte(Apunte apunte) throws UpdateException {
        try{
            em.merge(apunte);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("ApunteEJB -> editApunte() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Elimina un apunte ya existente
     * @param apunte {@link Apunte} es el objeto con los datos a borrar.
     * @throws DeleteException Salta si hay algun error al borrar.
     */
    @Override
    public void removeApunte(Apunte apunte) throws DeleteException {
        try{
            
            for(Cliente cliente:apunte.getVotantes())
                cliente.getMisVotaciones().remove(apunte);
            
            Query q1 = em.createQuery ("DELETE FROM Compra a WHERE a.idCompra.apunteId = :idApunte");
            q1.setParameter ("idApunte",apunte.getIdApunte());
            int deleted1 = q1.executeUpdate ();
            
            
            Query q = em.createQuery ("DELETE FROM Apunte a WHERE a.idApunte = :idApunte");
            q.setParameter ("idApunte",apunte.getIdApunte());
            int deleted = q.executeUpdate ();
            
            em.flush();
        }catch (Exception e){
            LOGGER.severe("ApunteEJB -> removeApunte() "+e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    /**
     * Busca un apunte por su ID.
     * @param idApunte Es el identificador de un apunte.
     * @return Retorna un {@link Apunte}.
     * @throws SelectException Salta si a ocurrido un error en la busqueda.
     */
    @Override
    public Apunte findApunte(Integer idApunte) throws SelectException {
        Apunte apunte=null;
        try{
            apunte = em.find(Apunte.class, idApunte);
        }catch (Exception e){
            LOGGER.severe("ApunteEJB -> findApunte() "+e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return apunte;
    }
    /**
     * Busca todos los apuntes existentes.
     * @return Retorna una lista de {@link Apunte}.
     * @throws SelectCollectionException Salta si hay un error en la busqueda de más de un dato.
     */
    @Override
    public Set<Apunte> findAllApuntes() throws SelectCollectionException {
        /*
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
        */
        Set <Apunte> resultado= null;
        try{
            resultado=new HashSet<>(em.createNamedQuery("findAllApuntes").getResultList());
        }catch (Exception e){
            LOGGER.severe("ApunteEJB -> findAllApuntes() "+e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return resultado;
        
    }
    
    /**
     * Devuelve todos los apuntes de un creador.
     * @param id Es el ID de un {@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     * @throws SelectCollectionException Salta si hay un error en la busqueda de más de un dato.
     */
    @Override
    public Set<Apunte> getApuntesByCreador(Integer id) throws SelectCollectionException{
        // List<Apunte> sourceList=(List<Apunte>)em.createNamedQuery("getApuntesByCreador").setParameter("idCliente", id).getResultList();
        Set <Apunte> resultado=null;
        try{
            resultado= new HashSet<>(em.createNamedQuery("getApuntesByCreador").setParameter("idCliente", id).getResultList());
        }catch (Exception e){
            LOGGER.severe("ApunteEJB -> getApuntesByCreador() "+e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return resultado;
    }
    /**
     * Devuelve todos los apuntes comprados por un {@link Cliente}.
     * @param id El el ID de un {@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     * @throws SelectCollectionException
     */
    @Override
    public Set<Apunte> getApuntesByComprador(Integer id)throws SelectCollectionException{
        //List<Apunte> sourceList=(List<Apunte>)em.createNamedQuery("getApuntesByComprador").setParameter("idCliente", id).getResultList();
        Set <Apunte> resultado=null;
        try{
            resultado= new HashSet<>(em.createNamedQuery("getApuntesByComprador").setParameter("idCliente", id).getResultList());
        }catch (Exception e){
            LOGGER.severe("ApunteEJB -> getApuntesByComprador() "+e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return resultado;
    }
    /**
     * Inserta una votación a un {@link Apunte}.
     * @param idCliente El ID de un {@link Cliente}.
     * @param like 1: Si es un like | -1: Si es un dislike.
     * @param apunte Un objecto de tipo {@link Apunte} con todos sus datos.
     * @throws UpdateException Salta si hay un error en la actualización.
     */
    @Override
    public void votacion (Integer idCliente, Integer like, Apunte apunte) throws UpdateException{
        try{
            Apunte apunteActualizado=em.find(Apunte.class, apunte.getIdApunte());
            Cliente cliente=em.find(Cliente.class, idCliente);
            if(like<0){
                apunteActualizado.setDislikeCont(apunteActualizado.getDislikeCont()+1);
            }else{
                apunteActualizado.setLikeCont(apunteActualizado.getLikeCont()+1);
            }
            apunteActualizado.getVotantes().add(cliente);
            em.merge(apunteActualizado);
            em.flush();
        }catch(Exception e){
            LOGGER.severe("ApunteEJB -> votacion() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    @Override
    public int cuantasCompras(Integer idApunte) throws SelectException{
        int resultado=0;
        try{
            Apunte apunte = em.find(Apunte.class, idApunte);
            resultado=apunte.getCompras().size();
        }catch(Exception e){
            LOGGER.severe("ApunteEJB -> cuantasCompras() "+e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return resultado;
    }
    
}
