package br.com.toolschallenge.api.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaldoRequestDTO {

    @Positive(message = "O valor deve ser um número positivo")
    private BigDecimal saldo;
}
