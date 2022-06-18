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
import javax.validation.constraints.Size;

/**
 *
 * @author joaov
 */
@Entity
@Table(name = "deposito")
@NamedQueries({
    @NamedQuery(name = "Deposito.findAll", query = "SELECT d FROM Deposito d"),
    @NamedQuery(name = "Deposito.findByIdDeposito", query = "SELECT d FROM Deposito d WHERE d.idDeposito = :idDeposito"),
    @NamedQuery(name = "Deposito.findByValor", query = "SELECT d FROM Deposito d WHERE d.valor = :valor"),
    @NamedQuery(name = "Deposito.findByTipo", query = "SELECT d FROM Deposito d WHERE d.tipo = :tipo"),
    @NamedQuery(name = "Deposito.findByDataDeposito", query = "SELECT d FROM Deposito d WHERE d.dataDeposito = :dataDeposito"),
    @NamedQuery(name = "Deposito.findByDataAutorizacao", query = "SELECT d FROM Deposito d WHERE d.dataAutorizacao = :dataAutorizacao")})
public class Deposito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDeposito")
    private Integer idDeposito;
    @Column(name = "valor")
    private double valor;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @NotNull
    @Column(name = "dataDeposito")
    @Temporal(TemporalType.DATE)
    private Date dataDeposito;
    @Column(name = "dataAutorizacao")
    @Temporal(TemporalType.DATE)
    private Date dataAutorizacao;
    @JoinColumn(name = "Conta_idConta", referencedColumnName = "idConta")
    @ManyToOne(optional = false)
    private Conta contaidConta;

    public Deposito() {
    }

    public Deposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }

    public Deposito(Integer idDeposito, double valor, String tipo, Date dataDeposito) {
        this.idDeposito = idDeposito;
        this.valor = valor;
        this.tipo = tipo;
        this.dataDeposito = dataDeposito;
    }

    public Integer getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataDeposito() {
        return dataDeposito;
    }

    public void setDataDeposito(Date dataDeposito) {
        this.dataDeposito = dataDeposito;
    }

    public Date getDataAutorizacao() {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(Date dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
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
        hash += (idDeposito != null ? idDeposito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deposito)) {
            return false;
        }
        Deposito other = (Deposito) object;
        if ((this.idDeposito == null && other.idDeposito != null) || (this.idDeposito != null && !this.idDeposito.equals(other.idDeposito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.ifsp.pep.model.Deposito[ idDeposito=" + idDeposito + " ]";
    }
    
}
