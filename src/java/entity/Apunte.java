/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 2dam
 */
@Entity
@Table(name="apunte",schema="serverA4db")
public class Apunte implements Serializable {
    private static final long serialVersionUID=1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idApunte;
    @NotNull
    private String titulo;
    @NotNull
    private String descripcion;
    private Blob archivo;
    private Date fechaValidacion;
    private int likeCont;
    private int dislikeCont;
    private Set <Cliente> votantes;
    @NotNull
    private float precio;
    @NotNull
    @ManyToOne
    private Cliente creador;
    @OneToMany(cascade=ALL,mappedBy="apunte")
    private Set <Compra> compras;
    @ManyToMany
    @JoinTable(name="apunte_pack",schema="serverA4db")
    private Set <Pack> packs;
    @NotNull
    @ManyToOne
    private Materia materia;
    
}
