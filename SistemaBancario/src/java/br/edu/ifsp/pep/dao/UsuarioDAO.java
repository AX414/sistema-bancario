/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author joaov
 */
@Stateless
public class UsuarioDAO {

    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;

    public void insert(Usuario u) {
        em.persist(u);
    }

    public void delete(Usuario u) {
        if (!em.contains(u)) {
            u = em.merge(u);
        }
        em.remove(u);
    }

    public List<Usuario> buscarTodos() {
        return em.createQuery("Select u FROM Usuario u", Usuario.class).getResultList();
    }

    public Usuario buscarPorCPFSenha(String cpf, String senha, String nivelAcesso) {
        TypedQuery<Usuario> query
                = em.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf "
                        + "AND u.senha = :senha AND u.nivelAcesso = :nivelAcesso", Usuario.class);
        query.setParameter("cpf", cpf);
        query.setParameter("senha", senha);
        query.setParameter("nivelAcesso", nivelAcesso);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex);
            return null;
        }

    }

    public Usuario buscarPorCPFEstado(String cpf, String estado, String nivelAcesso) {
        TypedQuery<Usuario> query
                = em.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf "
                        + "AND u.estado = :estado AND u.nivelAcesso = :nivelAcesso", Usuario.class);
        query.setParameter("cpf", cpf);
        query.setParameter("estado", estado);
        query.setParameter("nivelAcesso", nivelAcesso);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex);
            return null;
        }

    }
}
