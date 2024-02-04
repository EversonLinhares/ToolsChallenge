package br.com.toolschallenge.domain.service;

import br.com.toolschallenge.api.dto.request.SaldoRequestDTO;
import br.com.toolschallenge.api.dto.response.SaldoResponseDTO;
import br.com.toolschallenge.config.modelmapper.MapperConvert;
import br.com.toolschallenge.domain.exception.NegocioException;
import br.com.toolschallenge.domain.exception.ObjectNotFoundException;
import br.com.toolschallenge.domain.model.SaldoCartao;
import br.com.toolschallenge.domain.repository.SaldoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.com.toolschallenge.domain.service.Utils.Constantes.DEFAULT_LIMITE;

@Service
@RequiredArgsConstructor
public class SaldoService {

    private final SaldoRepository saldoRepository;
    private final MapperConvert mapperConvert;

    public SaldoResponseDTO editarSaldo(Long id, SaldoRequestDTO saldoRequestDTO) {
        SaldoCartao saldoCartao = pesquisarPorId(id);
        validaSaldoLimite(saldoCartao, saldoRequestDTO);
        saldoCartao.setSaldo(saldoCartao.getSaldo().add(saldoRequestDTO.getSaldo()));
        return mapperConvert.mapEntityToDto(saldoRepository.save(saldoCartao), SaldoResponseDTO.class);
    }

    public SaldoCartao pesquisarPorId(Long id) {
        SaldoCartao saldoCartao = saldoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado saldo com o id informado: " + id));
        return saldoCartao;
    }

    public void validaSaldoLimite(SaldoCartao saldoCartao, SaldoRequestDTO saldoRequestDTO) {
        BigDecimal novoSaldo = saldoCartao.getSaldo().add(saldoRequestDTO.getSaldo());
        BigDecimal limiteSaldo = new BigDecimal(DEFAULT_LIMITE);
        if(novoSaldo.compareTo(limiteSaldo) > 0) {
            throw new NegocioException("Limite maximo da conta foi excedido.");
        }
    }

    public void debitaSaldoCartao(SaldoCartao saldoCartao, BigDecimal valor) {
        saldoCartao.setSaldo(saldoCartao.getSaldo().subtract(valor));
        saldoRepository.save(saldoCartao);
    }

    public void estornarSaldoCartao(BigDecimal valor) {
        SaldoCartao saldoCartao = buscarSaldoCartao();
        saldoCartao.setSaldo(saldoCartao.getSaldo().add(valor));
        saldoRepository.save(saldoCartao);
    }

    public SaldoCartao buscarSaldoCartao() {
        SaldoCartao saldoCartao = pesquisarPorId(1L);
        return saldoCartao;
    }
}
