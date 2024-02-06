package br.com.toolschallenge.domain.model;

import br.com.toolschallenge.domain.enums.StatusTransacaoEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Descricao {

        private BigDecimal valor;
        private LocalDateTime dataHora;
        private String estabelecimento;
        private String nsu = "1234567890";
        private String codigoAutorizacao = "147258369";
        @Enumerated(EnumType.STRING)
        private StatusTransacaoEnum status;
}
