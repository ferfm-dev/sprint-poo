package br.com.sprintpoo.dao;

import br.com.sprintpoo.db.ConexaoBD;
import br.com.sprintpoo.models.TrechoRodovia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrechoRodoviaDAO {

    private static final String SQL_INSERIR =
            "INSERT INTO TB_TRECHO_RODOVIA (ID, TITULO, KM_INICIAL, KM_FINAL, NIVEL_VEGETACAO_CM, POSSUI_IOT) " +
            "VALUES (SEQ_TRECHO.NEXTVAL, ?, ?, ?, ?, ?)";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT ID, TITULO, KM_INICIAL, KM_FINAL, NIVEL_VEGETACAO_CM, POSSUI_IOT FROM TB_TRECHO_RODOVIA WHERE ID = ?";

    private static final String SQL_LISTAR_TODAS =
            "SELECT ID, TITULO, KM_INICIAL, KM_FINAL, NIVEL_VEGETACAO_CM, POSSUI_IOT FROM TB_TRECHO_RODOVIA ORDER BY ID";

    private static final String SQL_ATUALIZAR =
            "UPDATE TB_TRECHO_RODOVIA SET TITULO = ?, KM_INICIAL = ?, KM_FINAL = ?, NIVEL_VEGETACAO_CM = ?, POSSUI_IOT = ? WHERE ID = ?";

    private static final String SQL_DELETAR =
            "DELETE FROM TB_TRECHO_RODOVIA WHERE ID = ?";

    public TrechoRodoviaDAO() {
    }

    public TrechoRodovia inserir(TrechoRodovia trecho) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"})) {
            stmt.setString(1, trecho.getTitulo());
            stmt.setFloat(2, trecho.getQuilometroInicial());
            stmt.setFloat(3, trecho.getQuilometroFinal());
            stmt.setFloat(4, trecho.getNivelVegetacaoCM());
            stmt.setString(5, trecho.isPossuiIoT() ? "S" : "N");
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    trecho.setId(rs.getLong(1));
                }
            }
            System.out.println("[TrechoRodoviaDAO] Trecho inserido com ID " + trecho.getId());
        } catch (SQLException e) {
            System.err.println("[TrechoRodoviaDAO] Erro ao inserir: " + e.getMessage());
        }
        return trecho;
    }

    public TrechoRodovia buscarPorId(Long id) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[TrechoRodoviaDAO] Erro ao buscar por ID: " + e.getMessage());
        }
        return null;
    }

    public List<TrechoRodovia> listarTodas() {
        List<TrechoRodovia> trechos = new ArrayList<>();
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODAS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                trechos.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("[TrechoRodoviaDAO] Erro ao listar: " + e.getMessage());
        }
        return trechos;
    }

    public boolean atualizar(TrechoRodovia trecho) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR)) {
            stmt.setString(1, trecho.getTitulo());
            stmt.setFloat(2, trecho.getQuilometroInicial());
            stmt.setFloat(3, trecho.getQuilometroFinal());
            stmt.setFloat(4, trecho.getNivelVegetacaoCM());
            stmt.setString(5, trecho.isPossuiIoT() ? "S" : "N");
            stmt.setLong(6, trecho.getId());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("[TrechoRodoviaDAO] Erro ao atualizar: " + e.getMessage());
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
            System.err.println("[TrechoRodoviaDAO] Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    private TrechoRodovia mapear(ResultSet rs) throws SQLException {
        return new TrechoRodovia(
                rs.getLong("ID"),
                rs.getString("TITULO"),
                rs.getFloat("KM_INICIAL"),
                rs.getFloat("KM_FINAL"),
                rs.getFloat("NIVEL_VEGETACAO_CM"),
                "S".equals(rs.getString("POSSUI_IOT"))
        );
    }
}
