/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.UsuarioDAO;
import br.edu.ifsp.pep.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
    private Usuario uSelecionado;
    private List<Usuario> usuarios;

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

    public Usuario getuSelecionado() {
        return uSelecionado;
    }

    public void setuSelecionado(Usuario uSelecionado) {
        this.uSelecionado = uSelecionado;
    }

    public List<Usuario> getUsuarios() {
        if (this.usuarios == null) {
            System.out.println("Carregando...");
            this.usuarios = usuarioDAO.buscarTodos();
        }
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void redirecionarPagina(String pagina) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./" + pagina);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void retornarHome() {
        if (usuarioLogado.getNivelAcesso().equals("Cliente")) {
            redirecionarPagina("Tela_Principal_Cliente.xhtml");
        } else if (usuarioLogado.getNivelAcesso().equals("Funcionario")) {
            redirecionarPagina("Tela_Principal_Funcionario.xhtml");
        } else if (usuarioLogado.getNivelAcesso().equals("Administrador")) {
            redirecionarPagina("Tela_Principal_Administrador.xhtml");
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
            retornarHome();
        } else {
            System.out.println("Não autenticado");
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário não autenticado.");
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

    public void adicionar() {
        System.out.println("adicionou um usuario na lista.");
        usuarioDAO.insert(usuario);
        this.usuarios = null;
        addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Cadastro realizado.");
    }

    public void excluir() {
        if (uSelecionado != null) {
            usuarioDAO.delete(uSelecionado);
            this.usuarios = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Exclusão realizada");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Info Message", "Selecione um usuario para excluir");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
