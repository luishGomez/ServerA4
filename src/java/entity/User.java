/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sergio
 */
@Entity
@Table(name = "usuario", schema = "serverA4DB")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) //generar automaticament
    @NotNull private Integer id;
    @NotNull private String login;
    @NotNull private String email;
    @NotNull private String nombreCompleto;
    @NotNull private UserStatus status;
    @NotNull private UserPrivilege privilegio;
    @NotNull private String contrasenia;
    private Timestamp ultimoAcceso;
    private Timestamp ultimoCambioContrasenia;

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
    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    /**
     * @param ultimoAcceso the ultimoAcceso to set
     */
    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    /**
     * @return the ultimoCambioContrasenia
     */
    public Timestamp getUltimoCambioContrasenia() {
        return ultimoCambioContrasenia;
    }

    /**
     * @param ultimoCambioContrasenia the ultimoCambioContrasenia to set
     */
    public void setUltimoCambioContrasenia(Timestamp ultimoCambioContrasenia) {
        this.ultimoCambioContrasenia = ultimoCambioContrasenia;
    }
    
    
}
