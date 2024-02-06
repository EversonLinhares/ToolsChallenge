package br.com.toolschallenge.saldo.stub;

import br.com.toolschallenge.domain.model.SaldoCartao;

import java.math.BigDecimal;

public class SaldoCartaoStub {

    public static SaldoCartao getSaldoCartao() {
        SaldoCartao saldoCartao = SaldoCartao.builder().id(1L).saldo(BigDecimal.valueOf(500.00)).build();
        return saldoCartao;
    }
}
