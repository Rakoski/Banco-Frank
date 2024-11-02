package controle;

import entidade.Cliente;
import servico.ClienteServico;

public class ClienteControle {

    ClienteServico servico = new ClienteServico();

    public void inserir(Cliente cliente) throws Exception {
        servico.inserir(cliente);
    }
}
