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
    private static int HORA_FIM_PIX = 22;
    private static final int HORA_INICIO_PIX =6;
    private static final double VALOR_MAXIMO_PIX = 300.0;
    private static final int TAXA_PIX_PAGAMENTO = 5;
    private static final int TAXA_SAQUE = 2;


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

        if (cpfLimpo.matches("(\\d)\\1{10}")) throw new RuntimeException("CPF inválido!");
    }

    public int validaTipoPagamento(Conta conta, String tipoOperacao, BigInteger valor) {
        int valorDescontado = 0;
        if (Objects.equals(tipoOperacao, "pix") || Objects.equals(tipoOperacao, "pagamento")) {
            if (Objects.equals(tipoOperacao, "pix")) {
                if (Integer.parseInt(String.valueOf(valor)) > 300) throw new RuntimeException("Valor de pix muito alto!");
                if (Objects.equals(tipoOperacao, "pix")) validaHorarioPix();
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