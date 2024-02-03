package br.com.toolschallenge.api.dto.response;

import br.com.toolschallenge.domain.enums.TipoPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamentoResponseDTO {

    private TipoPagamentoEnum tipo;
    private Integer parcelas;
}
