package ejb;

import entity.Apunte;
import entity.Oferta;
import entity.Pack;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Interfaz EJB Local que maneja el CRUD de Pack.
 * @author Luis
 */
@Stateless
public class PackEJB implements PackEJBLocal {
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.PackEJB");
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;
    /**
     * Crea un pack.
     * @param pack Objeto pack para crear.
     * @throws CreateException Salta si hay algun error en el proceso de la creación.
     */
    @Override
    public void createPack(Pack pack) throws CreateException {
        try{
            em.persist(pack);
        }catch (Exception e){
            LOGGER.severe("createPack()" + e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * Edita un pack.
     * @param pack Objeto pack editado.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    @Override
    public void editPack(Pack pack) throws UpdateException {
        try{
            em.merge(pack);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("editPack()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Elimina un pack.
     * @param pack Objeto pack para eliminar.
     * @throws DeleteException Salta si hay algun error al borrar.
     */
    @Override
    public void removePack(Pack pack) throws DeleteException {
        try{
            
            pack=em.find(Pack.class, pack.getIdPack());
            for(Apunte a:pack.getApuntes()){
                a.getPacks().remove(pack);
            }
            pack=em.merge(pack);
            em.flush();
            Query q1 = em.createQuery ("DELETE FROM Pack a WHERE a.idPack = :idPack");
            q1.setParameter ("idPack",pack.getIdPack());
            int deleted1 = q1.executeUpdate ();
            
        }catch (Exception e){
            LOGGER.severe("removePack()" + e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    /**
     * Busca un pack.
     * @param idPack Id del pack.
     * @return Objeto pack buscado.
     * @throws SelectException Salta si a ocurrido un error en la búsqueda.
     */
    @Override
    public Pack findPack(Integer idPack) throws SelectException {
        Pack pack = null;
        try{
            pack = em.find(Pack.class, idPack);
        }catch(Exception e){
            LOGGER.severe("findMPack()" + e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return pack;
    }
    /**
     * Busca todos los packs.
     * @return Collección con todos los packs.
     * @throws SelectCollectionException Salta si hay un error en la búsqueda de más de un dato.
     */
    @Override
    public Set<Pack> findAllPack() throws SelectCollectionException {
        Set<Pack> packs = null;
        try{
            packs = new HashSet<>(em.createNamedQuery("findAllPack").getResultList());
        }catch(Exception e){
            LOGGER.severe("findAllPack()" + e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return packs;
    }
    /**
     * Inserta un apunte a un pack.
     * @param pack Objeto pack a editar.
     * @param idApunte Id del apunte a insertar.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    @Override
    public void insertarApunte(Pack pack, Integer idApunte) throws UpdateException{
        try{
            pack=em.find(Pack.class, pack.getIdPack());
            pack.getApuntes().add(em.find(Apunte.class, idApunte));
            em.merge(pack);
            Apunte apunte=em.find(Apunte.class, idApunte);
            apunte.getPacks().add(pack);
            em.merge(apunte);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("insertarApunte()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Elimina un apunte a un pack.
     * @param pack Objeto pack a editar.
     * @param idApunte Id del apunte a eliminar.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    @Override
    public void eliminarApunte(Pack pack, Integer idApunte) throws UpdateException{
        try{
            pack=em.find(Pack.class, pack.getIdPack());
            pack.getApuntes().remove(em.find(Apunte.class, idApunte));
            em.merge(pack);
            Apunte apunte=em.find(Apunte.class, idApunte);
            apunte.getPacks().remove(pack);
            em.merge(apunte);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("eliminarApunte()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Busca una oferta de un pack.
     * @param idPack Id del pack al que buscar la oferta.
     * @return Objeto oferta buscado.
     * @throws SelectException Salta si a ocurrido un error en la búsqueda.
     */
    @Override
    public Oferta dameOferta(Integer idPack) throws SelectException{
        Oferta resultado=null;
        Pack pack=em.find(Pack.class, idPack);
        for(Oferta o:pack.getOfertas()){
            if(o.getFechaFin().after(new Date()) && o.getFechaInicio().before(new Date())){
                resultado=o;
                break;
            }
        }
        return resultado;
    }
    
    
}
