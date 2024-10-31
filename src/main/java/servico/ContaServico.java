package servico;

import java.util.Date;

import dao.ContaDAO;
import entidade.Conta;

public class ContaServico {
	ContaDAO dao = new ContaDAO();
	
	
	public Conta inserir(Conta conta) {
		conta.setDescricao("Operação de "+conta.getTipoTransacao());
		conta.setDataTransacao(new Date());
		Conta contaBanco = dao.inserir(conta);
		return contaBanco;
	}
	
}
