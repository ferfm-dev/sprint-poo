package br.com.sprintpoo.service;

import br.com.sprintpoo.dao.RelatorioPrioridadeDAO;
import br.com.sprintpoo.models.TrechoRodovia;

/**
 * Classifica trechos de rodovia por nível de urgência de manutenção,
 * com base no nível de vegetação (cm), imprime o relatório no console
 * e salva o resumo no banco via RelatorioPrioridadeDAO.
 *
 * Faixas de classificação (ajustáveis conforme critério da disciplina):
 *   URGENTE  >= 50 cm
 *   CRITICO  >= 30 cm e < 50 cm
 *   ATENCAO  >= 15 cm e < 30 cm
 *   NORMAL   < 15 cm
 */
public class GeradorRelatorio {

    private static final float LIMITE_URGENTE = 50f;
    private static final float LIMITE_CRITICO = 30f;
    private static final float LIMITE_ATENCAO = 15f;

    public void imprimirCabecalho() {
        System.out.println("#######################################");
        System.out.println("   RELATÓRIO DE PRIORIDADE - MOTIVA");
        System.out.println("#######################################");
    }

    public void gerarRelatorio(TrechoRodovia[] trechos) {
        // 1. Lógica existente (console)
        imprimirCabecalho();

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

            String linha = "- " + trecho.getTitulo() + " | " + trecho.getNivelVegetacaoCM()
                    + " cm | Prioridade: " + prioridade;
            System.out.println(linha);
            resumo.append(linha).append(System.lineSeparator());
        }

        System.out.println("---------------------------------------");
        System.out.println("Urgente: " + qtUrgente + " | Crítico: " + qtCritico
                + " | Atenção: " + qtAtencao + " | Normal: " + qtNormal);
        System.out.println("#######################################");

        // 2. NOVO: Salvar no banco
        RelatorioPrioridadeDAO dao = new RelatorioPrioridadeDAO();
        dao.salvarRelatorio(qtUrgente, qtCritico, qtAtencao, qtNormal, resumo.toString());
    }

    private String classificar(float nivelVegetacaoCM) {
        if (nivelVegetacaoCM >= LIMITE_URGENTE) return "URGENTE";
        if (nivelVegetacaoCM >= LIMITE_CRITICO) return "CRITICO";
        if (nivelVegetacaoCM >= LIMITE_ATENCAO) return "ATENCAO";
        return "NORMAL";
    }
}
