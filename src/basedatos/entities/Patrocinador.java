/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "PATROCINADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patrocinador.findAll", query = "SELECT p FROM Patrocinador p")
    , @NamedQuery(name = "Patrocinador.findById", query = "SELECT p FROM Patrocinador p WHERE p.id = :id")
    , @NamedQuery(name = "Patrocinador.findByNombre", query = "SELECT p FROM Patrocinador p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Patrocinador.findByDinero", query = "SELECT p FROM Patrocinador p WHERE p.dinero = :dinero")
    , @NamedQuery(name = "Patrocinador.findByFeccontrato", query = "SELECT p FROM Patrocinador p WHERE p.feccontrato = :feccontrato")})
public class Patrocinador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DINERO")
    private String dinero;
    @Column(name = "FECCONTRATO")
    @Temporal(TemporalType.DATE)
    private Date feccontrato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patrocinador")
    private Collection<Equipos> equiposCollection;

    public Patrocinador() {
    }

    public Patrocinador(Integer id) {
        this.id = id;
    }

    public Patrocinador(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDinero() {
        return dinero;
    }

    public void setDinero(String dinero) {
        this.dinero = dinero;
    }

    public Date getFeccontrato() {
        return feccontrato;
    }

    public void setFeccontrato(Date feccontrato) {
        this.feccontrato = feccontrato;
    }

    @XmlTransient
    public Collection<Equipos> getEquiposCollection() {
        return equiposCollection;
    }

    public void setEquiposCollection(Collection<Equipos> equiposCollection) {
        this.equiposCollection = equiposCollection;
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
        if (!(object instanceof Patrocinador)) {
            return false;
        }
        Patrocinador other = (Patrocinador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basedatos.entities.Patrocinador[ id=" + id + " ]";
    }
    
}
