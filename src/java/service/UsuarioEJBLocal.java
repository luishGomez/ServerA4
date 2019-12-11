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

/**
 *
 * @author Sergio
 */
public interface UsuarioEJBLocal {
    public void createUser(User usuario) throws CreateException;
    public void deleteUser(User usuario) throws DeleteException;
   // public User findUserByLogin(String login) throws UserNoExistException;
    public void updateUser(User usuario) throws UpdateException;
    //-------
    public User findUserByLogin(String login) throws UserNoExistException;
}
