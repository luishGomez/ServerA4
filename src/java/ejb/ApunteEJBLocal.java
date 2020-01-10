/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb;

import entity.Apunte;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
import javax.ejb.Local;

/**
 * Es la interfaz EJB Local que maneja el CRUD de {@link Apunte}.
 * @author Ricardo Peinado Lastra
 */
@Local
public interface ApunteEJBLocal {
    /**
     * Crea un apunte.
     * @param apunte {@link Apunte} es el objeto que tiene los datos de un nuevo apunte.
     * @throws CreateException Salta si hay algun error en el proceso de la creación.
     */
    public void createApunte(Apunte apunte) throws CreateException;
    /**
     * Edita un apunte ya existente.
     * @param apunte {@link Apunte} es el objeto con los datos para modificar.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    public void editApunte(Apunte apunte) throws UpdateException;
    /**
     * Elimina un apunte ya existente
     * @param apunte {@link Apunte} es el objeto con los datos a borrar.
     * @throws DeleteException Salta si hay algun error al borrar.
     */
    public void removeApunte(Apunte apunte) throws DeleteException;
    /**
     * Busca un apunte por su ID.
     * @param idApunte Es el identificador de un apunte.
     * @return Retorna un {@link Apunte}.
     * @throws SelectException Salta si a ocurrido un error en la busqueda.
     */
    public Apunte findApunte(Integer idApunte) throws SelectException;
    /**
     * Busca todos los apuntes existentes.
     * @return Retorna una lista de {@link Apunte}.
     * @throws SelectCollectionException Salta si hay un error en la busqueda de más de un dato.
     */
    public Set<Apunte> findAllApuntes() throws SelectCollectionException;
    /**
     * Devuelve todos los apuntes de un creador.
     * @param id Es el ID de un {@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     * @throws SelectCollectionException Salta si hay un error en la busqueda de más de un dato.
     */
    public Set<Apunte> getApuntesByCreador(Integer id) throws SelectCollectionException;
    /**
     * Devuelve todos los apuntes comprados por un {@link Cliente}.
     * @param id El el ID de un {@link Cliente}.
     * @return Retorna una lista de {@link Apunte}.
     * @throws SelectCollectionException
     */
    public Set<Apunte> getApuntesByComprador(Integer id)throws SelectCollectionException;
    /**
     * Inserta una votación a un {@link Apunte}.
     * @param idCliente El ID de un {@link Cliente}.
     * @param like 1: Si es un like | -1: Si es un dislike.
     * @param apunte Un objecto de tipo {@link Apunte} con todos sus datos.
     * @throws UpdateException Salta si hay un error en la actualización.
     */
    public void votacion (Integer idCliente, Integer like, Apunte apunte) throws UpdateException;
    public int cuantasCompras(Integer idApunte) throws SelectException;
}
