/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Transferencia;
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
public class TransferenciaDAO {
    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;

    public void insert(Transferencia t) {
        em.persist(t);
    }

    public void delete(Transferencia t) {
        if (!em.contains(t)) 
        {
            t = em.merge(t);
        }
        em.remove(t);
    }

    public List<Transferencia> buscarTodas() {
        return em.createQuery("Select t FROM Transferencia t", Transferencia.class).getResultList();
    }

    public List<Transferencia> buscarTodasPorConta(Conta idConta) {
        TypedQuery<Transferencia> query = em.createQuery("Select t FROM Transferencia t "
        + "WHERE t.contaidConta = :idConta", Transferencia.class);
        query.setParameter("idConta", idConta);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            System.out.println(ex);
            return null;
        }
    }

}
