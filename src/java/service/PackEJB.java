/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Pack;
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
public class PackEJB implements PackEJBLocal {
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.PackEJB");
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;

    @Override
    public void createPack(Pack pack) throws CreateException {
        try{
            em.persist(pack);
        }catch (Exception e){
            LOGGER.severe("createPack()" + e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void editPack(Pack pack) throws UpdateException {
        try{
            em.merge(pack);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("editPack()" + e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void removePack(Pack pack) throws DeleteException {
        try{
            em.remove(em.merge(pack));
            em.flush();
        }catch (Exception e){
            LOGGER.severe("removePack()" + e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public Pack findPack(Integer idPack) throws SelectException {
        Pack pack = null;
        try{
            pack = em.find(Pack.class, idPack);
        }catch(Exception e){
            LOGGER.severe("findMPack()" + e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return pack;
    }

    @Override
    public Set<Pack> findAllPack() throws SelectCollectionException {
        Set<Pack> packs = null;
        try{
            packs = new HashSet<>(em.createNamedQuery("findAllPack").getResultList());
        }catch(Exception e){
            LOGGER.severe("findAllPack()" + e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return packs;
    }
}
