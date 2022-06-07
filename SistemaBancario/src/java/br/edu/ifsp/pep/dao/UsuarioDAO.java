/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.dao;
import br.edu.ifsp.pep.model.Usuario;
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
public class UsuarioDAO extends AbstratoDAO<Usuario>{
    @PersistenceContext(unitName = "SistemaBancarioPU")
    private EntityManager em;

    public Usuario buscarPorCPFSenha(String cpf, String senha, String nivelAcesso) {
        TypedQuery<Usuario> query
                = em.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf "
                        + "AND u.senha = :senha AND u.nivelAcesso = :nivelAcesso", Usuario.class);
        query.setParameter("cpf", cpf);
        query.setParameter("senha", senha);
        query.setParameter("nivelAcesso", nivelAcesso);
        
        try{
            return query.getSingleResult();
        } catch(NoResultException ex){
            return null;
        }
        
    }
}
