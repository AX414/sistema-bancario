/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author joaov
 */
@Entity
@Table(name = "conta")
@NamedQueries({
    @NamedQuery(name = "Conta.findAll", query = "SELECT c FROM Conta c"),
    @NamedQuery(name = "Conta.findByIdConta", query = "SELECT c FROM Conta c WHERE c.idConta = :idConta"),
    @NamedQuery(name = "Conta.findBySenha", query = "SELECT c FROM Conta c WHERE c.senha = :senha"),
    @NamedQuery(name = "Conta.findByTipo", query = "SELECT c FROM Conta c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Conta.findByLimite", query = "SELECT c FROM Conta c WHERE c.limite = :limite"),
    @NamedQuery(name = "Conta.findBySaldo", query = "SELECT c FROM Conta c WHERE c.saldo = :saldo")})
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "idConta")
    private Integer idConta;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "senha")
    private String senha;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @NotNull
    @Column(name = "limite")
    private double limite;
    @NotNull
    @Column(name = "saldo")
    private double saldo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contaidConta")
    private ArrayList<Transferencia> listaTransferencias;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contaidConta")
    private ArrayList<Saque> listaSaques;
    @JoinColumn(name = "Agencia_idAgencia", referencedColumnName = "idAgencia")
    @ManyToOne(optional = false)
    private Agencia agenciaidAgencia;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario usuarioidUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contaidConta")
    private ArrayList<Deposito> listaDepositos;

    public Conta() {
    }

    public Conta(Integer idConta) {
        this.idConta = idConta;
    }

    public Conta(Integer idConta, String senha, String tipo, String status, double limite, double saldo) {
        this.idConta = idConta;
        this.senha = senha;
        this.tipo = tipo;
        this.status = status;
        this.limite = limite;
        this.saldo = saldo;
    }

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public ArrayList<Transferencia> getListaTransferencias() {
        return listaTransferencias;
    }

    public void setListaTransferencias(ArrayList<Transferencia> listaTransferencias) {
        this.listaTransferencias = listaTransferencias;
    }

    public ArrayList<Saque> getListaSaques() {
        return listaSaques;
    }

    public void setListaSaques(ArrayList<Saque> listaSaques) {
        this.listaSaques = listaSaques;
    }

    public Agencia getAgenciaidAgencia() {
        return agenciaidAgencia;
    }

    public void setAgenciaidAgencia(Agencia agenciaidAgencia) {
        this.agenciaidAgencia = agenciaidAgencia;
    }

    public Usuario getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(Usuario usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public ArrayList<Deposito> getListaDepositos() {
        return listaDepositos;
    }

    public void setListaDepositos(ArrayList<Deposito> listaDepositos) {
        this.listaDepositos = listaDepositos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idConta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conta other = (Conta) obj;
        if (!Objects.equals(this.idConta, other.idConta)) {
            return false;
        }
        return true;
    }

   
    
}
