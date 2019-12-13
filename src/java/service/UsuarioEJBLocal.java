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
import javax.ejb.Local;

/**
 *
 * @author Sergio
 */
@Local
public interface UsuarioEJBLocal {
    /**
     * Crea un Usuario nuevo
     * @param usuario El Objeto Usuario que contiene los datos
     * @throws CreateException si hay una excepcion durante el proceso
     */
    public void createUser(User usuario) throws CreateException;
    /**
     * Borra un Usuario
     * @param usuario El Objeto Usuario que contiene los datos
     * @throws DeleteException si hay una excepcion durante el proceso
     */
    public void deleteUser(User usuario) throws DeleteException;
    /**
     * Actualiza los datos de un Usuario
     * @param usuario El Objeto Usuario que contiene los datos
     * @throws UpdateException si hay una excepcion durante el proceso
     */
    public void updateUser(User usuario) throws UpdateException;
    /**
     * Busca un Usuario por Login
     * @param login El Login de un Usuario a encontrar
     * @return Usuario
     * @throws UserNoExistException si hay una excepcion durante el proceso
     */
    public User findUserByLogin(String login) throws UserNoExistException;
    /**
     * Verifica que un Usuario existe comprobando su Login y Contraseña
     * @param usuario El Objeto Usuario que contiene los datos.
     * @return Usuario encontrado
     * @throws WrongPasswordException si hay una excepcion durante el proceso
     */
    public User contraseniaCorrecta(User usuario) throws WrongPasswordException;
}
