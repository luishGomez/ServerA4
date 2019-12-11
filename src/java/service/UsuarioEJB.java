/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.UpdateException;
import exception.UserNoExistException;
import exception.WrongPasswordException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
@Stateless
public  class UsuarioEJB implements UsuarioEJBLocal{
    
    @PersistenceContext
    private  EntityManager em;
    

    @Override
    public void createUser(User usuario) throws CreateException{
        try {
            em.persist(usuario);
        } catch (Exception e) {
        }
    }

    @Override
    public void deleteUser(User usuario) throws DeleteException{
        try {
            em.remove(em.merge(usuario));
            em.flush();
        } catch (Exception e) {
        }
        
    }
    @Override
    public void updateUser(User usuario) throws UpdateException{
        try {
            em.merge(usuario);
        em.flush();
        } catch (Exception e) {
        }
    }
    
    //-------

    /**
     *
     * @param login
     * @return
     * @throws WrongPasswordException
     */
    @Override
    public User findUserByLogin(String login) throws UserNoExistException{
        return (User) em.createNamedQuery("findUserByLogin").setParameter("login", login).getSingleResult();
    }

}
