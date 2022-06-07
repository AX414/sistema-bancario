/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joaov
 */
public abstract class AbstratoDAO<T> {

    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void create(T entity) {
        em.persist(entity);
    }
    
    public void remove(T entity) {
        em.remove(em.merge(entity));
    }
    
    public void edit(T entity) {
        em.merge(entity);
    }
    
}
