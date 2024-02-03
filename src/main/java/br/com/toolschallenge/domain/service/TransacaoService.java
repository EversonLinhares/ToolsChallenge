package br.com.toolschallenge.domain.service;

import br.com.toolschallenge.api.dto.request.PagamentoRequestDTO;
import br.com.toolschallenge.api.dto.response.PagamentoResponseDTO;
import br.com.toolschallenge.config.modelmapper.MapperConvert;
import br.com.toolschallenge.domain.enums.StatusTransacaoEnum;
import br.com.toolschallenge.domain.exception.ObjectNotFoundException;
import br.com.toolschallenge.domain.model.Transacao;
import br.com.toolschallenge.domain.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final MapperConvert mapperConvert;

    public PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO pagamentoRequestDTO) {
        Transacao transacao = mapperConvert.mapDtoToEntity(pagamentoRequestDTO.getTransacao(), Transacao.class);
        transacao.getDescricao().setStatus(StatusTransacaoEnum.AUTORIZADO);
        Transacao transacaoSalva = transacaoRepository.save(transacao);
        PagamentoResponseDTO pagamentoResponseDTO = mapperConvert
                .mapEntityToDto(transacaoSalva, PagamentoResponseDTO.class);
        return pagamentoResponseDTO;
    }

//    public PagamentoResponseDTO realizarEstorno(Long id, PagamentoRequestDTO pagamentoRequestDTO) {
//
//        transacao.setStatus(StatusTransacao.CANCELADO);
//    }

    public List<PagamentoResponseDTO> pesquisarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<PagamentoResponseDTO> pagamentoResponseDTOS = mapperConvert
                .collectionToDto(transacoes, PagamentoResponseDTO.class);
        return pagamentoResponseDTOS;
    }

    public PagamentoResponseDTO pesquisarPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado uma transação com o id informado: " + id));
        PagamentoResponseDTO pagamentoDTO = mapperConvert.mapEntityToDto(transacao, PagamentoResponseDTO.class);
        return pagamentoDTO;
    }
}
