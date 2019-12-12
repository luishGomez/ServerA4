/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Oferta;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
@Stateless
public  class OfertaEJB implements OfertaEJBLocal{
    
    @PersistenceContext
    private  EntityManager em;
    

    @Override
    public void createOferta(Oferta oferta) throws CreateException{
        try {
            em.persist(oferta);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    @Override
    public void deleteOferta(Oferta oferta) throws DeleteException{
        try {
            em.remove(em.find(Oferta.class, oferta.getIdOferta()));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
        
    }
    @Override
    public void updateOferta(Oferta oferta) throws UpdateException{
        try {
            em.merge(oferta);
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    @Override
    public List<Oferta> findAllOfertas() throws SelectCollectionException{
        List<Oferta> ofertas = null;
        try {
            ofertas = em.createNamedQuery("findAllClients").getResultList();
        } catch (Exception e) {
            throw new SelectCollectionException(e.getMessage());
        }
        return ofertas;
    }

    /**
     *
     * @param idOferta
     * @return
     * @throws SelectException
     */
    @Override
    public Oferta findOfertaById(Integer idOferta) throws SelectException{
        Oferta oferta = null;
        try {
            oferta = (Oferta) em.createNamedQuery("findOfertaById").setParameter("idOferta", idOferta).getSingleResult();
        } catch (Exception e) {
            throw new SelectException(e.getMessage());
        }
        return oferta;
    }
}
