/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 * s
 * @author 2dam
 */
public class CompraId implements Serializable{
    // 1L
    
    private Integer clienteId;
    private Integer apunteId;

    public CompraId() {
    }

    public CompraId(Integer clienteId, Integer apunteId) {
        this.clienteId = clienteId;
        this.apunteId = apunteId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getApunteId() {
        return apunteId;
    }

    public void setApunteId(Integer apunteId) {
        this.apunteId = apunteId;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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