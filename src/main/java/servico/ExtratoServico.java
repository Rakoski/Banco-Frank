package servico;

import dao.ClienteDAO;
import dao.ContaDAO;
import dto.ExtratoMensalDTO;
import entidade.Cliente;
import entidade.Conta;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExtratoServico {

    ClienteDAO clienteDAO = new ClienteDAO();
    ContaDAO contaDAO = new ContaDAO();

    public ExtratoMensalDTO gerarExtratoMensal(String cpf, int mes, int ano) {
        List<Cliente> clientes = clienteDAO.buscarPorCpf(cpf);
        Cliente cliente = clientes.get(0);

        assert Objects.equals(clientes.size(), 1) : "Cliente inválido!";
        assert Objects.nonNull(cliente) : "Cliente não encontrado!";

        Calendar calInicio = Calendar.getInstance();
        calInicio.set(ano, mes - 1, 1, 0, 0, 0);
        calInicio.set(Calendar.MILLISECOND, 0);

        Calendar calFim = Calendar.getInstance();
        calFim.set(ano, mes - 1, calFim.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calFim.set(Calendar.MILLISECOND, 999);

        List<Conta> transacoesMes = contaDAO.buscarTransacoesPorPeriodo(
                cliente,
                calInicio.getTime(),
                calFim.getTime()
        );

        ExtratoMensalDTO extrato = new ExtratoMensalDTO();
        extrato.setNomeCliente(cliente.getNomeCorrentista());
        extrato.setCpf(cliente.getCpfCorrentista());
        extrato.setMes(String.format("%02d/%d", mes, ano));

        List<ExtratoMensalDTO.ContaDTO> contaDTOS = transacoesMes.stream()
                .map(t -> new ExtratoMensalDTO.ContaDTO(
                        t.getDataTransacao(),
                        t.getTipoTransacao(),
                        t.getValorOperacao(),
                        t.getDescricao()
                ))
                .collect(Collectors.toList());

        extrato.setTransacoes(contaDTOS);

        calculateSaldos(extrato, cliente);

        return extrato;
    }

    private void calculateSaldos(ExtratoMensalDTO extrato, Cliente cliente) {
        extrato.setSaldo(cliente.getSaldo().doubleValue());
    }
}
