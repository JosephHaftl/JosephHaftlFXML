/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph Haftl
 */
@Entity
@Table(name = "FRIENDMODEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Friendmodel.findAll", query = "SELECT f FROM Friendmodel f")
    , @NamedQuery(name = "Friendmodel.findById", query = "SELECT f FROM Friendmodel f WHERE f.id = :id")
    , @NamedQuery(name = "Friendmodel.findByName", query = "SELECT f FROM Friendmodel f WHERE f.name = :name")
    , @NamedQuery(name = "Friendmodel.findByStatus", query = "SELECT f FROM Friendmodel f WHERE f.status = :status")
    , @NamedQuery(name = "Friendmodel.findByNotes", query = "SELECT f FROM Friendmodel f WHERE f.notes = :notes")
    //custom queries
    , @NamedQuery(name = "Friendmodel.findByNameAndId", query = "SELECT f FROM Friendmodel f WHERE f.name = :name and f.id = :id" )
    , @NamedQuery(name = "Friendmodel.findByNameContaining", query = "SELECT f FROM Friendmodel f WHERE f.name LIKE :name")
    , @NamedQuery(name = "Friendmodel.findbyStatusAndName", query = "SELECT f FROM Friendmodel f WHERE f.status = :status and f.name = :name")
        
   })     
public class Friendmodel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "STATUS")
    private Boolean status;
    @Column(name = "NOTES")
    private String notes;

    public Friendmodel() {
    }

    public Friendmodel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        if (!(object instanceof Friendmodel)) {
            return false;
        }
        Friendmodel other = (Friendmodel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Friendmodel[ id=" + id + " ]";
    }
    
}
