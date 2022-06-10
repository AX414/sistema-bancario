/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.model.Transferencia;
import br.edu.ifsp.pep.dao.TransferenciaDAO;
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
public class TransferenciaController implements Serializable {
    
    //@Inject
    private TransferenciaDAO TransferenciaDAO;
    private Transferencia transferencia = new Transferencia();
    private Transferencia tSelecionada;
    private List<Transferencia> transferencias;

    public TransferenciaDAO getTransferenciaDAO() {
        return TransferenciaDAO;
    }

    public void setTransferenciaDAO(TransferenciaDAO TransferenciaDAO) {
        this.TransferenciaDAO = TransferenciaDAO;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public Transferencia gettSelecionada() {
        return tSelecionada;
    }

    public void settSelecionada(Transferencia tSelecionada) {
        this.tSelecionada = tSelecionada;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
