package br.com.toolschallenge.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaldoResponseDTO {

    private Long id;
    private BigDecimal saldo;
}
