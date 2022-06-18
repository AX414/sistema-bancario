/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.AgenciaDAO;
import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.dao.SaqueDAO;
import br.edu.ifsp.pep.dao.UsuarioDAO;
import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Saque;
import br.edu.ifsp.pep.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Inject
    private SaqueDAO saqueDAO;
    @Inject
    private ContaDAO contaDAO;
    @Inject
    private UsuarioController usuarioController;

    private Saque saque;
    private Saque sSelecionado;

    private String nrConta;
    private String senha;
    private double valor;
    private Date dataSaque;

    private List<Saque> saques;

    public SaqueController() {
        System.out.println("construtor saque.");
        this.sSelecionado = null;
        this.saque = new Saque();
    }

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
        if (saques == null) {
            this.saques = saqueDAO.buscarTodos();
        }
        return saques;
    }

    public void setSaques(List<Saque> saques) {
        this.saques = saques;
    }

    public String getNrConta() {
        return nrConta;
    }

    public void setNrConta(String nrConta) {
        this.nrConta = nrConta;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataSaque() {
        return dataSaque;
    }

    public void setDataSaque(Date dataSaque) {
        this.dataSaque = dataSaque;
    }

    public SaqueController(UsuarioController usuarioController, Saque sSelecionado) {
        this.usuarioController = usuarioController;
        this.sSelecionado = sSelecionado;
    }

    public void efetuarSaque() {
        Conta contaRetornada = contaDAO.buscarContaPorNrSenhaId(nrConta, senha, usuarioController.getUsuarioLogado());
        double saldoRetornado;
        double limiteRetornado;
        int erro = 0;

        if (contaRetornada == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Você não possui nenhuma conta com esses dados. Tente novamente");
        } else {
            if (contaRetornada.getStatus().equals("Desativada")) {
                addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "A conta foi encontrada, porém ela está desativada. "
                        + "            Insira uma conta ativa para efetuar a operação.");
            } else {
                System.out.println("Número da Conta: " + contaRetornada.getNrConta());
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta encontrada.");
            }

            saldoRetornado = contaRetornada.getSaldo();
            limiteRetornado = contaRetornada.getLimite();

            //O valor do saque não pode ser maior que a quantia da conta se a conta for Comum
            //O valor do saque não pode ser maior que o saldo + limite da conta se ela for Especial
            if (contaRetornada.getTipo().equals("Comum")) {
                if (valor > saldoRetornado) {
                    erro = 1;
                    addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "O valor inserido ultrapassa o total presente na conta. Tente novamente");
                } else {
                    contaRetornada.setSaldo(saldoRetornado - valor);
                    contaDAO.edit(contaRetornada);
                    addMessage(FacesMessage.SEVERITY_INFO, "Informação", "O saldo da conta do tipo Comum foi alterado.");
                }
            } else if (contaRetornada.getTipo().equals("Especial")) {
                if (valor >= saldoRetornado + limiteRetornado) {
                    erro = 1;
                    addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "O valor inserido ultrapassa o total presente na conta e o limite. Tente novamente");
                } else {

                    if (valor > saldoRetornado) {
                        //Supondo uma conta de 600 de saldo com 100 de limite, se eu pego 650,
                        //eu zero o saldo, ainda sobra 50 (diferença) e depois eu faço o limite - diferença
                        //logo, 100 - 50 = 50, ainda sobraria 50 de limite
                        double diferenca = valor - saldoRetornado;

                        contaRetornada.setSaldo(saldoRetornado - (valor - diferenca));
                        contaRetornada.setLimite(limiteRetornado - diferenca);
                    } else {
                        contaRetornada.setSaldo(saldoRetornado - valor);
                    }

                    contaDAO.edit(contaRetornada);
                    addMessage(FacesMessage.SEVERITY_INFO, "Informação", "O saldo da conta do tipo Especial foi alterado.");
                }
            }

            //Se não tiver ocorrido algum erro
            if (erro == 0) {
                //formatando a data
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dataSaque = new Date();
                sdf.format(dataSaque);

                //insere os valores do saque e salva ele no banco
                saque.setContaidConta(contaRetornada);
                saque.setDataSaque(dataSaque);
                saque.setValor(valor);
                saqueDAO.insert(saque);
                this.saques = null;
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Saque efetuado com sucesso.");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Não foi possível efetuar o saque, tente novamente.");
            }
        }

    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
