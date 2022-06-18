/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.TransferenciaDAO;
import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.dao.AgenciaDAO;
import br.edu.ifsp.pep.model.Transferencia;
import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Agencia;
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
public class TransferenciaController implements Serializable {

    @Inject
    private TransferenciaDAO transferenciaDAO;
    @Inject
    private ContaDAO contaDAO;
    @Inject
    private AgenciaDAO agenciaDAO;
    @Inject
    private UsuarioController usuarioController;

    private Transferencia transferencia;
    private Transferencia tSelecionada;

    private String nrContaRemetente;
    private String senhaContaRemetente;
    private double valor;

    private String nrContaDestino;
    private String nrAgenciaDestino;
    private String nomeUsuarioContaDestino = "????";

    private List<Transferencia> transferencias;

    public TransferenciaController() {
        System.out.println("construtor transferencia.");
        this.tSelecionada = null;
        this.transferencia = new Transferencia();
        this.nomeUsuarioContaDestino = null;
    }

    public TransferenciaDAO getTransferenciaDAO() {
        return transferenciaDAO;
    }

    public void setTransferenciaDAO(TransferenciaDAO transferenciaDAO) {
        this.transferenciaDAO = transferenciaDAO;
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

    public String getNrContaRemetente() {
        return nrContaRemetente;
    }

    public void setNrContaRemetente(String nrContaRemetente) {
        this.nrContaRemetente = nrContaRemetente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getSenhaContaRemetente() {
        return senhaContaRemetente;
    }

    public void setSenhaContaRemetente(String senhaContaRemetente) {
        this.senhaContaRemetente = senhaContaRemetente;
    }

    public String getNrContaDestino() {
        return nrContaDestino;
    }

    public void setNrContaDestino(String nrContaDestino) {
        this.nrContaDestino = nrContaDestino;
    }

    public String getNrAgenciaDestino() {
        return nrAgenciaDestino;
    }

    public void setNrAgenciaDestino(String nrAgenciaDestino) {
        this.nrAgenciaDestino = nrAgenciaDestino;
    }

    public String getNomeUsuarioContaDestino() {
        return nomeUsuarioContaDestino;
    }

    public void setNomeUsuarioContaDestino(String nomeUsuarioContaDestino) {
        this.nomeUsuarioContaDestino = nomeUsuarioContaDestino;
    }

    public List<Transferencia> getTransferencias() {
        if (transferencias == null) {
            this.transferencias = transferenciaDAO.buscarTodas();
        }
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public void retornaNomeUsuarioContaDestino() {
        Agencia agenciaDestino = agenciaDAO.buscarPorNrAgencia(nrAgenciaDestino);
        Conta contaDestino = contaDAO.buscarPorNrContaIdAgencia(nrContaDestino, agenciaDestino);
        this.setNomeUsuarioContaDestino(contaDestino.getUsuarioidUsuario().getNome());
    }

    public void efetuarTransferencia() {
        Agencia agenciaDestino = agenciaDAO.buscarPorNrAgencia(nrAgenciaDestino);
        Conta contaRemetente = contaDAO.buscarPorNrSenhaId(nrContaRemetente, senhaContaRemetente, usuarioController.getUsuarioLogado());
        Conta contaDestino = contaDAO.buscarPorNrContaIdAgencia(nrContaDestino, agenciaDestino);
        Date dataTransferencia;
        double saldoRetornado, limiteRetornado, saldoDestino;
        int erro = 0;

        //Procura pela conta do Remetente
        if (contaRemetente == null) {
            erro = 1;
            addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Você não possui nenhuma conta com esses dados. Tente novamente");
        } else {
            //Se encontrou, ele vai seguir e verificar se está ativada
            if (contaRemetente.getStatus().equals("Desativada")) {
                erro = 1;
                addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "A conta do remetente foi encontrada, porém ela está desativada. "
                        + "            Insira uma conta ativa para efetuar a operação.");
            } else {
                System.out.println("Número da Conta do Remetente: " + contaRemetente.getNrConta());
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta do Remetente encontrada.");
            }

            //Se a conta do remetente foi ativa, segue pra procurar a conta de destino
            if (contaDestino == null) {
                erro = 1;
                addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "A conta de destino não foi encontrada. Tente novamente");
            } else {
                //Verifica se a conta de Destino é ativa
                if (contaDestino.getStatus().equals("Desativada")) {
                    erro = 1;
                    addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "A conta de destino foi encontrada, porém ela está desativada. "
                            + "            Insira uma conta ativa para efetuar a operação.");
                } else {
                    System.out.println("Número da Conta de Destino: " + contaDestino.getNrConta());
                    addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Conta de Destino encontrada.");
                }

            }

            if (contaRemetente.equals(contaDestino)) {
                erro = 1;
                addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "A conta do remetente e a conta de destino são a mesma conta. "
                        + "            Insira outra conta para efetuar a transferência");
            }

            //As duas contas são ativas e foram encontradas
            /*
            Para transferir, primeiro, pegar a conta do remetente, subtrair o valor, editar ela no banco
            O valor da transferência não pode ser maior que a quantia da conta se a conta for Comum
            O valor da transferência não pode ser maior que o saldo + limite da conta se ela for Especial
             */
            saldoRetornado = contaRemetente.getSaldo();
            limiteRetornado = contaRemetente.getLimite();

            if (contaRemetente.getTipo().equals("Comum")&&erro==0) {
                if (valor > saldoRetornado) {
                    erro = 1;
                    addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "O valor inserido ultrapassa o total presente na conta do remetente. Tente novamente");
                } else {
                    contaRemetente.setSaldo(saldoRetornado - valor);
                    contaDAO.edit(contaRemetente);
                    addMessage(FacesMessage.SEVERITY_INFO, "Informação", "O saldo da conta do tipo Comum foi alterado.");
                }
            } else if (contaRemetente.getTipo().equals("Especial")&&erro==0) {
                if (valor >= saldoRetornado + limiteRetornado) {
                    erro = 1;
                    addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "O valor inserido ultrapassa o saldo total presente na conta e o limite. Tente novamente");
                } else {

                    if (valor > saldoRetornado) {
                        double diferenca = valor - saldoRetornado;

                        contaRemetente.setSaldo(saldoRetornado - (valor - diferenca));
                        contaRemetente.setLimite(limiteRetornado - diferenca);
                    } else {
                        contaRemetente.setSaldo(saldoRetornado - valor);
                    }

                    contaDAO.edit(contaRemetente);
                    addMessage(FacesMessage.SEVERITY_INFO, "Informação", "O saldo da conta do tipo Especial foi alterado.");
                }
            }

            //se não teve erro, efetua a transferencia
            if (erro == 0) {
                //depois, acrescentar esse valor na conta de destino, editar ela no banco também
                saldoDestino = contaDestino.getSaldo();
                contaDestino.setSaldo(saldoDestino + valor);
                contaDAO.edit(contaDestino);

                //por último, acrescentar a transferência na lista de transferencias
                //formatando a data
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dataTransferencia = new Date();
                sdf.format(dataTransferencia);
                transferencia.setData(dataTransferencia);
                transferencia.setIdAgencia(agenciaDestino.getIdAgencia());
                transferencia.setContaidConta(contaRemetente);
                transferencia.setIdConta(nrContaDestino);
                transferencia.setValor(valor);
                
                transferenciaDAO.insert(transferencia);
                this.transferencias = null;
                addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Trasnferência efetuada com sucesso.");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Não foi possível efetuar a transferência, tente novamente.");
            }
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
