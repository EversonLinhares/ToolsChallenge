package br.com.toolschallenge.domain.repository;

import br.com.toolschallenge.domain.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
