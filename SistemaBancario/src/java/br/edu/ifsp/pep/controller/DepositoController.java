/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.model.Deposito;
import br.edu.ifsp.pep.dao.DepositoDAO;
import br.edu.ifsp.pep.model.Conta;
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
public class DepositoController implements Serializable {

    @Inject
    private DepositoDAO depositoDAO;
    @Inject
    private ContaDAO contaDAO;
    @Inject
    private UsuarioController usuarioController;

    private Deposito deposito;
    private Deposito dSelecionado;

    private String nrConta;
    private String senha;
    private double valor;
    private Date dataDeposito;
    private Date dataAutorizacao;
    private List<Deposito> depositosCaixa;
    private List<Deposito> depositosEnvelope;

    public DepositoController() {
        System.out.println("construtor deposito.");
        this.dSelecionado = null;
        this.deposito = new Deposito();
    }

    public DepositoDAO getDepositoDAO() {
        return depositoDAO;
    }

    public void setDepositoDAO(DepositoDAO depositoDAO) {
        this.depositoDAO = depositoDAO;
    }

    public ContaDAO getContaDAO() {
        return contaDAO;
    }

    public void setContaDAO(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
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

    public List<Deposito> getDepositosCaixa() {
        if (this.depositosCaixa == null) {
            this.depositosCaixa = depositoDAO.buscarTodosPorTipo("Caixa");
        }
        return depositosCaixa;
    }

    public void setDepositosCaixa(List<Deposito> depositosCaixa) {
        this.depositosCaixa = depositosCaixa;
    }

    public List<Deposito> getDepositosEnvelope() {
        if (this.depositosEnvelope == null) {
            this.depositosEnvelope = depositoDAO.buscarTodosPorTipoDataAutorizacao("Envelope");
        }
        return depositosEnvelope;
    }

    public void setDepositosEnvelope(List<Deposito> depositosEnvelope) {
        this.depositosEnvelope = depositosEnvelope;
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

    public void efetuarDeposito() {
        Conta contaRetornada = contaDAO.buscarContaPorNrSenhaId(nrConta, senha, usuarioController.getUsuarioLogado());
        double saldoRetornado;
        int erro = 0;

        if (contaRetornada == null) {
            erro = 1;
            addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Você não possui nenhuma conta com esses dados. Tente novamente");
        } else {
            if (contaRetornada.getStatus().equals("Desativada")) {
                erro = 1;
                addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "A conta foi encontrada, porém ela está desativada. "
                        + "            Insira uma conta ativa para efetuar a operação.");
            } else {
                System.out.println("Número da Conta: " + contaRetornada.getNrConta());
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta encontrada.");
            }

            if (erro == 0) {

                saldoRetornado = contaRetornada.getSaldo();

                //insere os valores do deposito e salva ele no banco
                //formatando a data
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dataDeposito = new Date();
                sdf.format(dataDeposito);

                deposito.setDataDeposito(dataDeposito);
                deposito.setContaidConta(contaRetornada);
                deposito.setValor(valor);
                deposito.setDataAutorizacao(null);

                if (deposito.getTipo().equals("Caixa")) {
                    //alterando o saldo da conta
                    contaRetornada.setSaldo(saldoRetornado + valor);
                    contaDAO.edit(contaRetornada);
                    deposito.setTipo("Caixa");
                } else {
                    //se é do tipo envelope, o saldo da conta 
                    //só vai mudar se o deposito receber a data de autorização
                    deposito.setTipo("Envelope");
                    addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Espere o envelope ser aprovado para o deposito cair na conta.");
                }

                depositoDAO.insert(deposito);
                this.depositosCaixa = null;
                this.depositosEnvelope = null;
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Deposito efetuado com sucesso.");
            }else{
                 addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Não foi possível efetuar o deposito, tente novamente.");
            }
        }

    }

    public void aprovarDeposito() {
        Conta contaDeposito = dSelecionado.getContaidConta();
        double saldoConta = contaDeposito.getSaldo();

        if (dSelecionado != null && dSelecionado.getDataAutorizacao() == null) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataAutorizacao = new Date();
            sdf.format(dataAutorizacao);

            //altera o deposito
            dSelecionado.setDataAutorizacao(dataAutorizacao);
            depositoDAO.edit(dSelecionado);
            //deposita o valor na conta vinculada ao deposito
            contaDeposito.setSaldo(saldoConta + dSelecionado.getValor());
            contaDAO.edit(contaDeposito);

            this.depositosCaixa = null;
            this.depositosEnvelope = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Deposito Aprovado");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Informação", "Selecione um deposito para aprovar");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
