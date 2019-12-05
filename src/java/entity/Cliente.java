package entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ricardo Peinado Lastra
 */
@Entity
@Table(name="cliente",schema="serverA4db")
@XmlRootElement
public class Cliente extends User implements Serializable{
    private static final long serialVersionUID=1L;
    
    @OneToMany(cascade=ALL,mappedBy="propietario")
    private Set <Compra> compras;
    @OneToMany(cascade=ALL,mappedBy="creador")
    private Set <Apunte> apuntes;
    private float saldo;
    @Lob
    private Blob foto;
    
    /**
     * @return the compras
     */
    @XmlTransient
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
     * @return the apuntes
     */
    @XmlTransient
    public Set <Apunte> getApuntes() {
        return apuntes;
    }
    
    /**
     * @param apuntes the apuntes to set
     */
    public void setApuntes(Set <Apunte> apuntes) {
        this.apuntes = apuntes;
    }
    
    /**
     * @return the saldo
     */
    public float getSaldo() {
        return saldo;
    }
    
    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
    /**
     * @return the foto
     */
    public Blob getFoto() {
        return foto;
    }
    
    /**
     * @param foto the foto to set
     */
    public void setFoto(Blob foto) {
        this.foto = foto;
    }
    
    
}
