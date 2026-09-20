package br.com.sprintpoo.dao;

import br.com.sprintpoo.db.ConexaoBD;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RelatorioPrioridadeDAO {

    // Record representando a entidade RELATORIO_PRIORIDADE (não existe classe de domínio própria)
    public record RelatorioPrioridade(
            Long id,
            LocalDateTime dataGeracao,
            int qtUrgente,
            int qtCritico,
            int qtAtencao,
            int qtNormal,
            String resumo
    ) {
        @Override
        public String toString() {
            return "Relatório #" + id + " (" + dataGeracao + ") - Urgente: " + qtUrgente
                    + " | Crítico: " + qtCritico + " | Atenção: " + qtAtencao + " | Normal: " + qtNormal;
        }
    }

    private static final String SQL_INSERIR =
            "INSERT INTO TB_RELATORIO_PRIORIDADE (ID, DATA_GERACAO, QT_URGENTE, QT_CRITICO, QT_ATENCAO, QT_NORMAL, RESUMO) " +
            "VALUES (SEQ_RELATORIO.NEXTVAL, SYSTIMESTAMP, ?, ?, ?, ?, ?)";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT ID, DATA_GERACAO, QT_URGENTE, QT_CRITICO, QT_ATENCAO, QT_NORMAL, RESUMO FROM TB_RELATORIO_PRIORIDADE WHERE ID = ?";

    private static final String SQL_LISTAR_TODAS =
            "SELECT ID, DATA_GERACAO, QT_URGENTE, QT_CRITICO, QT_ATENCAO, QT_NORMAL, RESUMO FROM TB_RELATORIO_PRIORIDADE ORDER BY ID";

    private static final String SQL_ATUALIZAR =
            "UPDATE TB_RELATORIO_PRIORIDADE SET QT_URGENTE = ?, QT_CRITICO = ?, QT_ATENCAO = ?, QT_NORMAL = ?, RESUMO = ? WHERE ID = ?";

    private static final String SQL_DELETAR =
            "DELETE FROM TB_RELATORIO_PRIORIDADE WHERE ID = ?";

    public RelatorioPrioridadeDAO() {
    }

    /** Método chamado pelo GeradorRelatorio para persistir o resumo gerado. */
    public RelatorioPrioridade salvarRelatorio(int qtUrgente, int qtCritico, int qtAtencao, int qtNormal, String resumo) {
        return inserir(new RelatorioPrioridade(null, null, qtUrgente, qtCritico, qtAtencao, qtNormal, resumo));
    }

    public RelatorioPrioridade inserir(RelatorioPrioridade relatorio) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"})) {
            stmt.setInt(1, relatorio.qtUrgente());
            stmt.setInt(2, relatorio.qtCritico());
            stmt.setInt(3, relatorio.qtAtencao());
            stmt.setInt(4, relatorio.qtNormal());
            stmt.setString(5, relatorio.resumo());
            stmt.executeUpdate();

            Long novoId = null;
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    novoId = rs.getLong(1);
                }
            }
            System.out.println("[RelatorioPrioridadeDAO] Relatório salvo com ID " + novoId);
            return buscarPorId(novoId);
        } catch (SQLException e) {
            System.err.println("[RelatorioPrioridadeDAO] Erro ao inserir: " + e.getMessage());
            return relatorio;
        }
    }

    public RelatorioPrioridade buscarPorId(Long id) {
        if (id == null) return null;
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[RelatorioPrioridadeDAO] Erro ao buscar por ID: " + e.getMessage());
        }
        return null;
    }

    public List<RelatorioPrioridade> listarTodas() {
        List<RelatorioPrioridade> relatorios = new ArrayList<>();
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODAS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                relatorios.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("[RelatorioPrioridadeDAO] Erro ao listar: " + e.getMessage());
        }
        return relatorios;
    }

    public boolean atualizar(RelatorioPrioridade relatorio) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR)) {
            stmt.setInt(1, relatorio.qtUrgente());
            stmt.setInt(2, relatorio.qtCritico());
            stmt.setInt(3, relatorio.qtAtencao());
            stmt.setInt(4, relatorio.qtNormal());
            stmt.setString(5, relatorio.resumo());
            stmt.setLong(6, relatorio.id());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("[RelatorioPrioridadeDAO] Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(Long id) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETAR)) {
            stmt.setLong(1, id);
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("[RelatorioPrioridadeDAO] Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    private RelatorioPrioridade mapear(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("DATA_GERACAO");
        return new RelatorioPrioridade(
                rs.getLong("ID"),
                ts != null ? ts.toLocalDateTime() : null,
                rs.getInt("QT_URGENTE"),
                rs.getInt("QT_CRITICO"),
                rs.getInt("QT_ATENCAO"),
                rs.getInt("QT_NORMAL"),
                rs.getString("RESUMO")
        );
    }
}
