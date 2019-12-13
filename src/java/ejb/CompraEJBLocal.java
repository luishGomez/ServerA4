package ejb;

import entity.Compra;
import entity.CompraId;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Luis
 */
@Local
public interface CompraEJBLocal {
    public void createCompra(Compra compra) throws CreateException;
    public void editCompra(Compra compra) throws UpdateException;
    public void removeCompra(Compra compra) throws DeleteException;
    public Compra findCompra(CompraId idCompra) throws SelectException;
    public Set<Compra> findAllCompra() throws SelectCollectionException;
    public Set<Compra> findAllCompraByClienteId(Integer idCliente) throws SelectCollectionException;
}
