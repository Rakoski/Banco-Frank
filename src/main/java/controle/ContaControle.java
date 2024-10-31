package controle;

import entidade.Conta;
import servico.ContaServico;

public class ContaControle {
	
	ContaServico servico = new ContaServico();
		
	public Conta inserir(Conta conta) {
		return servico.inserir(conta);
	}

}
