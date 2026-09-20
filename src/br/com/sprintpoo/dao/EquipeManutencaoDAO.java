package br.com.sprintpoo.dao;

import br.com.sprintpoo.db.ConexaoBD;
import br.com.sprintpoo.models.EquipeManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {

    private static final String SQL_INSERIR =
            "INSERT INTO TB_EQUIPE_MANUTENCAO (ID, NOME, ESPECIALIDADE, QTD_MEMBROS, DISPONIVEL) " +
            "VALUES (SEQ_EQUIPE.NEXTVAL, ?, ?, ?, ?)";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT ID, NOME, ESPECIALIDADE, QTD_MEMBROS, DISPONIVEL FROM TB_EQUIPE_MANUTENCAO WHERE ID = ?";

    private static final String SQL_LISTAR_TODAS =
            "SELECT ID, NOME, ESPECIALIDADE, QTD_MEMBROS, DISPONIVEL FROM TB_EQUIPE_MANUTENCAO ORDER BY ID";

    private static final String SQL_ATUALIZAR =
            "UPDATE TB_EQUIPE_MANUTENCAO SET NOME = ?, ESPECIALIDADE = ?, QTD_MEMBROS = ?, DISPONIVEL = ? WHERE ID = ?";

    private static final String SQL_DELETAR =
            "DELETE FROM TB_EQUIPE_MANUTENCAO WHERE ID = ?";

    public EquipeManutencaoDAO() {
    }

    public EquipeManutencao inserir(EquipeManutencao equipe) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"})) {
            stmt.setString(1, equipe.getNome());
            stmt.setString(2, equipe.getEspecialidade());
            stmt.setInt(3, equipe.getQuantidadeMembros());
            stmt.setString(4, equipe.isDisponivel() ? "S" : "N");
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    equipe.setId(rs.getLong(1));
                }
            }
            System.out.println("[EquipeManutencaoDAO] Equipe inserida com ID " + equipe.getId());
        } catch (SQLException e) {
            System.err.println("[EquipeManutencaoDAO] Erro ao inserir: " + e.getMessage());
        }
        return equipe;
    }

    public EquipeManutencao buscarPorId(Long id) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[EquipeManutencaoDAO] Erro ao buscar por ID: " + e.getMessage());
        }
        return null;
    }

    public List<EquipeManutencao> listarTodas() {
        List<EquipeManutencao> equipes = new ArrayList<>();
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODAS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                equipes.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("[EquipeManutencaoDAO] Erro ao listar: " + e.getMessage());
        }
        return equipes;
    }

    public boolean atualizar(EquipeManutencao equipe) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR)) {
            stmt.setString(1, equipe.getNome());
            stmt.setString(2, equipe.getEspecialidade());
            stmt.setInt(3, equipe.getQuantidadeMembros());
            stmt.setString(4, equipe.isDisponivel() ? "S" : "N");
            stmt.setLong(5, equipe.getId());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("[EquipeManutencaoDAO] Erro ao atualizar: " + e.getMessage());
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
            System.err.println("[EquipeManutencaoDAO] Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    private EquipeManutencao mapear(ResultSet rs) throws SQLException {
        return new EquipeManutencao(
                rs.getLong("ID"),
                rs.getString("NOME"),
                rs.getString("ESPECIALIDADE"),
                rs.getInt("QTD_MEMBROS"),
                "S".equals(rs.getString("DISPONIVEL"))
        );
    }
}
