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
 * Interfaz EJB Local que maneja el CRUD de Compra.
 * @author Luis
 */
@Local
public interface CompraEJBLocal {
    /**
     * Crea una compra.
     * @param idApunte Id del apunte comprado.
     * @param idCliente Id del comprador.
     * @throws CreateException Salta si hay algun error en el proceso de la creación.
     */
    public void createCompra(Integer idApunte, Integer idCliente) throws CreateException;
     /**
     * Edita una compra.
     * @param compra Objeto compra editado.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    public void editCompra(Compra compra) throws UpdateException;
    /**
     * Elimina una compra.
     * @param compra Objeto compra para eliminar.
     * @throws DeleteException Salta si hay algun error al borrar.
     */
    public void removeCompra(Compra compra) throws DeleteException;
    /**
     * Busca una compra.
     * @param idCompra Id de la compra.
     * @return Objeto compra buscado.
     * @throws SelectException Salta si a ocurrido un error en la búsqueda.
     */
    public Compra findCompra(CompraId idCompra) throws SelectException;
    /**
     * Busca todas las compras.
     * @return Collección con todas las compras.
     * @throws SelectCollectionException Salta si hay un error en la búsqueda de más de un dato.
     */
    public Set<Compra> findAllCompra() throws SelectCollectionException;
    /**
     * Busca todas las compras de un cliente.
     * @param idCliente Id del cliente.
     * @return Collección con todas las compras.
     * @throws SelectCollectionException Salta si hay un error en la búsqueda de más de un dato.
     */
    public Set<Compra> findAllCompraByClienteId(Integer idCliente) throws SelectCollectionException;
}
