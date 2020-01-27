/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Materia;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Set;
import javax.ejb.Local;

/**
 * Interfaz EJB Local que maneja el CRUD de Materia.
 * @author Luis
 */
@Local
public interface MateriaEJBLocal {
    /**
     * Crea una materia.
     * @param materia Objeto materia para crear.
     * @throws CreateException Salta si hay algun error en el proceso de la creación.
     */
    public void createMateria(Materia materia) throws CreateException;
    /**
     * Edita una materia.
     * @param materia Objeto materia editado.
     * @throws UpdateException Salta si hay algun error en la modificación.
     */
    public void editMateria(Materia materia) throws UpdateException;
    /**
     * Elimina una materia.
     * @param materia Objeto materia para eliminar.
     * @throws DeleteException Salta si hay algun error al borrar.
     */
    public void removeMateria(Materia materia) throws DeleteException;
    /**
     * Busca una materia.
     * @param idMateria Id de la materia.
     * @return Objeto materia buscado.
     * @throws SelectException Salta si a ocurrido un error en la búsqueda.
     */
    public Materia findMateria(Integer idMateria) throws SelectException;
    /**
     * Busca todas las materias.
     * @return Collección con todas las materias.
     * @throws SelectCollectionException Salta si hay un error en la búsqueda de más de un dato.
     */
    public Set<Materia> findAllMateria() throws SelectCollectionException;
}
