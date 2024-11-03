package controle;

import dto.ExtratoMensalDTO;
import servico.ExtratoServico;

public class ExtratoControle {

    ExtratoServico extratoServico = new ExtratoServico();

    public void getExtratoMensal(
            String cpf,
            int mes,
            int ano
    ) {
        ExtratoMensalDTO extrato = extratoServico.gerarExtratoMensal(cpf, mes, ano);
        System.out.println(extrato.getNomeCliente());
        System.out.println(extrato.getCpf());
        System.out.println(extrato.getMes());
        System.out.println(extrato.getSaldo());
        for (ExtratoMensalDTO.ContaDTO conta : extrato.getTransacoes()) {
            System.out.println(conta.getData());
            System.out.println(conta.getTipoConta());
            System.out.println(conta.getValor());
            System.out.println(conta.getDescricao());
        }
    }
}
