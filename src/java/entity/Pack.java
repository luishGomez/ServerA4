package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
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
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase entity de Pack.
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
    //@NotNull
    @ManyToMany(mappedBy="packs",fetch=EAGER,cascade=CascadeType.REMOVE)
    private Set<Apunte> apuntes;
    @ManyToMany(mappedBy="packs",fetch=EAGER,cascade=CascadeType.REMOVE)
    private Set<Oferta> ofertas;
    /**
     * Devuelve el valor de idPack.
     * @return Id del pack.
     */
    public Integer getIdPack() {
        return idPack;
    }
    /**
     * Le da un valor a idPack.
     * @param idPack Id del pack.
     */
    public void setIdPack(Integer idPack) {
        this.idPack = idPack;
    }
    /**
     * Devuelve el valor de titulo.
     * @return Título del pack.
     */
    public String getTitulo() {
        return titulo;
    }
    /**
     * Le da un valor a titulo.
     * @param titulo Título del pack.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    /**
     * Devuelve el valor de descripcion.
     * @return Descripción del pack.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Le da un valor a descripcion.
     * @param descripcion Descripción del pack.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Devuelve el valor de fechaModificacion.
     * @return Fecha de modificación del pack.
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }
    /**
     * Le da un valor a fechaModificacion.
     * @param fechaModificacion Fecha de modificación del pack.
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    /**
     * Devuelve el valor de apuntes.
     * @return Apuntes del pack.
     */
    public Set<Apunte> getApuntes() {
        return apuntes;
    }
    /**
     * Le da un valor a apuntes.
     * @param apuntes Apuntes del pack.
     */
    public void setApuntes(Set<Apunte> apuntes) {
        this.apuntes = apuntes;
    }
    /**
     * Devuelve el valor de ofertas.
     * @return Ofertas del pack.
     */
    @XmlTransient
    public Set<Oferta> getOfertas() {
        return ofertas;
    }
    /**
     * Le da un valor a ofertas.
     * @param ofertas Ofertas del pack.
     */
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
