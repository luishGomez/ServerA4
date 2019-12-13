package service;

import entity.Compra;
import entity.CompraId;
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
 *
 * @author Luis
 */
@Stateless
public class CompraEJB implements CompraEJBLocal{
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.CompraEJB");
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;

    @Override
    public void createCompra(Compra compra) throws CreateException {
        try{
            em.persist(compra);
        }catch (Exception e){
            LOGGER.severe("createCompra()" + e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

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
