package ejb;

import entity.Oferta;
import entity.Pack;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
import javax.ejb.Local;

/**
 * Interfaz EJB Local que maneja el CRUD de Pack.
 * @author Luis
 */
@Local
public interface PackEJBLocal {
    /**
     * Crea un pack.
     * @param pack Objeto pack para crear.
     * @throws CreateException Salta si hay algun error en el proceso de la creación.
     */
    public void createPack(Pack pack) throws CreateException;
    /**
     * Edita un pack.
     * @param pack Objeto pack editado.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    public void editPack(Pack pack) throws UpdateException;
    /**
     * Elimina un pack.
     * @param pack Objeto pack para eliminar.
     * @throws DeleteException Salta si hay algun error al borrar.
     */
    public void removePack(Pack pack) throws DeleteException;
    /**
     * Busca un pack.
     * @param idPack Id del pack.
     * @return Objeto pack buscado.
     * @throws SelectException Salta si a ocurrido un error en la búsqueda.
     */
    public Pack findPack(Integer idPack) throws SelectException;
    /**
     * Busca todos los packs.
     * @return Collección con todos los packs.
     * @throws SelectCollectionException Salta si hay un error en la búsqueda de más de un dato.
     */
    public Set<Pack> findAllPack() throws SelectCollectionException;
    /**
     * Inserta un apunte a un pack.
     * @param pack Objeto pack a editar.
     * @param idApunte Id del apunte a insertar.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    public void insertarApunte(Pack pack, Integer idApunte) throws UpdateException;
    /**
     * Elimina un apunte a un pack.
     * @param pack Objeto pack a editar.
     * @param idApunte Id del apunte a eliminar.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    public void eliminarApunte(Pack pack, Integer idApunte) throws UpdateException;
    /**
     * Busca una oferta de un pack.
     * @param idPack Id del pack al que buscar la oferta.
     * @return Objeto oferta buscado.
     * @throws SelectException Salta si a ocurrido un error en la búsqueda.
     */
    public Oferta dameOferta(Integer idPack) throws SelectException;
}
