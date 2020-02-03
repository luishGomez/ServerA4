package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase entity para las ofertas.
 * @author sergio
 */
@Entity
@Table(name = "oferta", schema = "serverA4db")
@NamedQueries({
   @NamedQuery(name="findAllOfertas",
            query="SELECT u FROM Oferta u ORDER BY u.idOferta DESC"
    )
})
@XmlRootElement
public class Oferta implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idOferta;
    @NotNull 
    private String titulo;
    @NotNull 
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @NotNull 
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.REMOVE)
    @JoinTable(name = "oferta_pack", schema = "serverA4db",joinColumns = @JoinColumn(name="ofertas_idOferta", referencedColumnName="idOferta"),
            inverseJoinColumns = @JoinColumn(name = "packs_idPack", referencedColumnName="idPack"))
    private Set<Pack> packs;
    @NotNull
    private float rebaja;

    /**
     * @return the idOferta
     */
    public Integer getIdOferta() {
        return idOferta;
    }

    /**
     * @param idOferta the idOferta to set
     */
    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the packs
     */
    
    public Set<Pack> getPacks() {
        return packs;
    }

    /**
     * @param packs the packs to set
     */
    public void setPacks(Set<Pack> packs) {
        this.packs = packs;
    }
       /**
     * @return the rebaja
     */
    public float getRebaja() {
        return rebaja;
    }

    /**
     * @param rebaja the rebaja to set
     */
    public void setRebaja(float rebaja) {
        this.rebaja = rebaja;
    }
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOferta != null ? idOferta.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.idOferta == null && other.idOferta != null) || (this.idOferta != null && !this.idOferta.equals(other.idOferta))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "serverApuntes4.entity.Oferta[ idOferta=" + idOferta + " ]";
    }

 
    
}
