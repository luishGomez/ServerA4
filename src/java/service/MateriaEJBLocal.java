/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Materia;
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
public interface MateriaEJBLocal {
    public void createMateria(Materia materia) throws CreateException;
    public void editMateria(Materia materia) throws UpdateException;
    public void removeMateria(Materia materia) throws DeleteException;
    public Materia findMateria(Integer idMateria) throws SelectException;
    public Set<Materia> findAllMateria() throws SelectCollectionException;
}
