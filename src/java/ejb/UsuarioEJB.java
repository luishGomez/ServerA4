package ejb;

import encriptaciones.Encriptador;
import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectException;
import exception.UpdateException;
import exception.UserNoExistException;
import exception.WrongPasswordException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * La clase que se encarga de la logica de los <b>usuarios</b> de la aplicacion.
 * @author Sergio
 */
@Stateless
public  class UsuarioEJB implements UsuarioEJBLocal{
    private Encriptador encriptador=new Encriptador();
    @PersistenceContext
    private  EntityManager em;
    
    /**
     * Crea un Usuario (administrador) nuevo
     * @param usuario Objeto con sus datos a crear
     * @throws CreateException si hay una excepcion durante el proceso
     */
    @Override
    public void createUser(User usuario) throws CreateException{
        try {
            em.persist(usuario);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
        
    }
    /**
     * Borra un Usuario
     * @param usuario Objeto a borrar
     * @throws DeleteException si hay una excepcion durante el proceso
     */
    @Override
    public void deleteUser(User usuario) throws DeleteException{
        try {
            usuario=em.merge(usuario);
            em.remove(usuario);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }
    /**
     * Actualiza un Usuario
     * @param usuario Objeto con datos a actualizar
     * @throws UpdateException si hay una excepcion durante el proceso
     */
    @Override
    public void updateUser(User usuario) throws UpdateException{
        try {
            em.merge(usuario);
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Busca un Usuario por el Login
     * @param login Login del Usuario a buscar
     * @return Usuario encontrado
     * @throws exception.UserNoExistException si hay una excepcion durante el proceso
     */
    @Override
    public User findUserByLogin(String login) throws SelectException,UserNoExistException{
        User usuario = null;
        try{
            usuario =(User) em.createNamedQuery("findUserByLogin").setParameter("login", login).getSingleResult();
        }catch(NoResultException e){
            throw new UserNoExistException(e.getMessage());
        }catch(Exception e){
            throw new SelectException(e.getMessage());
        }
        return usuario;
    }
    /**
     * Verifica que un Usuario existe comprobando su Login y Contraseña
     * @param usuario conteniendo su contraseña y logins a comprobar
     * @return Usuario encontrado
     * @throws WrongPasswordException si hay una excepcion durante el proceso
     */
    @Override
    public User contraseniaCorrecta(User usuario) throws SelectException,WrongPasswordException {
        User usuarioComprobado = null;
        try{
            String claveCifrada=encriptador.resumir(usuario.getContrasenia());
            usuarioComprobado = (User) em.createNamedQuery("contraseniaCorrecta").setParameter("login", usuario.getLogin()).setParameter("contrasenia", claveCifrada).getSingleResult();
        }catch(NoResultException e){
            throw new WrongPasswordException(e.getMessage());
        }catch(Exception e){
            throw new SelectException(e.getMessage());
        }
        return usuarioComprobado;
    }
    
}
