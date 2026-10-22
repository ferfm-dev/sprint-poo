package br.com.sprintpoo.repository;

import br.com.sprintpoo.model.TrechoRodovia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrechoRodoviaRepository extends JpaRepository<TrechoRodovia, Long> {

    // Derived Query exigida no requisito 3.4 da Sprint
    List<TrechoRodovia> findByNivelVegetacaoCmGreaterThanEqual(float minimo);
}