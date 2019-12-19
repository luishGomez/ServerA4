/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb;

import entity.Oferta;
import entity.Pack;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * La clase que se encarga de la logica de las <b>ofertas</b> de la aplicacion.
 * @author Sergio
 */
@Stateless
public  class OfertaEJB implements OfertaEJBLocal{
    private static final Logger LOGGER =
            Logger.getLogger("javafxserverside");
    @PersistenceContext
    private  EntityManager em;
    
    /**
     * Crea una Oferta nueva
     * @param oferta Objeto que contiene los datos de la oferta
     * @throws CreateException si hay una excepcion durante el proceso
     */
    @Override
    public void createOferta(Oferta oferta) throws CreateException{
        try {
            em.persist(oferta);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * Borra una Oferta
     * @param oferta Objeto que contiene los datos de la oferta
     * @throws DeleteException si hay una excepcion durante el proceso
     */
    @Override
    public void deleteOferta(Oferta oferta) throws DeleteException{
        try {
            for(Pack p:oferta.getPacks()){
                p.getOfertas().remove(oferta);
            }
            oferta=em.merge(oferta);
            em.flush();
            Query q1 = em.createQuery ("DELETE FROM Oferta a WHERE a.idOferta = :idOferta");
            q1.setParameter ("idOferta",oferta.getIdOferta());
            int deleted1 = q1.executeUpdate ();
            
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
        
    }
    /**
     * Actualiza una oferta
     * @param oferta Objeto que contiene los datos de la oferta
     * @throws UpdateException si hay una excepcion durante el proceso
     */
    @Override
    public void updateOferta(Oferta oferta) throws UpdateException{
        try {
            em.merge(oferta);
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Busca todas las ofertas disponibles
     * @return Lista de Ofertas
     * @throws SelectCollectionException si hay una excepcion durante el proceso
     */
    @Override
    public List<Oferta> findAllOfertas() throws SelectCollectionException{
        List<Oferta> ofertas = null;
        try {
            ofertas = em.createNamedQuery("findAllOfertas").getResultList();
        } catch (Exception e) {
            throw new SelectCollectionException(e.getMessage());
        }
        return ofertas;
    }
    
    /**
     * Busca una oferta por su Id
     * @param idOferta La id de la oferta para encontrar
     * @return Oferta encontradas
     * @throws SelectException si hay una excepcion durante el proceso
     */
    @Override
    public Oferta findOfertaById(Integer idOferta) throws SelectException{
        Oferta oferta = null;
        try {
            
            oferta = em.find(Oferta.class, idOferta);
        } catch (Exception e) {
            throw new SelectException(e.getMessage());
        }
        return oferta;
    }
    /**
     * Inserta en una oferta un pack.
     * @param oferta Objeto que contiene los datos de oferta.
     * @param idPack El identificador de pack.
     * @throws UpdateException Salta si ocurre un error al actualizar.
     */
    @Override
    public void insertarPack(Oferta oferta, Integer idPack) throws UpdateException{
        try{
            oferta=em.find(Oferta.class, oferta.getIdOferta());
            oferta.getPacks().add(em.find(Pack.class, idPack));
            em.merge(oferta);
            Pack pack=em.find(Pack.class, idPack);
            pack.getOfertas().add(oferta);
            em.merge(pack);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("insertarApunte()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Eliminar en una oferta un pack.
     * @param oferta Objeto que contiene los datos de oferta.
     * @param idPack El identificador de pack.
     * @throws UpdateException Salta si ocurre un error al actualizar.
     */
    @Override
    public void eliminarPack(Oferta oferta, Integer idPack) throws UpdateException{
        try{
            oferta=em.find(Oferta.class, oferta.getIdOferta());
            oferta.getPacks().remove(em.find(Pack.class, idPack));
            em.merge(oferta);
            Pack pack=em.find(Pack.class, idPack);
            pack.getOfertas().remove(oferta);
            em.merge(pack);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("eliminarApunte()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
}
