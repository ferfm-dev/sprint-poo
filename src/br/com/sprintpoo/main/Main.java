package br.com.sprintpoo.main;

import br.com.sprintpoo.dao.EquipeManutencaoDAO;
import br.com.sprintpoo.dao.IntervencaoOperacionalDAO;
import br.com.sprintpoo.dao.RelatorioPrioridadeDAO;
import br.com.sprintpoo.dao.TrechoRodoviaDAO;
import br.com.sprintpoo.db.ConexaoBD;
import br.com.sprintpoo.models.EquipeManutencao;
import br.com.sprintpoo.models.IntervencaoOperacional;
import br.com.sprintpoo.models.Pulverizacao;
import br.com.sprintpoo.models.RocadaMecanizada;
import br.com.sprintpoo.models.TrechoRodovia;
import br.com.sprintpoo.service.GeradorRelatorio;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 1. Testar conexão
        ConexaoBD conexao = ConexaoBD.getInstancia();
        conexao.conectar();

        // 2. Testar CRUD de Equipe
        System.out.println("\n=== CRUD EquipeManutencao ===");
        EquipeManutencaoDAO daoEquipe = new EquipeManutencaoDAO();
        EquipeManutencao equipe = new EquipeManutencao("Equipe Norte", "ROCADA", 5);
        daoEquipe.inserir(equipe);

        EquipeManutencao equipeBuscada = daoEquipe.buscarPorId(equipe.getId());
        if (equipeBuscada != null) equipeBuscada.exibirInfo();

        equipeBuscada.setQuantidadeMembros(7);
        daoEquipe.atualizar(equipeBuscada);

        List<EquipeManutencao> equipes = daoEquipe.listarTodas();
        System.out.println("Total de equipes cadastradas: " + equipes.size());

        // 3. Testar Trechos
        System.out.println("\n=== CRUD TrechoRodovia ===");
        TrechoRodoviaDAO daoTrecho = new TrechoRodoviaDAO();

        TrechoRodovia trecho1 = new TrechoRodovia("Rodovia Anhanguera KM 10-15", 10f, 15f, 12f, true);
        TrechoRodovia trecho2 = new TrechoRodovia("Rodovia Bandeirantes KM 40-45", 40f, 45f, 35f, false);
        TrechoRodovia trecho3 = new TrechoRodovia("Rodovia Castello Branco KM 60-65", 60f, 65f, 55f, true);

        daoTrecho.inserir(trecho1);
        daoTrecho.inserir(trecho2);
        daoTrecho.inserir(trecho3);

        TrechoRodovia trechoBuscado = daoTrecho.buscarPorId(trecho1.getId());
        if (trechoBuscado != null) trechoBuscado.trechoInfo();

        trechoBuscado.registrarCrescimentoCM(3f);
        daoTrecho.atualizar(trechoBuscado);

        // 4. Testar Intervenções
        System.out.println("\n=== CRUD IntervencaoOperacional ===");
        IntervencaoOperacionalDAO daoIntervencao = new IntervencaoOperacionalDAO();

        IntervencaoOperacional rocada = new RocadaMecanizada("Roçada no trecho Anhanguera", 5f);
        IntervencaoOperacional pulverizacao = new Pulverizacao("Pulverização no trecho Castello Branco", 5f);

        rocada.executarServico();
        daoIntervencao.inserir(rocada, trecho1.getId(), equipe.getId());

        pulverizacao.executarServico();
        daoIntervencao.inserir(pulverizacao, trecho3.getId(), equipe.getId());

        List<IntervencaoOperacional> intervencoes = daoIntervencao.listarTodas();
        System.out.println("Total de intervenções cadastradas: " + intervencoes.size());

        // 5. Gerar relatório com persistência
        System.out.println("\n=== GeradorRelatorio ===");
        GeradorRelatorio gerador = new GeradorRelatorio();
        List<TrechoRodovia> listaTrechos = daoTrecho.listarTodas();
        TrechoRodovia[] trechos = listaTrechos.toArray(new TrechoRodovia[0]);
        gerador.gerarRelatorio(trechos);

        // 6. Consultar histórico de relatórios
        System.out.println("\n=== Histórico de Relatórios ===");
        RelatorioPrioridadeDAO daoRelatorio = new RelatorioPrioridadeDAO();
        daoRelatorio.listarTodas().forEach(System.out::println);

        // 6.2 Testar deletar (usando dados de teste próprios, pra não mexer no que já existe)
        System.out.println("\n=== Teste de deletar() ===");

        EquipeManutencao equipeTeste = new EquipeManutencao("Equipe Temporária", "PULVERIZACAO", 3);
        daoEquipe.inserir(equipeTeste);
        System.out.println("Equipe temporária criada com ID " + equipeTeste.getId());

        boolean equipeDeletada = daoEquipe.deletar(equipeTeste.getId());
        System.out.println("Equipe deletada? " + equipeDeletada);

        TrechoRodovia trechoTeste = new TrechoRodovia("Trecho Temporário", 1f, 2f, 5f, false);
        daoTrecho.inserir(trechoTeste);
        System.out.println("Trecho temporário criado com ID " + trechoTeste.getId());

        boolean trechoDeletado = daoTrecho.deletar(trechoTeste.getId());
        System.out.println("Trecho deletado? " + trechoDeletado);

        // 7. Fechar conexão
        conexao.desconectar();
    }
}
