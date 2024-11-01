package controle;

import entidade.Conta;
import servico.ContaServico;

import java.math.BigInteger;

public class ContaControle {
	
	ContaServico servico = new ContaServico();
		
	public Conta inserir(Conta conta) {
		return servico.inserir(conta);
	}

	public Conta pagamento(String cpf, BigInteger valor, String tipoOperacao) {
		return servico.pagamento(cpf, valor, tipoOperacao);
	}


}
