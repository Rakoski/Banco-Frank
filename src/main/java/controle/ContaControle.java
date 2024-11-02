package controle;

import entidade.Conta;
import servico.ContaServico;

import java.math.BigInteger;

public class ContaControle {
	
	ContaServico servico = new ContaServico();

	public Conta pagamento(Conta conta, String cpf, BigInteger valor, String tipoOperacao) throws Exception {
		return servico.pagamento(conta, cpf, valor, tipoOperacao);
	}

}
