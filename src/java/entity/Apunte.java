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

    /**
     * @return the idApunte
     */
    public Integer getIdApunte() {
        return idApunte;
    }

    /**
     * @param idApunte the idApunte to set
     */
    public void setIdApunte(Integer idApunte) {
        this.idApunte = idApunte;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the archivo
     */
    public Blob getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(Blob archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the fechaValidacion
     */
    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    /**
     * @param fechaValidacion the fechaValidacion to set
     */
    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    /**
     * @return the likeCont
     */
    public int getLikeCont() {
        return likeCont;
    }

    /**
     * @param likeCont the likeCont to set
     */
    public void setLikeCont(int likeCont) {
        this.likeCont = likeCont;
    }

    /**
     * @return the dislikeCont
     */
    public int getDislikeCont() {
        return dislikeCont;
    }

    /**
     * @param dislikeCont the dislikeCont to set
     */
    public void setDislikeCont(int dislikeCont) {
        this.dislikeCont = dislikeCont;
    }

    /**
     * @return the votantes
     */
    public Set <Cliente> getVotantes() {
        return votantes;
    }

    /**
     * @param votantes the votantes to set
     */
    public void setVotantes(Set <Cliente> votantes) {
        this.votantes = votantes;
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return the creador
     */
    public Cliente getCreador() {
        return creador;
    }

    /**
     * @param creador the creador to set
     */
    public void setCreador(Cliente creador) {
        this.creador = creador;
    }

    /**
     * @return the compras
     */
    public Set <Compra> getCompras() {
        return compras;
    }

    /**
     * @param compras the compras to set
     */
    public void setCompras(Set <Compra> compras) {
        this.compras = compras;
    }

    /**
     * @return the packs
     */
    public Set <Pack> getPacks() {
        return packs;
    }

    /**
     * @param packs the packs to set
     */
    public void setPacks(Set <Pack> packs) {
        this.packs = packs;
    }

    /**
     * @return the materia
     */
    public Materia getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    
    
    
}
