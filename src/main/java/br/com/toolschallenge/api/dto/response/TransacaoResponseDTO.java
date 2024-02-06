package br.com.toolschallenge.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransacaoResponseDTO {

    private Long id;
    private String cartao;
    private DescricaoResponseDTO descricao;
    private FormaPagamentoResponseDTO formaPagamento;
}
