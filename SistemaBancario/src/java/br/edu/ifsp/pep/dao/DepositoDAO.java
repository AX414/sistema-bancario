/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Deposito;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joaov
 */
@Stateless
public class DepositoDAO {
    
    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;

    public void insert(Deposito d) {
        em.persist(d);
    }

    public void delete(Deposito d) {
        if (!em.contains(d)) {
            d = em.merge(d);
        }
        em.remove(d);
    }

    public void edit(Deposito d) {
        em.merge(d);
    }

    public List<Deposito> buscarTodos() {
        return em.createQuery("Select d FROM Deposito d",Deposito.class).getResultList();
    }
}
