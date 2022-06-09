/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joaov
 */
@Stateless
public class ContaDAO{

    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;

    public void insert(Conta c) {
        em.persist(c);
    }

    public void delete(Conta c) {
        if (!em.contains(c)) {
            c = em.merge(c);
        }
        em.remove(c);
    }

    public void edit(Conta c) {
        em.merge(c);
    }

    public List<Conta> buscarTodas() {
        return em.createQuery("Select c FROM Conta c",Conta.class).getResultList();
    }


}
