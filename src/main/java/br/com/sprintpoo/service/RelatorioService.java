package br.com.sprintpoo.service;

import br.com.sprintpoo.model.RelatorioPrioridade;
import br.com.sprintpoo.model.TrechoRodovia;
import br.com.sprintpoo.repository.RelatorioPrioridadeRepository;
import br.com.sprintpoo.repository.TrechoRodoviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioService {

    private static final float LIMITE_URGENTE = 50f;
    private static final float LIMITE_CRITICO = 30f;
    private static final float LIMITE_ATENCAO = 15f;

    @Autowired
    private TrechoRodoviaRepository trechoRepository;

    @Autowired
    private RelatorioPrioridadeRepository relatorioRepository;

    public RelatorioPrioridade gerarESalvarRelatorio() {
        List<TrechoRodovia> trechos = trechoRepository.findAll();

        int qtUrgente = 0, qtCritico = 0, qtAtencao = 0, qtNormal = 0;
        StringBuilder resumo = new StringBuilder();

        for (TrechoRodovia trecho : trechos) {
            String prioridade = classificar(trecho.getNivelVegetacaoCM());

            switch (prioridade) {
                case "URGENTE" -> qtUrgente++;
                case "CRITICO" -> qtCritico++;
                case "ATENCAO" -> qtAtencao++;
                default -> qtNormal++;
            }

            resumo.append("- ").append(trecho.getTitulo())
                    .append(" | ").append(trecho.getNivelVegetacaoCM())
                    .append(" cm | Prioridade: ").append(prioridade)
                    .append(System.lineSeparator());
        }

        RelatorioPrioridade relatorio = new RelatorioPrioridade(qtUrgente, qtCritico, qtAtencao, qtNormal, resumo.toString());
        return relatorioRepository.save(relatorio);
    }

    public List<RelatorioPrioridade> listarHistorico() {
        return relatorioRepository.findAll();
    }

    public List<RelatorioPrioridade> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return relatorioRepository.findByDataGeracaoBetween(inicio, fim);
    }

    private String classificar(float nivelVegetacaoCM) {
        if (nivelVegetacaoCM >= LIMITE_URGENTE) return "URGENTE";
        if (nivelVegetacaoCM >= LIMITE_CRITICO) return "CRITICO";
        if (nivelVegetacaoCM >= LIMITE_ATENCAO) return "ATENCAO";
        return "NORMAL";
    }
}