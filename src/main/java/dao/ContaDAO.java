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

	public List<Conta> buscarOperacoesPorClienteEData(Cliente cliente, Date data) {
		EntityManager em = emf.createEntityManager();
		try {
			Calendar startDay = Calendar.getInstance();
			startDay.setTime(data);
			startDay.set(Calendar.HOUR_OF_DAY, 0);
			startDay.set(Calendar.MINUTE, 0);
			startDay.set(Calendar.SECOND, 0);
			startDay.set(Calendar.MILLISECOND, 0);

			Calendar endDay = Calendar.getInstance();
			endDay.setTime(data);
			endDay.set(Calendar.HOUR_OF_DAY, 23);
			endDay.set(Calendar.MINUTE, 59);
			endDay.set(Calendar.SECOND, 59);
			endDay.set(Calendar.MILLISECOND, 999);

			TypedQuery<Conta> query = em.createQuery(
					"SELECT c FROM Conta c WHERE c.cliente = :cliente " +
							"AND c.dataTransacao BETWEEN :startDay AND :endDay",
					Conta.class
			);
			query.setParameter("cliente", cliente);
			query.setParameter("startDay", startDay.getTime());
			query.setParameter("endDay", endDay.getTime());

			return query.getResultList();
		} finally {
			em.close();
		}
	}

	public List<Conta> buscarTransacoesPorPeriodo(Cliente cliente, Date dataInicio, Date dataFim) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Conta> query = em.createQuery(
					"SELECT c FROM Conta c " +
							"WHERE c.cliente.id = :clienteId " +
							"AND c.dataTransacao BETWEEN :dataInicio AND :dataFim " +
							"ORDER BY c.dataTransacao",
					Conta.class
			);

			query.setParameter("clienteId", cliente.getId());
			query.setParameter("dataInicio", dataInicio);
			query.setParameter("dataFim", dataFim);

			List<Conta> resultado = query.getResultList();
			System.out.println("Número de transações encontradas: " + resultado.size());

			return resultado;
		} finally {
			em.close();
		}
	}
}