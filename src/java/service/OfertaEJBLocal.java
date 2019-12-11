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

/**
 *
 * @author Sergio
 */
public interface OfertaEJBLocal {
    public void createOferta(Oferta oferta) throws CreateException;
    public void deleteOferta(Oferta oferta) throws DeleteException;
    public void updateUser(Oferta usuario) throws UpdateException;
    public List<Oferta> findAllOfertas() throws SelectCollectionException;
    public Oferta findOfertaById(Integer idOferta) throws SelectException;
}
