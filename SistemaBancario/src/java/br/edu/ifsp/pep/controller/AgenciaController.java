/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.AgenciaDAO;
import br.edu.ifsp.pep.model.Agencia;
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
public class AgenciaController implements Serializable {

    @Inject
    private AgenciaDAO agenciaDAO;
    private Agencia agencia = new Agencia();
    private Agencia aSelecionada;
    private List<Agencia> agencias;

    public AgenciaController() {
        System.out.println("construtor agencia.");
        this.agencia = new Agencia();
    }

    public AgenciaDAO getAgenciaDAO() {
        return agenciaDAO;
    }

    public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
        this.agenciaDAO = agenciaDAO;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public Agencia getaSelecionada() {
        return aSelecionada;
    }

    public void setaSelecionada(Agencia aSelecionada) {
        this.aSelecionada = aSelecionada;
    }

    public List<Agencia> getAgencias() {
        if (this.agencias == null) {
            System.out.println("Carregando...");
            this.agencias = agenciaDAO.buscarTodas();
        }
        return agencias;
    }

    public void setAgencias(List<Agencia> agencias) {
        this.agencias = agencias;
    }

    public void adicionar() {
        agencia.setContas(null);
        agenciaDAO.insert(agencia);
        this.agencias = null;
        System.out.println("adicionou uma agencia na lista.");
        addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Cadastro realizado.");
    }

    public void excluir() {
        if (aSelecionada != null) {
            agenciaDAO.delete(aSelecionada);
            this.agencias = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Exclusão realizada");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Info Message", "Selecione uma agencia para excluir");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
