/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.SaqueDAO;
import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Saque;
import br.edu.ifsp.pep.model.Usuario;
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
public class SaqueController implements Serializable {
    
    //@Inject
    private SaqueDAO saqueDAO;
    private Saque saque = new Saque();
    private Saque sSelecionado;
    private List<Saque> saques;

    public SaqueDAO getSaqueDAO() {
        return saqueDAO;
    }

    public void setSaqueDAO(SaqueDAO saqueDAO) {
        this.saqueDAO = saqueDAO;
    }

    public Saque getSaque() {
        return saque;
    }

    public void setSaque(Saque saque) {
        this.saque = saque;
    }

    public Saque getsSelecionado() {
        return sSelecionado;
    }

    public void setsSelecionado(Saque sSelecionado) {
        this.sSelecionado = sSelecionado;
    }

    public List<Saque> getSaques() {
        return saques;
    }

    public void setSaques(List<Saque> saques) {
        this.saques = saques;
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    

}
