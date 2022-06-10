/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.model.Agencia;
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
public class AgenciaDAO {

    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;

    public void insert(Agencia a) {
        em.persist(a);
    }

    public void delete(Agencia a) {
        if (!em.contains(a)) 
        {
            a = em.merge(a);
        }
        em.remove(a);
    }

    public List<Agencia> buscarTodas() {
        return em.createQuery("Select a FROM Agencia a", Agencia.class).getResultList();
    }

    public Agencia buscarPorNrAgencia(String nrAgencia){
       TypedQuery<Agencia> query = em.createQuery("Select a FROM Agencia a WHERE a.nrAgencia = :nrAgencia",Agencia.class);
        query.setParameter("nrAgencia", nrAgencia);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
