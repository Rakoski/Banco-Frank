package visao;

import java.math.BigInteger;
import java.util.Date;

import controle.ClienteControle;
import controle.ContaControle;
import entidade.Cliente;
import entidade.Conta;

public class ContaTela {

	public static void main(String[] args) throws Exception {
		ContaControle controleConta = new ContaControle();
		ClienteControle controleCliente = new ClienteControle();

		Conta conta = new Conta();
		conta.setDataTransacao(new Date());
		conta.setDescricao("Depósito de 500,00 no dia 03/10/24");
		conta.setTipoTransacao("depósito");
		conta.setValorOperacao(500.);

		Cliente cliente = new Cliente();
		cliente.setCpfCorrentista("04425225112");
		cliente.setNomeCorrentista("José");
		cliente.setSaldo(BigInteger.valueOf(6000));
		controleCliente.inserir(cliente);

		controleConta.pagamento(conta, cliente.getCpfCorrentista(), BigInteger.valueOf(300), "pix");
	}

}