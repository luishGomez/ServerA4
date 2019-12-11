/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import entity.Cliente;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
@Stateless
public class ClienteEJB implements ClienteEJBLocal{
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.ClienteEJB");
    
    
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;
    
    @Override
    public void createCliente(Cliente cliente) throws CreateException {
        try{
            em.persist(cliente);
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> createCliente() "+e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
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
    @Override
    public void removeCliente(Integer idCliente) throws DeleteException {
        try{
            em.remove(em.merge(em.find(Cliente.class, idCliente)));
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> removeCliente() "+e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
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
    /*
    public List<T> findRange(int[] range) {
    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    cq.select(cq.from(entityClass));
    javax.persistence.Query q = getEntityManager().createQuery(cq);
    q.setMaxResults(range[1] - range[0] + 1);
    q.setFirstResult(range[0]);
    return q.getResultList();
    }
    
    public int count() {
    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
    cq.select(getEntityManager().getCriteriaBuilder().count(rt));
    javax.persistence.Query q = getEntityManager().createQuery(cq);
    return ((Long) q.getSingleResult()).intValue();
    }
    */
    /*
    public byte[] getArchivoById(Integer id){
    byte[] bytes=null;
    SerialBlob blob=null;
    try{
    blob=(SerialBlob) getEntityManager().createNamedQuery("getArchivoById").setParameter("idApunte", id).getSingleResult();
    bytes=blob.getBytes(1, (int) blob.length());
    }catch(ClassCastException e){
    LOGGER.severe("ERROR: de caseteo"+2+" \n El archivo del apunte no se a castedo bien a un arrary de bytes \"byte[]\"");
    }catch(SerialException e){
    LOGGER.severe("ERROR:  serial"+2+" \n El archivo del apunte no se a castedo bien a un arrary de bytes \"byte[]\"");
    }
    return  bytes;
    }
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
    
    //De cliente
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
    /*
    @Override
    public void actualizarCliente(Cliente cliente) throws UpdateException{
        try{
            em.merge(cliente);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> actualizarCliente() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
*/
    
    
    
}
