/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Cliente;
import exception.CreateException;
import exception.DeleteException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Usuario
 */
@Local
public interface ClienteEJBLocal {
    public void createCliente(Cliente cliente) throws CreateException;
    public void editCliente(Cliente cliente) throws UpdateException;
    public void removeCliente(Integer idCliente) throws DeleteException;
    public Cliente findCliente(Integer idCliente) throws SelectException;
    public Set<Cliente> findAllClientes() throws SelectCollectionException;
    public List <Cliente> getVotantesId(Integer id) throws SelectCollectionException;
    public void actualizarContrasenia(Cliente cliente) throws UpdateException;
   // public void actualizarCliente(Cliente cliente) throws UpdateException;
    
}
