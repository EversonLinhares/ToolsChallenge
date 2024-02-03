package br.com.toolschallenge.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cartao;

    @Embedded
    private Descricao descricao;

    @Embedded
    private FormaPagamento formaPagamento;
}

