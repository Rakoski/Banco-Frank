package util;

import dao.ContaDAO;
import entidade.Conta;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ValidationUtils {
    ContaDAO contaDao = new ContaDAO();
    private static final int HORA_FIM_PIX = 22;
    private static final int HORA_INICIO_PIX = 6;
    private static final double VALOR_MAXIMO_PIX = 300.0;
    private static final int TAXA_PIX_PAGAMENTO = 5;
    private static final int TAXA_SAQUE = 2;
    private static final int LIMITE_OPERACOES_DIARIAS = 10;
    private static final double LIMITE_DESVIO_PADRAO = 15000.0;

    public void validateCpf(String cpf) {
        int quantCaracCpf = "99999999999".length();
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != quantCaracCpf) {
            throw new RuntimeException("CPF menor ou maior que o necessário!");
        }
        for (int i = 0; i < quantCaracCpf; i++) {
            if (Character.isLetter(cpf.charAt(i))) {
                throw new RuntimeException("Letra em CPF!");
            }
        }
        if (cpfLimpo.matches("(\\d)\\1{10}"))
            throw new RuntimeException("CPF inválido!");
    }

    public int validaTipoPagamento(Conta conta, String tipoOperacao, BigInteger valor) {
        validaLimiteOperacoesDiarias(conta);

        int valorDescontado = 0;
        if (Objects.equals(tipoOperacao, "pix") || Objects.equals(tipoOperacao, "pagamento")) {
            if (Objects.equals(tipoOperacao, "pix")) {
                if (Integer.parseInt(String.valueOf(valor)) > 300)
                    throw new RuntimeException("Valor de pix muito alto!");
                if (Objects.equals(tipoOperacao, "pix"))
                    validaHorarioPix();
            }
            valorDescontado = TAXA_PIX_PAGAMENTO;
        }
        if (Objects.equals(tipoOperacao, "saque")) {
            List<Conta> contaDataUltimaTransacao = contaDao.buscarPorDataDeTransacaoAteOntem(conta.getDataTransacao());
            if (contaDataUltimaTransacao.size() == 0) {
                valorDescontado = TAXA_SAQUE;
                return valorDescontado;
            }
            Date dataUltimaTransacao = contaDataUltimaTransacao.get(contaDataUltimaTransacao.size() - 1).getDataTransacao();
            verificaLimiteSaqueDiario(dataUltimaTransacao, new BigDecimal(String.valueOf(valor)));
            valorDescontado = TAXA_SAQUE;
        }
        return valorDescontado;
    }

    private void validaLimiteOperacoesDiarias(Conta conta) {
        Calendar calHoje = Calendar.getInstance();
        calHoje.set(Calendar.HOUR_OF_DAY, 0);
        calHoje.set(Calendar.MINUTE, 0);
        calHoje.set(Calendar.SECOND, 0);
        calHoje.set(Calendar.MILLISECOND, 0);

        List<Conta> operacoesDoDia = contaDao.buscarOperacoesPorClienteEData(conta.getCliente(), calHoje.getTime());

        if (operacoesDoDia.size() >= LIMITE_OPERACOES_DIARIAS) {
            throw new RuntimeException(
                    String.format("Cliente atingiu o limite de %d operações diárias. Tente novamente amanhã.",
                            LIMITE_OPERACOES_DIARIAS)
            );
        }

        if (!operacoesDoDia.isEmpty()) {
            double mediaOperacoes = operacoesDoDia.stream()
                    .mapToDouble(Conta::getValorOperacao)
                    .average()
                    .orElse(0.0);

            double valorAtual = conta.getValorOperacao();

            if (valorAtual > (mediaOperacoes + LIMITE_DESVIO_PADRAO)) {
                throw new RuntimeException(
                        String.format("Operação suspeita detectada! Valor R$ %.2f está muito acima da média diária de R$ %.2f",
                                valorAtual, mediaOperacoes)
                );
            }
        }
    }

    private static void validaHorarioPix() {
        LocalTime horaAtual = LocalTime.now();
        int hora = horaAtual.getHour();
        if (hora < HORA_INICIO_PIX || hora >= HORA_FIM_PIX) {
            throw new RuntimeException(
                    String.format("Operações Pix só podem ser realizadas entre %02d:00 e %02d:00",
                            HORA_INICIO_PIX, HORA_FIM_PIX)
            );
        }
    }

    public void verificaLimiteSaqueDiario(Date dataUltimaTransacao, BigDecimal valorSaque) {
        Calendar calUltima = Calendar.getInstance();
        calUltima.setTime(dataUltimaTransacao);
        Calendar calHoje = Calendar.getInstance();
        boolean mesmoDia = calUltima.get(Calendar.DAY_OF_MONTH) == calHoje.get(Calendar.DAY_OF_MONTH);
        if (mesmoDia && valorSaque.compareTo(new BigDecimal("5000")) > 0) {
            throw new RuntimeException("Valor de saque muito alto!");
        }
    }
}