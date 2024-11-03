package dto;

import java.util.Date;
import java.util.List;

public class ExtratoMensalDTO {
    private String nomeCliente;
    private String cpf;
    private String mes;
    private Double saldo;
    private List<ContaDTO> transacoes;

    public static class ContaDTO {
        private Date data;
        private String tipoConta;
        private Double valor;
        private String descricao;

        public ContaDTO(Date data, String tipoConta, Double valor, String descricao) {
            this.data = data;
            this.tipoConta = tipoConta;
            this.valor = valor;
            this.descricao = descricao;
        }

        public Date getData() { return data; }
        public void setData(Date data) { this.data = data; }
        public String getTipoConta() { return tipoConta; }
        public void setTipoConta(String tipoConta) { this.tipoConta = tipoConta; }
        public Double getValor() { return valor; }
        public void setValor(Double valor) { this.valor = valor; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getMes() { return mes; }
    public void setMes(String mes) { this.mes = mes; }
    public List<ContaDTO> getTransacoes() { return transacoes; }
    public void setTransacoes(List<ContaDTO> transacoes) { this.transacoes = transacoes; }
    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }
}
