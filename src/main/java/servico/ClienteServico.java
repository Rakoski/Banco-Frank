package servico;

import dao.ClienteDAO;
import entidade.Cliente;
import util.ValidationUtils;

public class ClienteServico {

    ClienteDAO clienteDAO = new ClienteDAO();
    ValidationUtils validationUtils = new ValidationUtils();

    public void inserir(Cliente cliente) throws Exception {
        validationUtils.validateCpf(cliente.getCpfCorrentista());
        clienteDAO.inserir(cliente);
    }
}
