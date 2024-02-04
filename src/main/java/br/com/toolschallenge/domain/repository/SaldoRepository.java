package br.com.toolschallenge.domain.repository;

import br.com.toolschallenge.domain.model.SaldoCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaldoRepository extends JpaRepository<SaldoCartao, Long> {
}
