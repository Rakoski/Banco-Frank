package servico;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dao.ClienteDAO;
import dao.ContaDAO;
import entidade.Cliente;
import entidade.Conta;
import util.ValidationUtils;

public class ContaServico {
	ContaDAO contaDao = new ContaDAO();
	ClienteDAO clienteDAO = new ClienteDAO();
	ValidationUtils validationUtils = new ValidationUtils();

	public Conta pagamento(Conta conta, String cpf, BigInteger valor, String tipoOperacao) throws Exception {
		validationUtils.validateCpf(cpf);
		List<Cliente> clientes = clienteDAO.buscarPorCpf(cpf);

		assert clientes.size() == 0 : "Cliente inválido!";
		Cliente cliente = clientes.get(0);
		List<Conta> contas = contaDao.buscarPorCliente(cliente);
		BigInteger saldo = cliente.getSaldo();

		assert Objects.nonNull(saldo) : "Saldo não pode ser nulo";
		assert saldo.compareTo(BigInteger.ZERO) >= 0 : "Saldo menor que zero!";

		int saldoInt = Integer.parseInt(String.valueOf(saldo));
		int valorRetiradoInt = Integer.parseInt(String.valueOf(valor));
		int taxaRetirada = validationUtils.validaTipoPagamento(conta, tipoOperacao, valor);

		assert saldoInt >= (valorRetiradoInt + taxaRetirada) : "Valor a ser retirado maior que o saldo!";

		if (contas.size() == 0) {
			conta.setDataTransacao(new Date());

			conta.setCliente(cliente);
			contaDao.inserir(conta);

			BigInteger novoSaldo = BigInteger.valueOf(saldoInt - (valorRetiradoInt + taxaRetirada));
			cliente.setSaldo(novoSaldo);
			clienteDAO.atualizar(cliente);
			System.out.println("Pagamento realizado com sucesso!");

			if (novoSaldo.doubleValue() < 100.00) {
				System.out.println("Saldo abaixo de 100!");
			}

			return conta;
		}

		List<Conta> contasExistentes = contaDao.buscarPorCliente(cliente);
		Conta contaExistente = contasExistentes.get(contasExistentes.size() - 1);

		contaExistente.setDataTransacao(new Date());
		conta.setCliente(cliente);
		contaDao.inserir(conta);
		BigInteger novoSaldo = BigInteger.valueOf(saldoInt - (valorRetiradoInt + taxaRetirada));
		cliente.setSaldo(novoSaldo);
		clienteDAO.atualizar(cliente);

		System.out.println("Pagamento realizado com sucesso!");

		if (novoSaldo.doubleValue() < 100.00) {
			System.out.println("Saldo abaixo de 100!");
		}

		return conta;
	}
}