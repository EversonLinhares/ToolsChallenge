package br.com.toolschallenge.saldo;

import br.com.toolschallenge.api.dto.request.SaldoRequestDTO;
import br.com.toolschallenge.api.dto.response.SaldoResponseDTO;
import br.com.toolschallenge.config.modelmapper.MapperConvert;
import br.com.toolschallenge.domain.exception.NegocioException;
import br.com.toolschallenge.domain.model.SaldoCartao;
import br.com.toolschallenge.domain.repository.SaldoRepository;
import br.com.toolschallenge.domain.service.SaldoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.toolschallenge.saldo.stub.SaldoCartaoStub.getSaldoCartao;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaldoServiceTest {

    @InjectMocks
    private SaldoService saldoService;

    @Mock
    private SaldoRepository saldoRepository;

    @Mock
    private MapperConvert mapperConvert;

    @Test
    void deveEditarSaldoSucesso() {
        SaldoRequestDTO saldoRequestDTO = new SaldoRequestDTO();
        saldoRequestDTO.setSaldo(BigDecimal.TEN);

        SaldoCartao saldoCartao = getSaldoCartao();
        saldoCartao.setSaldo(saldoCartao.getSaldo().add(saldoRequestDTO.getSaldo()));

        when(saldoRepository.findById(1L)).thenReturn(Optional.of(getSaldoCartao()));
        when(saldoRepository.save(any())).thenReturn(saldoCartao);
        when(mapperConvert.mapEntityToDto(saldoCartao, SaldoResponseDTO.class)).thenReturn(new SaldoResponseDTO());

        SaldoResponseDTO resultado = saldoService.editarSaldo(1L, saldoRequestDTO);

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(510.0), saldoCartao.getSaldo());
        verify(saldoRepository, times(1)).save(saldoCartao);
    }

    @Test
    void deveValidaSaldoLimiteExcedido() {
        SaldoCartao saldoCartao = getSaldoCartao();

        SaldoRequestDTO saldoRequestDTO = new SaldoRequestDTO();
        saldoRequestDTO.setSaldo(BigDecimal.valueOf(10000.00));

        NegocioException excecao = assertThrows(NegocioException.class,
                () -> saldoService.validaSaldoLimite(saldoCartao, saldoRequestDTO));

        assertNotNull(excecao);
        assertEquals("Limite maximo da conta foi excedido.", excecao.getMessage());
    }

    @Test
    void deveDebitaSaldoCartao() {
        SaldoCartao saldoCartao = getSaldoCartao();

        BigDecimal valorDebito = BigDecimal.valueOf(20);

        when(saldoRepository.save(saldoCartao)).thenReturn(saldoCartao);

        saldoService.debitaSaldoCartao(saldoCartao, valorDebito);

        assertEquals(BigDecimal.valueOf(480.0), saldoCartao.getSaldo());
        verify(saldoRepository, times(1)).save(saldoCartao);
    }

    @Test
    void deveEstornarSaldoCartao() {
        SaldoCartao saldoCartao = getSaldoCartao();
        saldoCartao.setSaldo(saldoCartao.getSaldo().add(BigDecimal.valueOf(100)));
        BigDecimal valorEstorno = BigDecimal.valueOf(100);

        when(saldoRepository.findById(anyLong())).thenReturn(Optional.of(getSaldoCartao()));
        when(saldoRepository.save(saldoCartao)).thenReturn(saldoCartao);

        saldoService.estornarSaldoCartao(valorEstorno);

        assertEquals(BigDecimal.valueOf(600.0), saldoCartao.getSaldo());
        verify(saldoRepository, times(1)).save(saldoCartao);
    }

    @Test
    void deveBuscarSaldoCartao() {
        SaldoCartao saldoCartao = getSaldoCartao();
        when(saldoRepository.findById(1L)).thenReturn(java.util.Optional.of(saldoCartao));

        SaldoCartao resultado = saldoService.buscarSaldoCartao();

        assertNotNull(resultado);
        assertSame(saldoCartao, resultado);
    }

}