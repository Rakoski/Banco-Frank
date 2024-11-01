package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import entidade.Conta;

public class ContaDAO {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

	public Conta inserir(Conta conta) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(conta);
			em.getTransaction().commit();
			return conta;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public Conta atualizar(Conta conta) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			conta = em.merge(conta);
			em.getTransaction().commit();
			return conta;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public void excluir(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Conta conta = em.find(Conta.class, id);
			if (conta != null) {
				em.remove(conta);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public List<Conta> listarTodos() {
		EntityManager em = emf.createEntityManager();
		try {
			return em.createQuery("from Conta", Conta.class).getResultList();
		} finally {
			em.close();
		}
	}

	public List<Conta> buscarPorCpf(String cpf) {
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("from Conta where cpfCorrentista = :cpf");
			query.setParameter("cpf", cpf);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	public Conta buscarPorId(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(Conta.class, id);
		} finally {
			em.close();
		}
	}
}