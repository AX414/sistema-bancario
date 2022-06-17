/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Saque;
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
public class SaqueDAO {

    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;

    public void insert(Saque s) {
        em.persist(s);
    }

    public void delete(Saque s) {
        if (!em.contains(s)) {
            s = em.merge(s);
        }
        em.remove(s);
    }

    public void edit(Saque s) {
        em.merge(s);
    }

    public List<Saque> buscarTodos() {
        return em.createQuery("Select s FROM Saque s", Saque.class).getResultList();
    }

    public List<Saque> buscarTodosSaquesPorConta(Conta idConta) {
        TypedQuery<Saque> query = em.createQuery("Select s FROM Saque s WHERE s.contaidConta = :idConta", Saque.class);
        query.setParameter("idConta", idConta);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            System.out.println(ex);
            return null;
        }
    }
}
