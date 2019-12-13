package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis
 */
@NamedQuery(
    name="findAllPack",
    query="SELECT p FROM Pack p ORDER BY p.idPack")
@Entity
@Table(name="pack",schema="serverA4db")
@XmlRootElement
public class Pack implements Serializable{
    private static final Long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idPack;
    @NotNull
    private String titulo;
    @NotNull
    private String descripcion;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @NotNull
    @ManyToMany(mappedBy="packs",fetch=EAGER)
    private Set<Apunte> apuntes;
    @ManyToMany(mappedBy="packs")
    private Set<Oferta> ofertas;

    public Integer getIdPack() {
        return idPack;
    }

    public void setIdPack(Integer idPack) {
        this.idPack = idPack;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    
    public Set<Apunte> getApuntes() {
        return apuntes;
    }

    public void setApuntes(Set<Apunte> apuntes) {
        this.apuntes = apuntes;
    }

    @XmlTransient
    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

   @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPack != null ? idPack.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pack)) {
            return false;
        }
        Pack other = (Pack) object;
        if ((this.idPack == null && other.idPack != null) || (this.idPack != null && !this.idPack.equals(other.idPack))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "serverApuntes4.entity.Pack[ idPack=" + idPack + " ]";
    }
}
