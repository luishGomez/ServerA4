/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author 2dam
 */
@Entity
@Table(name="cliente",schema="serverA4db")
public class Cliente extends User implements Serializable{
    private static final long serialVersionUID=1L;
    
    
    private Set <Compra> compras;
    private Set <Apunte> apuntes;
    private float saldo;
    private Blob foto;
}
