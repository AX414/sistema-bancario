/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Transferencia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
