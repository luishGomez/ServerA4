/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Clase Id de Compra.
 * @author Luis
 */
@Embeddable
public class CompraId implements Serializable{
    // 1L
    
    private Integer clienteId;
    private Integer apunteId;
    /**
     * Constructor vacio.
     */
    public CompraId() {
    }
    /**
     * Constructor de CompraId con parametros.
     * @param clienteId Id del cliente.
     * @param apunteId Id del apunte.
     */
    public CompraId(Integer clienteId, Integer apunteId) {
        this.clienteId = clienteId;
        this.apunteId = apunteId;
    }
    /**
     * Devuelve el valor de clienteId.
     * @return Id del cliente.
     */
    public Integer getClienteId() {
        return clienteId;
    }
    /**
     * Le da un valor a clienteId.
     * @param clienteId Id del cliente.
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
    /**
     * Devuelve el valor de apunteId.
     * @return Id del apunte.
     */
    public Integer getApunteId() {
        return apunteId;
    }
    /**
     * Le da un valor a apunteId.
     * @param apunteId Id del apunte.
     */
    public void setApunteId(Integer apunteId) {
        this.apunteId = apunteId;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CompraId)) {
            return false;
        }
        CompraId other = (CompraId) object;
        if ((this.clienteId == null && other.clienteId != null) ||
                (this.clienteId != null && !this.clienteId.equals(other.clienteId))) {
            return false;
        }
        if ((this.apunteId == null && other.apunteId != null) ||
                (this.apunteId != null && !this.apunteId.equals(other.apunteId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apunteId != null ? apunteId.hashCode() : 0);
        hash += (clienteId != null ? clienteId.hashCode() : 0);
        return hash;
    }
}
