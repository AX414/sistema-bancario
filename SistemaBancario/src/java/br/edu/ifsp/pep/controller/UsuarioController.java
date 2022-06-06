/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author aluno
 */
@Named
@SessionScoped
public class UsuarioController implements Serializable {

    @Inject
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private Usuario usuarioLogado;

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void autenticar() {
        usuarioLogado = usuarioDAO.buscarPorCPFSenha(usuario.getCpf(), usuario.getSenha(), usuario.getNivelAcesso());

        System.out.println("\nCPF: " + usuario.getCpf()
                + "\nSenha: " + usuario.getSenha()
                + "\nNivel de acesso: " + usuario.getNivelAcesso());
        if (usuarioLogado != null) {
            System.out.println("Autenticado");
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Usuário autenticado.");

            PhaseEvent pe = null;
            HttpServletRequest request
                    = (HttpServletRequest) pe.getFacesContext()
                            .getExternalContext()
                            .getRequest();

            if (request.getServletPath().equals("./Tela_Principal_Cliente.xhtml")) {
                if (usuarioLogado.getNivelAcesso().equals("Cliente")) {
                    try {
                        pe.getFacesContext().getExternalContext().redirect("./Tela_Login.xhtml");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Não autenticado");
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usário não autenticado.");
            this.usuario = new Usuario();
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
