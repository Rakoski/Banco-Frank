package dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.*;

import entidade.Cliente;
import entidade.Conta;

public class ContaDAO {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

	public Conta inserir(Conta conta) throws Exception {
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

	public Conta atualizar(Conta conta) throws Exception {
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

	public void excluir(Long id) throws Exception {
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

	public List<Conta> buscarPorCliente(Cliente cliente) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Conta> query = em.createQuery(
					"SELECT c FROM Conta c WHERE c.cliente = :cliente",
					Conta.class
			);
			query.setParameter("cliente", cliente);

			try {
				return query.getResultList();
			} catch (NoResultException e) {
				throw new NoResultException("Conta não encontrada com o cliente: " + cliente);
			}

		} finally {
			em.close();
		}
	}

	public List<Conta> buscarPorDataDeTransacaoAteOntem(Date dataInicial) {
		EntityManager em = emf.createEntityManager();
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);

			Date ontem = calendar.getTime();

			TypedQuery<Conta> query = em.createQuery(
					"SELECT c FROM Conta c WHERE c.dataTransacao BETWEEN :dataInicial AND :dataLimite",
					Conta.class
			);
			query.setParameter("dataInicial", dataInicial);
			query.setParameter("dataLimite", ontem);

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