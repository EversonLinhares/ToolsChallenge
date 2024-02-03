package br.com.toolschallenge.api.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagamentoRequestDTO {

    private @Valid TransacaoRequestDTO transacao;

}
