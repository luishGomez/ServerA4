/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb;

import entity.Cliente;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

/**
 * Es la interfaz EJB Local que maneja el CRUD de {@link Cliente}.
 * @author Ricardo Peinado Lastra
 */
@Local
public interface ClienteEJBLocal {
    /**
     * Crea un cliente.
     * @param cliente El objeto {@link Cliente} que contiene los datos.
     * @throws CreateException Salta si ocurre un error al crear.
     */
    public void createCliente(Cliente cliente) throws CreateException;
    /**
     * Edita un cliente ya existente.
     * @param cliente El objeto {@link Cliente} con todos los datos para editar.
     * @throws UpdateException Salta si ocurre un error al modificar.
     */
    public void editCliente(Cliente cliente) throws UpdateException;
    /**
     * Borra un cliente ya existente.
     * @param idCliente El ID de un {@link Cliente}.
     * @throws DeleteException Salta si ocurre un error al borrar un cliente.
     */
    public void removeCliente(Integer idCliente) throws DeleteException;
    /**
     * Busca un cliente ya existente por su ID.
     * @param idCliente El ID de un {@link Cliente}.
     * @return Retorna un {@link Cliente}.
     * @throws SelectException Salta si ocurre un error al buscar.
     */
    public Cliente findCliente(Integer idCliente) throws SelectException;
    /**
     * Busca todos los clientes existentes.
     * @return Retorna una lista de {@link Cliente}.
     * @throws SelectCollectionException Salta si ocurre un error al buscar varios datos.
     */
    public Set<Cliente> findAllClientes() throws SelectCollectionException;
    /**
     * Busca los clientes que han votado un {@link Apunte}.
     * @param id El ID de un {@link Apunte}.
     * @return Retorna una lista de {@link Cliente}.
     * @throws SelectCollectionException Salta si ocurre un error en la busqueda de varios datos.
     */
    public List <Cliente> getVotantesId(Integer id) throws SelectCollectionException;
    /**
     * Actualiza la contraseña de un cliente.
     * @param cliente Envia el objeto de un {@link Cliente}
     * @throws UpdateException Salta si ocurre un error al modificar.
     */
    public void actualizarContrasenia(Cliente cliente) throws UpdateException;
    // public void actualizarCliente(Cliente cliente) throws UpdateException;
    
}
