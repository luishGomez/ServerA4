/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package service;

import entity.Apunte;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import exception.YaEstaVendidoException;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Usuario
 */
@Local
public interface ApunteEJBLocal {
    public void createApunte(Apunte apunte) throws CreateException;
    public void editApunte(Apunte apunte) throws UpdateException;
    public void removeApunte(Apunte apunte) throws DeleteException;
    public Apunte findApunte(Integer idApunte) throws SelectException;
    public Set<Apunte> findAllApuntes() throws SelectCollectionException;
    public Set<Apunte> getApuntesByCreador(Integer id) throws SelectCollectionException;
    public Set<Apunte> getApuntesByComprador(Integer id)throws SelectCollectionException;
    public void votacion (Integer idCliente, Integer like, Apunte apunte) throws UpdateException;
    //public Set <Apunte> getMisApuntes(Integer id)throws SelectCollectionException;
    //public void borrarApunte(Integer id) throws YaEstaVendidoException, DeleteException;
}
