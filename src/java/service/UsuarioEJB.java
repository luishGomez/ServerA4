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
            throw new CreateException(e.getMessage());
        }
        
    }

    @Override
    public void deleteUser(User usuario) throws DeleteException{
        try {
            usuario=em.merge(usuario);
            em.remove(usuario);
        } catch (Exception e) {
            
        }
        
       /*
        try{
          em.createNamedQuery("deleteUser").setParameter("id", usuario.getId());
        }catch(Exception e){            
        
        }
       */
    }
    @Override
    public void updateUser(User usuario) throws UpdateException{
        try {
            em.merge(usuario);
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    
    //-------

    /**
     *
     * @param login
     * @return
     * @throws exception.UserNoExistException
     */
    @Override
    public User findUserByLogin(String login) throws UserNoExistException{
        User usuario = null;
        try{
           usuario =em.find(User.class, login);
        }catch(Exception e){            
         throw new UserNoExistException(e.getMessage());
        }
        return usuario;
    }

    @Override
    public User contraseniaCorrecta(User usuario) throws WrongPasswordException {       
       User usuarioComprobado = null;
        try{
           usuarioComprobado = (User) em.createNamedQuery("contraseniaCorrecta").setParameter("lgin", usuario.getLogin()).setParameter("contrasenia", usuario.getContrasenia()).getSingleResult();
        }catch(Exception e){            
         throw new WrongPasswordException(e.getMessage());
        }
        return usuarioComprobado;
    }

}
