package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase entity de Compra.
 * @author Luis
 */
@NamedQueries({
    @NamedQuery(
            name="findAllCompraByClienteId",
            query="SELECT c FROM Compra c WHERE c.propietario.id = :idCliente"),
    @NamedQuery(
            name="findAllCompra",
            query="SELECT c FROM Compra c ORDER BY c.idCompra")
})
@Entity
@Table(name="compra",schema="serverA4db")
@XmlRootElement
public class Compra implements Serializable{
    private static final Long serialVersionUID = 1L;
    
    @EmbeddedId
    private CompraId idCompra;
    @MapsId("clienteId")
    @ManyToOne
    private Cliente propietario;
    @MapsId("apunteId")
    @ManyToOne
    private Apunte apunte;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
        
    /**
     * Devuelve el valor de idCompra.
     * @return Id de la compra.
     */
    public CompraId getIdCompra() {
        return idCompra;
    }
    /**
     * Le da un valor a idCompra.
     * @param idCompra Id de la compra.
     */
    public void setIdCompra(CompraId idCompra) {
        this.idCompra = idCompra;
    }
    /**
     * Devuelve el valor de propietario.
     * @return Cliente de la compra.
     */
    public Cliente getPropietario() {
        return propietario;
    }
    /**
     * Le da un valor a propietario.
     * @param propietario Cliente de la compra.
     */
    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }
    /**
     * Devuelve el valor de apunte.
     * @return Apunte de la compra.
     */
    public Apunte getApunte() {
        return apunte;
    }
    /**
     * Le da un valor a apunte.
     * @param apunte Apunte de la compra.
     */
    public void setApunte(Apunte apunte) {
        this.apunte = apunte;
    }
    /**
     * Devuelve el valor de fecha.
     * @return Fecha de la compra.
     */
    public Date getFecha() {
        return fecha;
    }
    /**
     * Le da un valor a fecha.
     * @param fecha Fecha de la compra.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPropietario() != null && getApunte() != null ? getPropietario().hashCode()+ getApunte().hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.getPropietario()== null && other.getPropietario() != null) || (this.getPropietario() != null && !this.propietario.equals(other.propietario))
                || (this.getApunte()== null && other.getApunte() != null) || (this.getApunte() != null && !this.apunte.equals(other.apunte))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        String retorno = "entity.Compra[ cliente=";
        if(getPropietario() != null) retorno= retorno + getPropietario().getId();
        if(getApunte() != null) retorno = retorno + " apunte=" + getApunte().getIdApunte();
        
        return retorno;
    }
}
