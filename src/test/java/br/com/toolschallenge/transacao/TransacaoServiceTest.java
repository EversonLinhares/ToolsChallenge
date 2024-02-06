package br.com.toolschallenge.transacao;

import br.com.toolschallenge.api.dto.response.PagamentoResponseDTO;
import br.com.toolschallenge.config.modelmapper.MapperConvert;
import br.com.toolschallenge.domain.enums.StatusTransacaoEnum;
import br.com.toolschallenge.domain.exception.NegocioException;
import br.com.toolschallenge.domain.model.SaldoCartao;
import br.com.toolschallenge.domain.model.Transacao;
import br.com.toolschallenge.domain.repository.TransacaoRepository;
import br.com.toolschallenge.domain.service.SaldoService;
import br.com.toolschallenge.domain.service.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.toolschallenge.saldo.stub.SaldoCartaoStub.getSaldoCartao;
import static br.com.toolschallenge.transacao.stub.TransacaoStub.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private SaldoService saldoService;

    @Mock
    private MapperConvert mapperConvert;

    @Test
    void deveAutorizaRealizarPagamento() {
        when(mapperConvert.mapDtoToEntity(getTransacaoRequestDTO(), Transacao.class)).thenReturn(getTransacao());
        when(saldoService.buscarSaldoCartao()).thenReturn(getSaldoCartao());
        when(transacaoRepository.save(any())).thenReturn(getTransacao());
        when(mapperConvert.mapEntityToDto(getTransacao(), PagamentoResponseDTO.class)).thenReturn(getPagamentoResponseDTO());

        PagamentoResponseDTO resultado = transacaoService.realizarPagamento(getPagamentoRequestDTO());

        assertNotNull(resultado);
        assertEquals(StatusTransacaoEnum.AUTORIZADO, resultado.getTransacao().getDescricao().getStatus());
        verify(transacaoRepository, times(1)).save(getTransacao());
    }

    @Test
    void deveRealizarEstorno() {
        PagamentoResponseDTO pagamentoResponseDTO = getPagamentoResponseDTO();
        pagamentoResponseDTO.getTransacao().getDescricao().setStatus(StatusTransacaoEnum.CANCELADO);
        when(transacaoRepository.findById(any())).thenReturn(Optional.of(getTransacao()));
        when(transacaoRepository.save(any())).thenReturn(getTransacaoCancelada());
        when(mapperConvert.mapEntityToDto(any(),any())).thenReturn(pagamentoResponseDTO);
        PagamentoResponseDTO resultado = transacaoService.realizarEstorno(1L);

        assertNotNull(resultado);
        assertEquals(StatusTransacaoEnum.CANCELADO, resultado.getTransacao().getDescricao().getStatus());
    }

    @Test
    void deveLancaExcecaoEstornoQuandoTransacaoStatusCancelada() {
        when(transacaoRepository.findById(any())).thenReturn(Optional.of(getTransacaoCancelada()));

        NegocioException erro = assertThrows(NegocioException.class, () -> transacaoService.realizarEstorno(anyLong()));

        assertNotNull(erro);
        Assertions.assertTrue(erro.getMessage().contains("Não foi possível realizar estorno, pois a transação " +
                "já se encotra com status de CANCELADA ou foi NEGADA."));
    }

    @Test
    void devePesquisarTransacoes() {
        when(transacaoRepository.findAll()).thenReturn(Collections.singletonList(getTransacao()));
        when(mapperConvert.collectionToDto(any(), eq(PagamentoResponseDTO.class)))
                .thenReturn(Collections.singletonList(getPagamentoResponseDTO()));

        List<PagamentoResponseDTO> resultado = transacaoService.pesquisarTransacoes();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void devePesquisarPorId() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(getTransacao()));
        Transacao resultado = transacaoService.pesquisarPorId(1L);

        assertNotNull(resultado);
    }

    @Test
    void deveNEGARPagamentoQuandoSaldoInsuficiente() {
        Transacao transacao = getTransacao();
        transacao.getDescricao().setStatus(StatusTransacaoEnum.NEGADO);
        SaldoCartao saldoCartao = getSaldoCartao();
        saldoCartao.setSaldo(BigDecimal.valueOf(100));
        when(saldoService.buscarSaldoCartao()).thenReturn(saldoCartao);

        transacaoService.validaPagamentoSaldo(getTransacao(), getPagamentoRequestDTO());

        assertEquals(StatusTransacaoEnum.NEGADO, transacao.getDescricao().getStatus());
    }

}