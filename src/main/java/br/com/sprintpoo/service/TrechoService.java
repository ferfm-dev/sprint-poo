package br.com.sprintpoo.service;

import br.com.sprintpoo.model.TrechoRodovia;
import br.com.sprintpoo.repository.TrechoRodoviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrechoService {

    @Autowired
    private TrechoRodoviaRepository repository;

    public List<TrechoRodovia> listarTodos() {
        return repository.findAll();
    }

    public TrechoRodovia buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Trecho não encontrado"));
    }

    public TrechoRodovia salvar(TrechoRodovia trecho) {
        // Validação da Sprint 1 mantida no Service
        if (trecho.getNivelVegetacaoCM() < 0) {
            throw new IllegalArgumentException("O nível de vegetação não pode ser negativo.");
        }
        return repository.save(trecho);
    }

    public void deletar(Long id) {
        TrechoRodovia trecho = buscarPorId(id);
        repository.delete(trecho);
    }
}