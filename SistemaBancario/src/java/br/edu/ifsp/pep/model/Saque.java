/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author joaov
 */
@Entity
@Table(name = "saque")
@NamedQueries({
    @NamedQuery(name = "Saque.findAll", query = "SELECT s FROM Saque s"),
    @NamedQuery(name = "Saque.findByIdSaque", query = "SELECT s FROM Saque s WHERE s.idSaque = :idSaque"),
    @NamedQuery(name = "Saque.findByValor", query = "SELECT s FROM Saque s WHERE s.valor = :valor"),
    @NamedQuery(name = "Saque.findByDataSaque", query = "SELECT s FROM Saque s WHERE s.dataSaque = :dataSaque")})
public class Saque implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSaque")
    private Integer idSaque;
    @NotNull
    @Column(name = "valor")
    private double valor;
    @NotNull
    @Column(name = "dataSaque")
    @Temporal(TemporalType.DATE)
    private Date dataSaque;
    @JoinColumn(name = "Conta_idConta", referencedColumnName = "idConta")
    @ManyToOne(optional = false)
    private Conta contaidConta;

    public Saque() {
    }

    public Saque(Integer idSaque) {
        this.idSaque = idSaque;
    }

    public Saque(Integer idSaque, double valor, Date dataSaque) {
        this.idSaque = idSaque;
        this.valor = valor;
        this.dataSaque = dataSaque;
    }

    public Integer getIdSaque() {
        return idSaque;
    }

    public void setIdSaque(Integer idSaque) {
        this.idSaque = idSaque;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataSaque() {
        return dataSaque;
    }

    public void setDataSaque(Date dataSaque) {
        this.dataSaque = dataSaque;
    }

    public Conta getContaidConta() {
        return contaidConta;
    }

    public void setContaidConta(Conta contaidConta) {
        this.contaidConta = contaidConta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSaque != null ? idSaque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saque)) {
            return false;
        }
        Saque other = (Saque) object;
        if ((this.idSaque == null && other.idSaque != null) || (this.idSaque != null && !this.idSaque.equals(other.idSaque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.ifsp.pep.model.Saque[ idSaque=" + idSaque + " ]";
    }
    
}
