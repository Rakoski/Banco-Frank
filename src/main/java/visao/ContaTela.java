package visao;

import java.math.BigInteger;
import java.util.Date;

import controle.ClienteControle;
import controle.ContaControle;
import controle.ExtratoControle;
import entidade.Cliente;
import entidade.Conta;
import entidade.ContaTipo;

public class ContaTela {

	public static void main(String[] args) throws Exception {
		ContaControle controleConta = new ContaControle();
		ClienteControle controleCliente = new ClienteControle();
		ExtratoControle controleExtrato = new ExtratoControle();

		Conta conta = new Conta();
		conta.setDataTransacao(new Date());
		conta.setDescricao("pix de 200,00 no dia 03/10/24");
		conta.setTipoTransacao("pix");
		conta.setValorOperacao(200.);
		conta.setTipoConta(String.valueOf(ContaTipo.CONTA_CORRENTE));

		Cliente cliente = new Cliente();
		cliente.setCpfCorrentista("04425235112");
		cliente.setNomeCorrentista("José");
		cliente.setSaldo(BigInteger.valueOf(6000));
//		controleCliente.inserir(cliente);

		controleConta.pagamento(conta, cliente.getCpfCorrentista(), BigInteger.valueOf(200), conta.getTipoTransacao());
//		controleExtrato.getExtratoMensal(cliente.getCpfCorrentista(), 11, 2024);
	}

}