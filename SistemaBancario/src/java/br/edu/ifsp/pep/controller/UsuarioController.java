/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.UsuarioDAO;
import br.edu.ifsp.pep.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author aluno
 */
@Named
@SessionScoped
public class UsuarioController implements Serializable {

    @Inject
    private UsuarioDAO usuarioDAO;
    private Usuario usuario = new Usuario();
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

    public void redirecionarPagina(String pagina) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./"+pagina);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void autenticar() {
        usuarioLogado = usuarioDAO.buscarPorCPFSenha(usuario.getCpf(), usuario.getSenha(), usuario.getNivelAcesso());

        System.out.println("\nCPF: " + usuario.getCpf()
                + "\nSenha: " + usuario.getSenha()
                + "\nNivel de acesso: " + usuario.getNivelAcesso());
        if (usuarioLogado != null) {
            System.out.println("Autenticado");
            addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Usuário autenticado.");

            if (usuarioLogado.getNivelAcesso().equals("Cliente")) {
                redirecionarPagina("Tela_Principal_Cliente.xhtml");
            } else if (usuarioLogado.getNivelAcesso().equals("Funcionario")) {
                redirecionarPagina("Tela_Principal_Funcionario.xhtml");
            } else if(usuarioLogado.getNivelAcesso().equals("Administrador")){
                redirecionarPagina("Tela_Principal_Administrador.xhtml");
            }
        } else {
            System.out.println("Não autenticado");
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usário não autenticado.");
            this.usuario = new Usuario();
        }
    }

    public void deslogar() {
        if (usuarioLogado != null) {
            usuarioLogado = null;
            redirecionarPagina("Tela_Login.xhtml");
        } else {
            //quando dá o bug de sair da sessão e ficar dentro da tela principal ainda
            redirecionarPagina("Tela_Login.xhtml");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
