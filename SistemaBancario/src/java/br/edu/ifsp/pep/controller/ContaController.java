/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.dao.UsuarioDAO;
import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Usuario;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author joaov
 */
public class ContaController {

    @Inject
    private ContaDAO contaDAO;
    private Conta conta = new Conta();
    private Conta cSelecionada;
    private List<Conta> contas;
    @Inject
    private UsuarioDAO usuarioDAO;    

    public ContaDAO getContaDAO() {
        return contaDAO;
    }

    public void setContaDAO(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Conta getcSelecionada() {
        return cSelecionada;
    }

    public void setcSelecionada(Conta cSelecionada) {
        this.cSelecionada = cSelecionada;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void desativarConta() {
        if (cSelecionada != null) {
            cSelecionada.setStatus("Conta Desativada");
            contaDAO.edit(cSelecionada);
            this.contas = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Conta Desativada");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Info Message", "Selecione uma conta para desativar");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
