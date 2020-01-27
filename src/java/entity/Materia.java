package entity;

import java.io.Serializable;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase entity de Materia.
 * @author Luis
 */
@NamedQuery(
        name="findAllMateria",
        query="SELECT m FROM Materia m ORDER BY m.idMateria")
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
    /**
     * Devuelve el valor de idMateria.
     * @return Id de la materia.
     */
    public Integer getIdMateria() {
        return idMateria;
    }
    /**
     * Le da un valor a idMateria.
     * @param idMateria Id de la materia.
     */
    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }
    /**
     * Devuelve el valor de titulo.
     * @return Título de la materia.
     */
    public String getTitulo() {
        return titulo;
    }
    /**
     * Le da un valor a titulo.
     * @param titulo Título de la materia.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    /**
     * Devuelve el valor de descripcion.
     * @return Descripción de la materia.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Le da un valor a descripcion.
     * @param descripcion Descripción de la materia.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Devuelve el valor de apuntes.
     * @return Apuntes de la materia.
     */
    @XmlTransient
    public Set<Apunte> getApuntes() {
        return apuntes;
    }
    /**
     * Le da un valor a apuntes.
     * @param apuntes Apuntes de la materia.
     */
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
