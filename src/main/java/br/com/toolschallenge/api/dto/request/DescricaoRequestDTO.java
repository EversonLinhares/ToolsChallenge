package br.com.toolschallenge.api.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DescricaoRequestDTO {

    @Positive(message = "O valor deve ser um número positivo")
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String Estabelecimento;
}
