package br.com.toolschallenge.domain.service;

import br.com.toolschallenge.api.dto.request.PagamentoRequestDTO;
import br.com.toolschallenge.api.dto.response.PagamentoResponseDTO;
import br.com.toolschallenge.config.modelmapper.MapperConvert;
import br.com.toolschallenge.domain.enums.StatusTransacaoEnum;
import br.com.toolschallenge.domain.exception.NegocioException;
import br.com.toolschallenge.domain.exception.ObjectNotFoundException;
import br.com.toolschallenge.domain.model.SaldoCartao;
import br.com.toolschallenge.domain.model.Transacao;
import br.com.toolschallenge.domain.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final SaldoService saldoService;
    private final MapperConvert mapperConvert;

    public PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO pagamentoRequestDTO) {
        Transacao transacao = mapperConvert.mapDtoToEntity(pagamentoRequestDTO.getTransacao(), Transacao.class);
        validaPagamentoSaldo(transacao, pagamentoRequestDTO);
        Transacao transacaoSalva = transacaoRepository.save(transacao);
        PagamentoResponseDTO pagamentoResponseDTO = mapperConvert
                .mapEntityToDto(transacaoSalva, PagamentoResponseDTO.class);
        return pagamentoResponseDTO;
    }

    @Transactional
    public PagamentoResponseDTO realizarEstorno(Long id) {
        Transacao transacao = pesquisarPorId(id);
        if(!transacao.getDescricao().getStatus().equals(StatusTransacaoEnum.AUTORIZADO)){
            throw new NegocioException("Não foi possível realizar estorno, pois a transação " +
                    "já se encotra com status de CANCELADA ou foi NEGADA.");
        }
        transacao.getDescricao().setStatus(StatusTransacaoEnum.CANCELADO);
        saldoService.estornarSaldoCartao(transacao.getDescricao().getValor());
        Transacao transacaoAtualizada = transacaoRepository.save(transacao);
        PagamentoResponseDTO pagamentoResponseDTO = mapperConvert
                .mapEntityToDto(transacaoAtualizada, PagamentoResponseDTO.class);
        return pagamentoResponseDTO;
    }

    public List<PagamentoResponseDTO> pesquisarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<PagamentoResponseDTO> pagamentoResponseDTOS = mapperConvert
                .collectionToDto(transacoes, PagamentoResponseDTO.class);
        return pagamentoResponseDTOS;
    }

    public Transacao pesquisarPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado uma transação com o id informado: " + id));
        return transacao;
    }

     /*
     Código abaixo simula chamada externa api do cartão do banco , lá iria ser feito todas validações necessárias
     aqui só valido o saldo para caso haja saldo eu debito e autorizo ,caso não haja saldo apenas Nego a transação.
     */
    public void validaPagamentoSaldo(Transacao transacao,PagamentoRequestDTO pagamentoRequestDTO) {
        SaldoCartao saldoCartao = saldoService.buscarSaldoCartao();
        BigDecimal saldoAtual = saldoCartao.getSaldo();
        BigDecimal valorPagamento = pagamentoRequestDTO.getTransacao().getDescricao().getValor();
        if(saldoAtual.compareTo(valorPagamento) < 0) {
            transacao.getDescricao().setStatus(StatusTransacaoEnum.NEGADO);
        }else {
            saldoService.debitaSaldoCartao(saldoCartao,valorPagamento);
            transacao.getDescricao().setStatus(StatusTransacaoEnum.AUTORIZADO);
        }
    }

}
