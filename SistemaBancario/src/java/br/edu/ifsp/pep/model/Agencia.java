/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author joaov
 */
@Entity
@Table(name = "agencia")
@NamedQueries({
    @NamedQuery(name = "Agencia.findAll", query = "SELECT a FROM Agencia a"),
    @NamedQuery(name = "Agencia.findByIdAgencia", query = "SELECT a FROM Agencia a WHERE a.idAgencia = :idAgencia"),
    @NamedQuery(name = "Agencia.findByNome", query = "SELECT a FROM Agencia a WHERE a.nome = :nome"),
    @NamedQuery(name = "Agencia.findByNrAgencia", query = "SELECT a FROM Agencia a WHERE a.nrAgencia = :nrAgencia")})
public class Agencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAgencia")
    private Integer idAgencia;
    @Basic(optional = false)
    @Size(min = 1, max = 45)
    @NotNull
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Size(min = 1, max = 15)
    @NotNull
    @Column(name = "nrAgencia")
    private String nrAgencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agenciaidAgencia")
    private ArrayList<Conta> contas;

    public Agencia() {
    }

    public Agencia(Integer idAgencia) {
        this.idAgencia = idAgencia;
    }

    public int getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(int idAgencia) {
        this.idAgencia = idAgencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNrAgencia() {
        return nrAgencia;
    }

    public void setNrAgencia(String nrAgencia) {
        this.nrAgencia = nrAgencia;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public void setContas(ArrayList<Conta> contas) {
        this.contas = contas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.idAgencia;
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
        final Agencia other = (Agencia) obj;
        if (this.idAgencia != other.idAgencia) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "br.edu.ifsp.pep.model.Agencia[ idAgencia=" + idAgencia + " ]";
    }

}
