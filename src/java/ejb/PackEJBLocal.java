/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Pack;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Luis
 */
@Local
public interface PackEJBLocal {
    public void createPack(Pack pack) throws CreateException;
    public void editPack(Pack pack) throws UpdateException;
    public void removePack(Pack pack) throws DeleteException;
    public Pack findPack(Integer idPack) throws SelectException;
    public Set<Pack> findAllPack() throws SelectCollectionException;
}
