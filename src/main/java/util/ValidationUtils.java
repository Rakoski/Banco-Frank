package util;

import entidade.Conta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ValidationUtils {

    public Boolean validateCpf(String cpf) {
        int quantCaracCpf = "99999999999".length();
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        if (cpfLimpo.length() != quantCaracCpf) {
            return false;
        }

        for (int i = 0; i < quantCaracCpf; i++) {
            if (Character.isLetter(cpf.charAt(i))) {
                return false;
            }
        }

        return !cpfLimpo.matches("(\\d)\\1{10}");
    }

    public Boolean validaTipoPagamento(Conta conta, String tipoOperacao, BigInteger valor) {
        int valorDescontado = 0;
        if (Objects.equals(tipoOperacao, "pix")) {
            if (Integer.parseInt(String.valueOf(valor)) > 300) {
                return false;
            }
            valorDescontado = 2;
        }
        
        if (Objects.equals(tipoOperacao, "saque")) {
            Date dataUltimaTransacao = conta.getDataTransacao();
            Double valorDaUltimaOperacao = conta.getValorOperacao();

            return verificarLimiteSaqueDiario(dataUltimaTransacao, new BigInteger(String.valueOf(valorDaUltimaOperacao)));
        }

        return true;
    }

    public boolean verificarLimiteSaqueDiario(Date dataUltimaTransacao, BigInteger valorUltimoSaque) {
        Calendar calUltima = Calendar.getInstance();
        calUltima.setTime(dataUltimaTransacao);

        Calendar calHoje = Calendar.getInstance();

        boolean mesmoDia = calUltima.get(Calendar.DAY_OF_MONTH) == calHoje.get(Calendar.DAY_OF_MONTH);

        if (mesmoDia && valorUltimoSaque.compareTo(new BigInteger("5000")) > 0) {
            return false;
        }

        return true;
    }
}
