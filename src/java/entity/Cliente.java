package entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Set;
import javax.persistence.Basic;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Es una clase JPA para almacenar los datos de un Cliente.
 * @author Ricardo Peinado Lastra
 */

@NamedQuery(name="findAllCliente", query="SELECT a FROM Cliente a ORDER BY a.nombreCompleto ASC")
@Entity
@Table(name="cliente",schema="serverA4db")
@XmlRootElement
public class Cliente extends User implements Serializable{
    private static final long serialVersionUID=1L;
    
    @OneToMany(cascade=ALL,mappedBy="propietario")
    private Set <Compra> compras;
    @OneToMany(cascade=ALL,mappedBy="creador",fetch=EAGER)
    private Set <Apunte> apuntes;
    @ManyToMany(mappedBy="votantes",fetch = FetchType.EAGER)
    private Set <Apunte> misVotaciones;
    private float saldo;
    @Lob
    @Basic(fetch=EAGER)
    private byte[] foto;
    
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
    public byte[] getFoto() {
        return foto;
    }
    
    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    /**
     * @return the misVotaciones
     */
    @XmlTransient
    public Set <Apunte> getMisVotaciones() {
        return misVotaciones;
    }
    
    /**
     * @param misVotaciones the misVotaciones to set
     */
    public void setMisVotaciones(Set <Apunte> misVotaciones) {
        this.misVotaciones = misVotaciones;
    }
    
    
    
    
}
