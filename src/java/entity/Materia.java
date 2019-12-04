package entity;

import java.io.Serializable;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis
 */
@Entity
@Table(name="materia",schema="serverA4db")
@XmlRootElement
public class Materia implements Serializable{
    private static final Long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idMateria;
    @NotNull
    private String titulo;
    @NotNull
    private String descripcion;
    @OneToMany(cascade=ALL,mappedBy="materia")
    private Set<Apunte> apuntes;
    
    public Integer getIdMateria() {
        return idMateria;
    }
    
    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
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
    
    @XmlTransient
    public Set<Apunte> getApuntes() {
        return apuntes;
    }
    
    public void setApuntes(Set<Apunte> apuntes) {
        this.apuntes = apuntes;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMateria != null ? idMateria.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.idMateria == null && other.idMateria != null) || (this.idMateria != null && !this.idMateria.equals(other.idMateria))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "serverApuntes4.entity.Materia[ idMateria=" + idMateria + " ]";
    }
}
