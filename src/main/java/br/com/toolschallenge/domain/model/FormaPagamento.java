package br.com.toolschallenge.domain.model;

import br.com.toolschallenge.domain.enums.TipoPagamentoEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormaPagamento {

    @Enumerated(EnumType.STRING)
    private TipoPagamentoEnum tipo;

    private int parcelas;
}
