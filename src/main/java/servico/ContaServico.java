package servico;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.ContaDAO;
import entidade.Conta;
import util.ValidationUtils;

public class ContaServico {
	ContaDAO dao = new ContaDAO();
	ValidationUtils validationUtils = new ValidationUtils();

	public Conta inserir(Conta conta) throws Exception {
		conta.setDescricao("Operação de "+conta.getTipoTransacao());
		conta.setDataTransacao(new Date());
		return dao.inserir(conta);
	}

	public Conta pagamento(String cpf, BigInteger valor, String tipoOperacao) throws Exception {
		if (!validationUtils.validateCpf(cpf)) {
			throw new RuntimeException("CPF inválido!");
		}

		List<Conta> contas = dao.buscarPorCpf(cpf);
		Conta conta = contas.get(0);

		if (!validationUtils.validaTipoPagamento(conta, tipoOperacao, valor)) {
			throw new RuntimeException("Tipo de pagamento inválido!");
		}



		BigInteger saldo = conta.getSaldo();
		if (Objects.isNull(saldo) || saldo.doubleValue() < 0) {
			throw new RuntimeException("Saldo menor que zero!");
		}

		Integer saldoInt = Integer.valueOf(String.valueOf(saldo));
		Integer valorRetiradoInt = Integer.valueOf(String.valueOf(valor));

		if (saldoInt < valorRetiradoInt) {
			throw new RuntimeException("Valor a ser retirado maior que o saldo!");
		}

		BigInteger novoSaldo = BigInteger.valueOf(saldoInt - valorRetiradoInt);
		conta.setSaldo(novoSaldo);
		conta.setDataTransacao(new Date());
		conta = dao.inserir(conta);

		System.out.println("Pagamento realizado com sucesso!");
		return conta;
	}

}