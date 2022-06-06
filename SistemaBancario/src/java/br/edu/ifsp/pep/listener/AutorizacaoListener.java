/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.listener;

import br.edu.ifsp.pep.controller.UsuarioController;
import java.io.IOException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author aluno
 */
public class AutorizacaoListener implements PhaseListener {

    @Inject
    private UsuarioController usuarioController;

    @Override
    public void afterPhase(PhaseEvent pe) {
        System.out.println("After: " + pe.getPhaseId());

        HttpServletRequest request
                = (HttpServletRequest) pe.getFacesContext()
                        .getExternalContext()
                        .getRequest();

        if (request.getServletPath().equals("./Tela_Principal_Cliente.xhtml")) {
            if (usuarioController.getUsuarioLogado().getNivelAcesso().equals("Cliente")) {
                try {
                    pe.getFacesContext().getExternalContext().redirect("./Tela_Login.xhtml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (request.getServletPath().equals("./funcionario.xhtml")) {
            if (usuarioController.getUsuarioLogado().getNivelAcesso().equals("Funcionario")) {
                try {
                    pe.getFacesContext().getExternalContext().redirect("../index.xhtml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (request.getServletPath().equals("./admin.xhtml")) {
            if (usuarioController.getUsuarioLogado().getNivelAcesso().equals("Administrador")) {
                try {
                    pe.getFacesContext().getExternalContext().redirect("../index.xhtml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public void beforePhase(PhaseEvent pe) {
        System.out.println("Before: " + pe.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    /*
        System.out.println(".......................................");
        System.out.println("Address: " + request.getRemoteAddr());
        System.out.println("Host: " + request.getRemoteHost());
        System.out.println("User: " + request.getRemoteUser());
        System.out.println("Port: " + request.getRemotePort());
        System.out.println("Local Addr: " + request.getLocalAddr());
        System.out.println("Path: " + request.getServletPath());
        System.out.println("Server Name: " + request.getServerName());
        System.out.println("Path Info: " + request.getPathInfo());
        System.out.println(".......................................");
     */
}
