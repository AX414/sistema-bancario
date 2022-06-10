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
@Table(name = "transferencia")
@NamedQueries({
    @NamedQuery(name = "Transferencia.findAll", query = "SELECT t FROM Transferencia t"),
    @NamedQuery(name = "Transferencia.findByIdTransferencia", query = "SELECT t FROM Transferencia t WHERE t.idTransferencia = :idTransferencia"),
    @NamedQuery(name = "Transferencia.findByData", query = "SELECT t FROM Transferencia t WHERE t.data = :data"),
    @NamedQuery(name = "Transferencia.findByValor", query = "SELECT t FROM Transferencia t WHERE t.valor = :valor"),
    @NamedQuery(name = "Transferencia.findByIdConta", query = "SELECT t FROM Transferencia t WHERE t.idConta = :idConta"),
    @NamedQuery(name = "Transferencia.findByIdAgencia", query = "SELECT t FROM Transferencia t WHERE t.idAgencia = :idAgencia")})
public class Transferencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransferencia")
    private Integer idTransferencia;
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @NotNull
    @Column(name = "valor")
    private double valor;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idConta")
    private String idConta;
    @NotNull
    @Column(name = "idAgencia")
    private int idAgencia;
    @JoinColumn(name = "Conta_idConta", referencedColumnName = "idConta")
    @ManyToOne(optional = false)
    private Conta contaidConta;

    public Transferencia() {
    }

    public Transferencia(Integer idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public Transferencia(Integer idTransferencia, Date data, double valor, String idConta, int idAgencia) {
        this.idTransferencia = idTransferencia;
        this.data = data;
        this.valor = valor;
        this.idConta = idConta;
        this.idAgencia = idAgencia;
    }

    public Integer getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Integer idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getIdConta() {
        return idConta;
    }

    public void setIdConta(String idConta) {
        this.idConta = idConta;
    }

    public int getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(int idAgencia) {
        this.idAgencia = idAgencia;
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
        hash += (idTransferencia != null ? idTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transferencia)) {
            return false;
        }
        Transferencia other = (Transferencia) object;
        if ((this.idTransferencia == null && other.idTransferencia != null) || (this.idTransferencia != null && !this.idTransferencia.equals(other.idTransferencia))) {
            return false;
        }
        return true;
    }
    
}
