package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sergio
 */
@Entity
@Table(name = "usuario", schema = "serverA4DB")
@Inheritance(strategy=TABLE_PER_CLASS)
@XmlRootElement
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE) 
    private Integer id;
    @NotNull
    private String login;
    @NotNull
    private String email;
    @NotNull
    private String nombreCompleto;
    @NotNull
    private UserStatus status;
    @NotNull
    private UserPrivilege privilegio;
    @NotNull
    private String contrasenia;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcceso;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoCambioContrasenia;
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    /**
     * @return the status
     */
    public UserStatus getStatus() {
        return status;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    /**
     * @return the privilegio
     */
    public UserPrivilege getPrivilegio() {
        return privilegio;
    }
    
    /**
     * @param privilegio the privilegio to set
     */
    public void setPrivilegio(UserPrivilege privilegio) {
        this.privilegio = privilegio;
    }
    
    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }
    
    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    /**
     * @return the ultimoAcceso
     */
    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }
    
    /**
     * @param ultimoAcceso the ultimoAcceso to set
     */
    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
    
    /**
     * @return the ultimoCambioContrasenia
     */
    public Date getUltimoCambioContrasenia() {
        return ultimoCambioContrasenia;
    }
    
    /**
     * @param ultimoCambioContrasenia the ultimoCambioContrasenia to set
     */
    public void setUltimoCambioContrasenia(Date ultimoCambioContrasenia) {
        this.ultimoCambioContrasenia = ultimoCambioContrasenia;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "serverApuntes4.entity.User[ id=" + id + " ]";
    }
    
}
