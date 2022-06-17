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
import br.edu.ifsp.pep.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author joaov
 */
@Named
@RequestScoped
public class ContaController implements Serializable {

    @Inject
    private ContaDAO contaDAO;
    @Inject
    private UsuarioDAO usuarioDAO;
    @Inject
    private AgenciaDAO agenciaDAO;
    @Inject
    private UsuarioController usuarioController;

    private Conta conta;
    private Conta cSelecionada;
    private Conta cAtivadaSelecionada;
    private Conta cDesativadaSelecionada;
        
    private String nrAgencia;
    private String cpf;
    private String estado;

    private List<Conta> contasAtivadas;
    private List<Conta> contasDesativadas;
    private List<Conta> minhasContasAtivadas;
    private List<Conta> minhasContasDesativadas;

    public ContaController() {
        System.out.println("construtor conta.");
        this.cSelecionada = null;
        this.cAtivadaSelecionada = null;
        this.cDesativadaSelecionada = null;

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

    public Conta getcAtivadaSelecionada() {
        return cAtivadaSelecionada;
    }

    public void setcAtivadaSelecionada(Conta cAtivadaSelecionada) {
        this.cAtivadaSelecionada = cAtivadaSelecionada;
    }

    public Conta getcDesativadaSelecionada() {
        return cDesativadaSelecionada;
    }

    public void setcDesativadaSelecionada(Conta cDesativadaSelecionada) {
        this.cDesativadaSelecionada = cDesativadaSelecionada;
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

    public List<Conta> getMinhasContasAtivadas() {
        if (minhasContasAtivadas == null) {
            this.minhasContasAtivadas = contaDAO.buscarTodasMinhasContas(usuarioController.getUsuarioLogado(),"Ativada");
        }
        return minhasContasAtivadas;
    }

    public void setMinhasContasAtivadas(List<Conta> minhasContasAtivadas) {
        this.minhasContasAtivadas = minhasContasAtivadas;
    }

    public List<Conta> getMinhasContasDesativadas() {

        if (minhasContasDesativadas == null) {
            this.minhasContasDesativadas = contaDAO.buscarTodasMinhasContas(usuarioController.getUsuarioLogado(), "Desativada");
        }
        return minhasContasDesativadas;
    }

    public void setMinhasContasDesativadas(List<Conta> minhasContasDesativadas) {
        this.minhasContasDesativadas = minhasContasDesativadas;
    }

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public String getNrAgencia() {
        return nrAgencia;
    }

    public void setNrAgencia(String nrAgencia) {
        this.nrAgencia = nrAgencia;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void adicionar() {
        Agencia agenciaRetornada = agenciaDAO.buscarPorNrAgencia(nrAgencia);
        Usuario usuarioRetornado = usuarioDAO.buscarPorCPFEstado(cpf, estado, "Cliente");

        if (agenciaRetornada == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "A agencia informada não existe. Tente novamente");
        } else {
            System.out.println("Agência: " + agenciaRetornada.getNome());
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Agência encontrada.");
        }

        if (usuarioRetornado == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Os dados do usuário não constam no banco de dados. Tente novamente");
        } else {
            System.out.println("Usuario: " + usuarioRetornado.getNome());
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Usuário encontrado.");
        }

        //Anula os históricos de movimentações
        conta.setListaDepositos(null);
        conta.setListaSaques(null);
        conta.setListaTransferencias(null);

        conta.setSaldo(0);
        conta.setStatus("Ativada");
        conta.setUsuarioidUsuario(usuarioRetornado);
        conta.setAgenciaidAgencia(agenciaRetornada);

        if (conta.getTipo().equals("Comum")) {
            if (conta.getLimite() != 0) {
                conta.setLimite(0);
                addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Contas Comuns não possuem limite, o valor do limite é R$ 0.");
            }
        }

        contaDAO.insert(conta);
        this.contasAtivadas = null;
        System.out.println("adicionou uma conta na lista.");
        addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Cadastro realizado.");
    }

    public void desativarConta() {
        if (cAtivadaSelecionada != null) {
            cAtivadaSelecionada.setStatus("Desativada");
            contaDAO.edit(cAtivadaSelecionada);
            this.contasAtivadas = null;
            this.contasDesativadas = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta Desativada");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Informação", "Selecione uma conta para desativar");
        }
    }

    public void reativarConta() {
        if (cDesativadaSelecionada != null) {
            cDesativadaSelecionada.setStatus("Ativada");
            contaDAO.edit(cDesativadaSelecionada);
            this.contasAtivadas = null;
            this.contasDesativadas = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta Ativada");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione uma conta para reativar");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
