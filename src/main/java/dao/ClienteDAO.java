package dao;

import entidade.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ClienteDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Cliente inserir(Cliente Cliente) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(Cliente);
            em.getTransaction().commit();
            return Cliente;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Cliente atualizar(Cliente Cliente) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Cliente = em.merge(Cliente);
            em.getTransaction().commit();
            return Cliente;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void excluir(Long id) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Cliente Cliente = em.find(Cliente.class, id);
            if (Cliente != null) {
                em.remove(Cliente);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Cliente> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("from Cliente", Cliente.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cliente> buscarPorCpf(String cpf) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("from Cliente where cpfCorrentista = :cpf");
            query.setParameter("cpf", cpf);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }
}