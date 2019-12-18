/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
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
import exception.YaExisteLoginException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * La clase que se encarga de la logia de los <b>clientes</b> de la aplicacion.
 * @author Ricardo Peinado Lastra
 */
@Stateless
public class ClienteEJB implements ClienteEJBLocal{
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.ClienteEJB");
    
    /**
     * El objeto Entity Manager
     */
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;
    
    /**
     * Crea un cliente.
     * @param cliente El objeto {@link Cliente} que contiene los datos.
     * @throws CreateException Salta si ocurre un error al crear.
     */
    @Override
    public void createCliente(Cliente cliente) throws CreateException {
        try{
            em.persist(cliente);
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> createCliente() "+e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * Edita un cliente ya existente.
     * @param cliente El objeto {@link Cliente} con todos los datos para editar.
     * @throws UpdateException Salta si ocurre un error al modificar.
     */
    @Override
    public void editCliente(Cliente cliente) throws UpdateException {
        try{
            em.merge(cliente);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> editCliente() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Borra un cliente ya existente.
     * @param idCliente El ID de un {@link Cliente}.
     * @throws DeleteException Salta si ocurre un error al borrar un cliente.
     */
    @Override
    public void removeCliente(Integer idCliente) throws DeleteException {
        try{
            em.remove(em.merge(em.find(Cliente.class, idCliente)));
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> removeCliente() "+e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    /**
     * Busca un cliente ya existente por su ID.
     * @param idCliente El ID de un {@link Cliente}.
     * @return Retorna un {@link Cliente}.
     * @throws SelectException Salta si ocurre un error al buscar.
     */
    @Override
    public Cliente findCliente(Integer idCliente) throws SelectException {
        Cliente resultado=null;
        try{
            resultado=em.find(Cliente.class, idCliente);
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> findCliente() "+e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return resultado;
    }
    /**
     * Busca todos los clientes existentes.
     * @return Retorna una lista de {@link Cliente}.
     * @throws SelectCollectionException Salta si ocurre un error al buscar varios datos.
     */
    @Override
    public Set<Cliente> findAllClientes() throws SelectCollectionException {
        /*
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
        */
        Set <Cliente> resultado=null;
        try{
            resultado= new HashSet<>(em.createNamedQuery("findAllCliente").getResultList());
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> findAllClientes() "+e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return resultado;
    }
    
    
    /**
     * Busca los clientes que han votado un {@link Apunte}.
     * @param id El ID de un {@link Apunte}.
     * @return Retorna una lista de {@link Cliente}.
     * @throws SelectCollectionException Salta si ocurre un error en la busqueda de varios datos.
     */
    @Override
    public List <Cliente> getVotantesId(Integer id) throws SelectCollectionException{
        List <Cliente> resultado=null;
        try{
            resultado= (List <Cliente>)em.createNamedQuery("getVotantesId").setParameter("idApunte", id).getResultList();
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> getVotantesId() "+e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return resultado;
    }
    
    
    /**
     * Actualiza la contraseña de un cliente.
     * @param cliente Envia el objeto de un {@link Cliente}
     * @throws UpdateException Salta si ocurre un error al modificar.
     */
    @Override
    public void actualizarContrasenia(Cliente cliente) throws UpdateException{
        try{
            em.merge(cliente);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> actualizarContrasenia() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    @Override
    public void comprarApunte(Cliente cliente, Integer idApunte) throws CreateException{
        try{
            Cliente clienteRenovado = em.find(Cliente.class, cliente.getId());
            Compra nuevaCompra= new Compra();
            Apunte elApunte=em.find(Apunte.class, idApunte);
            nuevaCompra.setApunte(elApunte);
            nuevaCompra.setPropietario(clienteRenovado);
            Date fecha=new Date();
            nuevaCompra.setFecha(fecha);
            nuevaCompra.setIdCompra(new CompraId(clienteRenovado.getId(),idApunte));
            em.persist(nuevaCompra);
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> comprarApunte() "+e.getMessage());
            throw new CreateException(e.getMessage());
        }
        
    }
}
