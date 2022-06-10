/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.model.Deposito;
import br.edu.ifsp.pep.dao.DepositoDAO;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author joaov
 */
@Named
@SessionScoped
public class DepositoController implements Serializable {

    //@Inject
    private DepositoDAO depositoDAO;
    private Deposito deposito = new Deposito();
    private Deposito dSelecionado;
    private List<Deposito> depositos;

    public DepositoDAO getDepositoDAO() {
        return depositoDAO;
    }

    public void setDepositoDAO(DepositoDAO depositoDAO) {
        this.depositoDAO = depositoDAO;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

    public Deposito getdSelecionado() {
        return dSelecionado;
    }

    public void setdSelecionado(Deposito dSelecionado) {
        this.dSelecionado = dSelecionado;
    }

    public List<Deposito> getDepositos() {
    if(this.depositos == null){
        this.depositos = depositoDAO.buscarTodos();
    }
        return depositos;
    }

    public void setDepositos(List<Deposito> depositos) {
        this.depositos = depositos;
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
