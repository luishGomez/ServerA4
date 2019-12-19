/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb;

import entity.Oferta;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.List;

/**
 * Es la interfaz EJB Local que maneja el CRUD de la entidad Oferta.
 * @author Sergio
 */
public interface OfertaEJBLocal {
    /**
     * Crea una Oferta nueva
     * @param oferta
     * @throws CreateException si hay una excepcion durante el proceso
     */
    public void createOferta(Oferta oferta) throws CreateException;
    /**
     * Borra una Oferta
     * @param oferta
     * @throws DeleteException si hay una excepcion durante el proceso
     */
    public void deleteOferta(Oferta oferta) throws DeleteException;
    /**
     * Actualiza los datos de una oferta
     * @param oferta
     * @throws UpdateException si hay una excepcion durante el proceso
     */
    public void updateOferta(Oferta oferta) throws UpdateException;
    /**
     * Busca todas las ofertas disponibles
     * @return Una lista de Ofertas
     * @throws SelectCollectionException si hay una excepcion durante el proceso
     */
    public List<Oferta> findAllOfertas() throws SelectCollectionException;
    /**
     * Busca ofertas por id
     * @param idOferta La id de la oferta para encontrar
     * @return Oferta
     * @throws SelectException si hay una excepcion durante el proceso
     */
    public Oferta findOfertaById(Integer idOferta) throws SelectException;
    /**
     * Inserta en una oferta un pack.
     * @param oferta Objeto que contiene los datos de oferta.
     * @param idPack El identificador de pack.
     * @throws UpdateException Salta si ocurre un error al actualizar.
     */
    public void insertarPack(Oferta oferta, Integer idPack) throws UpdateException;
    /**
     * Eliminar en una oferta un pack.
     * @param oferta Objeto que contiene los datos de oferta.
     * @param idPack El identificador de pack.
     * @throws UpdateException Salta si ocurre un error al actualizar.
     */
    public void eliminarPack(Oferta oferta, Integer idPack) throws UpdateException;
}
