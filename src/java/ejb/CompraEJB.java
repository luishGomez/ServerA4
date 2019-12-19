package ejb;

import entity.Apunte;
import entity.Cliente;
import entity.Compra;
import entity.CompraId;
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

/**
 * La clase que se encarga de la logica de las <b>compas</b> de la aplicacion.
 * @author Luis
 */
@Stateless
public class CompraEJB implements CompraEJBLocal{
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.CompraEJB");
    /**
     * El objeto Entity Manager
     */
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;
    /**
     * Crea una compra.
     * @param idApunte El identificador del apunte.
     * @param idCliente El identificador del cliente.
     * @throws CreateException Salta si ocurre un error al crear
     */
    @Override
    public void createCompra( Integer idApunte, Integer idCliente) throws CreateException {
        try{
            Compra nuevaCompra= new Compra();
            Apunte elApunte=em.find(Apunte.class, idApunte);
            Cliente elCliente=em.find(Cliente.class, idCliente);
            nuevaCompra.setApunte(elApunte);
            nuevaCompra.setPropietario(elCliente);
            Date fecha=new Date();
            nuevaCompra.setFecha(fecha);
            nuevaCompra.setIdCompra(new CompraId(idCliente,idApunte));
            em.persist(nuevaCompra);
        }catch (Exception e){
            LOGGER.severe("createCompra()" + e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * Edita una compra.
     * @param compra El objeto con los datos de una compra.
     * @throws UpdateException Salta si ocurre un error al actualizar.
     */
    @Override
    public void editCompra(Compra compra) throws UpdateException {
        try{
            em.merge(compra);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("editCompra()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Borra una compra.
     * @param compra El objeto con los datos de una compra.
     * @throws DeleteException Salta si ocurre un erro al borrar una compra.
     */
    @Override
    public void removeCompra(Compra compra) throws DeleteException {
        try{
            em.remove(em.merge(compra));
            em.flush();
        }catch (Exception e){
            LOGGER.severe("removeCompra()" + e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    /**
     * Busca una compra por su identificador.
     * @param idCompra El identificador de la compra.
     * @return Devuelve un onjeto Compra sus datos.
     * @throws SelectException Salta si ocurre un error al seleccionar un objeto.
     */
    @Override
    public Compra findCompra(CompraId idCompra) throws SelectException {
        Compra compra = null;
        try{
            compra = em.find(Compra.class, idCompra);
        }catch(Exception e){
            LOGGER.severe("findCompra()" + e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return compra;
    }
    /**
     * Busca todas las compras existentes.
     * @return Retorna una lista de objetos Compra.
     * @throws SelectCollectionException Salta si ocurre un error al seleccionar
     * una lista de objetos.
     */
    @Override
    public Set<Compra> findAllCompra() throws SelectCollectionException {
        Set<Compra> compras = null;
        try{
            compras = new HashSet<>(em.createNamedQuery("findAllCompra").getResultList());
        }catch(Exception e){
            LOGGER.severe("findAllCompra()" + e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return compras;
    }
    /**
     * Busca todas las compras hechas por un cliente.
     * @param idCliente El identificador de un cliente.
     * @return Retorna una coleccion de Compras.
     * @throws SelectCollectionException  Salta si ocurre un error en la busqueda 
     * de mas de un objeto.
     */
    @Override
    public Set<Compra> findAllCompraByClienteId(Integer idCliente) throws SelectCollectionException {
        Set<Compra> compras = null;
        try{
            compras = new HashSet<>(em.createNamedQuery("findAllCompraByClienteId")
                .setParameter("idCliente", idCliente).getResultList());
        }catch(Exception e){
            LOGGER.severe("findAllCompraByClienteId()" + e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return compras;
    }
    
}
