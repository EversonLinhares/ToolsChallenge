package br.com.toolschallenge.transacao.stub;

import br.com.toolschallenge.api.dto.request.DescricaoRequestDTO;
import br.com.toolschallenge.api.dto.request.FormaPagamentoRequestDTO;
import br.com.toolschallenge.api.dto.request.PagamentoRequestDTO;
import br.com.toolschallenge.api.dto.request.TransacaoRequestDTO;
import br.com.toolschallenge.api.dto.response.DescricaoResponseDTO;
import br.com.toolschallenge.api.dto.response.FormaPagamentoResponseDTO;
import br.com.toolschallenge.api.dto.response.PagamentoResponseDTO;
import br.com.toolschallenge.api.dto.response.TransacaoResponseDTO;
import br.com.toolschallenge.domain.enums.StatusTransacaoEnum;
import br.com.toolschallenge.domain.enums.TipoPagamentoEnum;
import br.com.toolschallenge.domain.model.Descricao;
import br.com.toolschallenge.domain.model.FormaPagamento;
import br.com.toolschallenge.domain.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class TransacaoStub {

    private static LocalDateTime data = LocalDateTime.now();
    public static PagamentoRequestDTO getPagamentoRequestDTO() {
        PagamentoRequestDTO pagamento = PagamentoRequestDTO.builder()
                .transacao(getTransacaoRequestDTO())
                .build();
        return pagamento;
    }

    public static TransacaoRequestDTO getTransacaoRequestDTO() {
        TransacaoRequestDTO transacao = TransacaoRequestDTO
                .builder()
                .cartao("0000111122223333")
                .descricao(getDescricaoRequestDTO())
                .formaPagamento(getFormaPagamentoRequestDTO())
                .build();
        return transacao;
    }

    public static DescricaoRequestDTO getDescricaoRequestDTO() {
        DescricaoRequestDTO descricao = DescricaoRequestDTO.builder()
                .valor(BigDecimal.valueOf(250.00))
                .Estabelecimento("Petshop mundo cão")
                .dataHora(data)
                .build();
        return descricao;
    }

    public static FormaPagamentoRequestDTO getFormaPagamentoRequestDTO() {
        FormaPagamentoRequestDTO formaPagamento = FormaPagamentoRequestDTO
                .builder()
                .tipo(TipoPagamentoEnum.AVISTA)
                .parcelas(1)
                .build();
        return formaPagamento;
    }

    public static Transacao getTransacao() {
        Transacao transacao = Transacao
                .builder()
                .id(1L)
                .cartao("0000111122223333")
                .descricao(getDescricao())
                .formaPagamento(getFormaPagamento())
                .build();
        return transacao;
    }

    public static Transacao getTransacaoCancelada() {
        Transacao transacao = Transacao
                .builder()
                .id(1L)
                .cartao("0000111122223333")
                .descricao(getDescricaoCancelada())
                .formaPagamento(getFormaPagamento())
                .build();
        return transacao;
    }

    public static Descricao getDescricao() {
        Descricao descricao = Descricao
                .builder()
                .estabelecimento("Petshop mundo cão")
                .status(StatusTransacaoEnum.AUTORIZADO)
                .valor(BigDecimal.valueOf(250.00))
                .dataHora(data)
                .build();
        return descricao;
    }

    public static Descricao getDescricaoCancelada() {
        Descricao descricao = Descricao
                .builder()
                .estabelecimento("Petshop mundo cão")
                .status(StatusTransacaoEnum.CANCELADO)
                .valor(BigDecimal.valueOf(250.00))
                .dataHora(data)
                .build();
        return descricao;
    }

    public static FormaPagamento getFormaPagamento() {
        FormaPagamento formaPagamento = FormaPagamento
                .builder()
                .parcelas(1)
                .tipo(TipoPagamentoEnum.AVISTA)
                .build();
        return formaPagamento;
    }

    public static PagamentoResponseDTO getPagamentoResponseDTO() {
        PagamentoResponseDTO pagamento = PagamentoResponseDTO.builder()
                .transacao(getTransacaoResponseDTO())
                .build();
        return pagamento;
    }

    public static TransacaoResponseDTO getTransacaoResponseDTO() {
        TransacaoResponseDTO transacao = TransacaoResponseDTO
                .builder()
                .cartao("0000111122223333")
                .descricao(getDescricaoResponseDTO())
                .formaPagamento(getFormaPagamentoResponseDTO())
                .build();
        return transacao;
    }

    public static DescricaoResponseDTO getDescricaoResponseDTO() {
        DescricaoResponseDTO descricao = DescricaoResponseDTO.builder()
                .valor(BigDecimal.valueOf(250.00))
                .dataHora(data)
                .status(StatusTransacaoEnum.AUTORIZADO)
                .build();
        return descricao;
    }

    public static FormaPagamentoResponseDTO getFormaPagamentoResponseDTO() {
        FormaPagamentoResponseDTO formaPagamento = FormaPagamentoResponseDTO
                .builder()
                .tipo(TipoPagamentoEnum.AVISTA)
                .parcelas(1)
                .build();
        return formaPagamento;
    }

}
