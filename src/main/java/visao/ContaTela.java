package visao;

import java.util.Date;

import controle.ContaControle;
import entidade.Conta;

public class ContaTela {

	public static void main(String[] args) {
		ContaControle controle = new ContaControle();
		
		Conta conta = new Conta();
		conta.setCpfCorrentista("04425225112");
		conta.setDataTransacao(new Date());
		conta.setDescricao("Depósito de 500,00 no dia 03/10/24");
		conta.setNomeCorrentista("José");
		conta.setTipoTransacao("depósito");
		conta.setValorOperacao(500.);
		
		controle.inserir(conta);
	}

}
