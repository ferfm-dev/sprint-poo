package br.com.sprintpoo.repository;

import br.com.sprintpoo.model.RelatorioPrioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RelatorioPrioridadeRepository extends JpaRepository<RelatorioPrioridade, Long> {

    // Derived Query para o requisito 3.3 (Consulta históricos por período)
    List<RelatorioPrioridade> findByDataGeracaoBetween(LocalDateTime inicio, LocalDateTime fim);
}