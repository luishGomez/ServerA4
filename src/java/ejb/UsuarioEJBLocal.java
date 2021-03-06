package ejb;

import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectException;
import exception.UpdateException;
import exception.UserNoExistException;
import exception.WrongPasswordException;
import javax.ejb.Local;

/**
 * Es la interfaz que se encarga de la logica de los <b>usuarios</b> de la aplicacion.
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
     * @throws SelectException si hay una excepcion durante el proceso
     */
    public User findUserByLogin(String login) throws SelectException,UserNoExistException;
    /**
     * Verifica que un Usuario existe comprobando su Login y Contraseña
     * @param usuario El Objeto Usuario que contiene los datos.
     * @return Usuario encontrado
     * @throws SelectException si hay una excepcion durante el proceso
     */
    public User contraseniaCorrecta(User usuario) throws SelectException,WrongPasswordException;
}
