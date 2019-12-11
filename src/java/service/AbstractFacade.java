/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Apunte;
import entity.Cliente;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author 2dam
 */
public abstract class AbstractFacade<T> {
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.AbstractFacade");
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

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
    public Set<Apunte> getApuntesByCreador(Integer id){
       List<Apunte> sourceList=(List<Apunte>)getEntityManager().createNamedQuery("getApuntesByCreador").setParameter("idCliente", id).getResultList();
       return new HashSet<>(sourceList);
    }
    public Set<Apunte> getApuntesByComprador(Integer id){
       List<Apunte> sourceList=(List<Apunte>)getEntityManager().createNamedQuery("getApuntesByComprador").setParameter("idCliente", id).getResultList();
       return new HashSet<>(sourceList);
    }
    
    
    public List <Cliente> getVotantesId(Integer id){
        return (List <Cliente>) getEntityManager().createNamedQuery("getVotantesId").setParameter("idApunte", id).getResultList();
    }
    
    //De cliente
    public void actualizarContrasenia(Cliente cliente){
        getEntityManager().merge(cliente);
        getEntityManager().flush();
    }
    public void actualizarCliente(Cliente cliente){
        getEntityManager().merge(cliente);
        getEntityManager().flush();
    }
     public Set <Apunte> getMisApuntes(Integer id){
        
        Set <Apunte> apuntes=null; 
        try{
        if(getEntityManager().find(Cliente.class, id).getApuntes()!=null)
            apuntes=getEntityManager().find(Cliente.class, id).getApuntes();
        }catch(NullPointerException e){
            apuntes=new HashSet <Apunte>();   
        }            
        return apuntes;
                
     }
     
    
}
