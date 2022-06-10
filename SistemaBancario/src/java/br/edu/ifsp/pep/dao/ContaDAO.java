/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Conta;
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

    public List<Conta> buscarTodasMinhasContas(Integer idUsuario) {
        TypedQuery<Conta> query = em.createQuery("Select c FROM Conta c "
        + "WHERE c.Usuario_idUsuario = :idUsuario",Conta.class);
        query.setParameter("idUsuario", idUsuario);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
}

}
