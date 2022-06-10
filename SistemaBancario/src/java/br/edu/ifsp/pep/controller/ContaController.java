/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.AgenciaDAO;
import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.dao.UsuarioDAO;
import br.edu.ifsp.pep.model.Agencia;
import br.edu.ifsp.pep.model.Conta;
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
public class ContaController implements Serializable {

    @Inject
    private ContaDAO contaDAO;
    private Conta conta;
    private Conta cSelecionada;
    private List<Conta> contasAtivadas;
    private List<Conta> contasDesativadas;
    @Inject
    private UsuarioDAO usuarioDAO;
    @Inject
    private UsuarioController usuarioController;
    //private boolean minhasContas = false; 
    private String nrAgencia;
    @Inject
    private AgenciaDAO agenciaDAO;

    public ContaController() {
        System.out.println("construtor conta.");
        this.conta = new Conta();
    }

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

    public List<Conta> getContasAtivadas() {
        if (contasAtivadas == null) {
            this.contasAtivadas = contaDAO.buscarTodas("Ativada");
        }
        return contasAtivadas;
    }

    public void setContasAtivadas(List<Conta> contasAtivadas) {
        this.contasAtivadas = contasAtivadas;
    }

    public List<Conta> getContasDesativadas() {
        if (contasDesativadas == null) {
            this.contasDesativadas = contaDAO.buscarTodas("Desativada");
        }
        return contasDesativadas;
    }

    public void setContasDesativadas(List<Conta> contasDesativadas) {
        this.contasDesativadas = contasDesativadas;
    }

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    /*
    public boolean isMinhasContas() {
        return minhasContas;
    }

    public void setMinhasContas(boolean minhasContas) {
        this.minhasContas = minhasContas;
    }
     */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /*
    public void mudaValor(){
            if(this.minhasContas == false){
                this.minhasContas = true;
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Apenas suas Contas");
            } else{
                this.minhasContas = false;
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Apresentar Todas as Contas");
            }
    }


    public List apresentaContas(){
            if(this.minhasContas == true){
                this.setContas(contaDAO.buscarTodasMinhasContas(this.usuarioController.getUsuarioLogado()
                                                                                      .getIdUsuario())); 
                return contas;
            } else {
                return contas;
            }
    }
     */

    public String getNrAgencia() {
        return nrAgencia;
    }

    public void setNrAgencia(String nrAgencia) {
        this.nrAgencia = nrAgencia;
    }

    public void adicionar() {
        Agencia agenciaRetornada = agenciaDAO.buscarPorNrAgencia(nrAgencia);
       
        //Anula os históricos de movimentações
        conta.setListaDepositos(null);
        conta.setListaSaques(null);
        conta.setListaTransferencias(null);

        conta.setSaldo(0);
        conta.setStatus("Ativada");
        
        
        if(agenciaRetornada == null){
            addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "A agencia informada não existe. Tente novamente");
            return;
        }

        if (conta.getTipo().equals("Comum")) {
            if (conta.getLimite() != 0) {
                conta.setLimite(0);
                addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Contas Comuns não possuem limite, o valor inserido foi desconsiderado.");
            }
        }

        contaDAO.insert(conta);
        this.contasAtivadas = null;
        System.out.println("adicionou uma conta na lista.");
        addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Cadastro realizado.");
    }

    public void desativarConta() {
        if (cSelecionada != null) {
            cSelecionada.setStatus("Desativada");
            contaDAO.edit(cSelecionada);
            this.contasAtivadas = null;
            this.contasDesativadas = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta Desativada");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Informação", "Selecione uma conta para desativar");
        }
    }

    public void reativarConta() {
        if (cSelecionada != null) {
            cSelecionada.setStatus("Ativada");
            contaDAO.edit(cSelecionada);
            this.contasAtivadas = null;
            this.contasDesativadas = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta Ativada");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Informação", "Selecione uma conta para reativar");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
