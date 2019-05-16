/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "EQUIPOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipos.findAll", query = "SELECT e FROM Equipos e")
    , @NamedQuery(name = "Equipos.findById", query = "SELECT e FROM Equipos e WHERE e.id = :id")
    , @NamedQuery(name = "Equipos.findByNombre", query = "SELECT e FROM Equipos e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Equipos.findByEmail", query = "SELECT e FROM Equipos e WHERE e.email = :email")
    , @NamedQuery(name = "Equipos.findByNumpilotos", query = "SELECT e FROM Equipos e WHERE e.numpilotos = :numpilotos")
    , @NamedQuery(name = "Equipos.findByPatrocinio", query = "SELECT e FROM Equipos e WHERE e.patrocinio = :patrocinio")
    , @NamedQuery(name = "Equipos.findByNumempleados", query = "SELECT e FROM Equipos e WHERE e.numempleados = :numempleados")
    , @NamedQuery(name = "Equipos.findByFeccreacion", query = "SELECT e FROM Equipos e WHERE e.feccreacion = :feccreacion")
    , @NamedQuery(name = "Equipos.findByNumcampeonatos", query = "SELECT e FROM Equipos e WHERE e.numcampeonatos = :numcampeonatos")
    , @NamedQuery(name = "Equipos.findByNumvictorias", query = "SELECT e FROM Equipos e WHERE e.numvictorias = :numvictorias")
    , @NamedQuery(name = "Equipos.findBySalariomedioequipo", query = "SELECT e FROM Equipos e WHERE e.salariomedioequipo = :salariomedioequipo")})
public class Equipos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NUMPILOTOS")
    private Integer numpilotos;
    @Column(name = "PATROCINIO")
    private Boolean patrocinio;
    @Column(name = "NUMEMPLEADOS")
    private Short numempleados;
    @Column(name = "FECCREACION")
    @Temporal(TemporalType.DATE)
    private Date feccreacion;
    @Column(name = "NUMCAMPEONATOS")
    private Short numcampeonatos;
    @Column(name = "NUMVICTORIAS")
    private Short numvictorias;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARIOMEDIOEQUIPO")
    private BigDecimal salariomedioequipo;
    @JoinColumn(name = "PATROCINADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Patrocinador patrocinador;

    public Equipos() {
    }

    public Equipos(Integer id) {
        this.id = id;
    }

    public Equipos(Integer id, String nombre) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumpilotos() {
        return numpilotos;
    }

    public void setNumpilotos(Integer numpilotos) {
        this.numpilotos = numpilotos;
    }

    public Boolean getPatrocinio() {
        return patrocinio;
    }

    public void setPatrocinio(Boolean patrocinio) {
        this.patrocinio = patrocinio;
    }

    public Short getNumempleados() {
        return numempleados;
    }

    public void setNumempleados(Short numempleados) {
        this.numempleados = numempleados;
    }

    public Date getFeccreacion() {
        return feccreacion;
    }

    public void setFeccreacion(Date feccreacion) {
        this.feccreacion = feccreacion;
    }

    public Short getNumcampeonatos() {
        return numcampeonatos;
    }

    public void setNumcampeonatos(Short numcampeonatos) {
        this.numcampeonatos = numcampeonatos;
    }

    public Short getNumvictorias() {
        return numvictorias;
    }

    public void setNumvictorias(Short numvictorias) {
        this.numvictorias = numvictorias;
    }

    public BigDecimal getSalariomedioequipo() {
        return salariomedioequipo;
    }

    public void setSalariomedioequipo(BigDecimal salariomedioequipo) {
        this.salariomedioequipo = salariomedioequipo;
    }

    public Patrocinador getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
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
        if (!(object instanceof Equipos)) {
            return false;
        }
        Equipos other = (Equipos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basedatos.entities.Equipos[ id=" + id + " ]";
    }
    
}
