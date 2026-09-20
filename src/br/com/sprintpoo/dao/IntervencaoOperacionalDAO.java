package br.com.sprintpoo.dao;

import br.com.sprintpoo.db.ConexaoBD;
import br.com.sprintpoo.models.IntervencaoOperacional;
import br.com.sprintpoo.models.Pulverizacao;
import br.com.sprintpoo.models.RocadaMecanizada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para IntervencaoOperacional (RocadaMecanizada / Pulverizacao).
 * As duas subclasses são persistidas na mesma tabela, diferenciadas
 * pela coluna TIPO ('ROCADA' ou 'PULVERIZACAO').
 * ID_TRECHO e ID_EQUIPE são opcionais (podem ser null caso a intervenção
 * ainda não esteja vinculada a um trecho/equipe).
 */
public class IntervencaoOperacionalDAO {

    private static final String SQL_INSERIR =
            "INSERT INTO TB_INTERVENCAO_OPERACIONAL (ID, TIPO, DESCRICAO, EXTENSAO_KM, ID_TRECHO, ID_EQUIPE) " +
            "VALUES (SEQ_INTERVENCAO.NEXTVAL, ?, ?, ?, ?, ?)";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT ID, TIPO, DESCRICAO, EXTENSAO_KM, ID_TRECHO, ID_EQUIPE FROM TB_INTERVENCAO_OPERACIONAL WHERE ID = ?";

    private static final String SQL_LISTAR_TODAS =
            "SELECT ID, TIPO, DESCRICAO, EXTENSAO_KM, ID_TRECHO, ID_EQUIPE FROM TB_INTERVENCAO_OPERACIONAL ORDER BY ID";

    private static final String SQL_ATUALIZAR =
            "UPDATE TB_INTERVENCAO_OPERACIONAL SET TIPO = ?, DESCRICAO = ?, EXTENSAO_KM = ?, ID_TRECHO = ?, ID_EQUIPE = ? WHERE ID = ?";

    private static final String SQL_DELETAR =
            "DELETE FROM TB_INTERVENCAO_OPERACIONAL WHERE ID = ?";

    public IntervencaoOperacionalDAO() {
    }

    public IntervencaoOperacional inserir(IntervencaoOperacional intervencao, Long idTrecho, Long idEquipe) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"})) {
            stmt.setString(1, intervencao.getTipo());
            stmt.setString(2, intervencao.getDescricao());
            stmt.setFloat(3, intervencao.getExtensaoKM());
            setNullableLong(stmt, 4, idTrecho);
            setNullableLong(stmt, 5, idEquipe);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    intervencao.setId(rs.getLong(1));
                }
            }
            System.out.println("[IntervencaoOperacionalDAO] Intervenção inserida com ID " + intervencao.getId());
        } catch (SQLException e) {
            System.err.println("[IntervencaoOperacionalDAO] Erro ao inserir: " + e.getMessage());
        }
        return intervencao;
    }

    public IntervencaoOperacional buscarPorId(Long id) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[IntervencaoOperacionalDAO] Erro ao buscar por ID: " + e.getMessage());
        }
        return null;
    }

    public List<IntervencaoOperacional> listarTodas() {
        List<IntervencaoOperacional> intervencoes = new ArrayList<>();
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODAS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                intervencoes.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("[IntervencaoOperacionalDAO] Erro ao listar: " + e.getMessage());
        }
        return intervencoes;
    }

    public boolean atualizar(IntervencaoOperacional intervencao, Long idTrecho, Long idEquipe) {
        Connection conn = ConexaoBD.getInstancia().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR)) {
            stmt.setString(1, intervencao.getTipo());
            stmt.setString(2, intervencao.getDescricao());
            stmt.setFloat(3, intervencao.getExtensaoKM());
            setNullableLong(stmt, 4, idTrecho);
            setNullableLong(stmt, 5, idEquipe);
            stmt.setLong(6, intervencao.getId());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("[IntervencaoOperacionalDAO] Erro ao atualizar: " + e.getMessage());
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
            System.err.println("[IntervencaoOperacionalDAO] Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    private IntervencaoOperacional mapear(ResultSet rs) throws SQLException {
        Long id = rs.getLong("ID");
        String tipo = rs.getString("TIPO");
        String descricao = rs.getString("DESCRICAO");
        float extensaoKM = rs.getFloat("EXTENSAO_KM");

        if (RocadaMecanizada.TIPO.equals(tipo)) {
            return new RocadaMecanizada(id, descricao, extensaoKM);
        } else if (Pulverizacao.TIPO.equals(tipo)) {
            return new Pulverizacao(id, descricao, extensaoKM);
        }
        throw new IllegalStateException("Tipo de intervenção desconhecido: " + tipo);
    }

    private void setNullableLong(PreparedStatement stmt, int indice, Long valor) throws SQLException {
        if (valor == null) {
            stmt.setNull(indice, Types.NUMERIC);
        } else {
            stmt.setLong(indice, valor);
        }
    }
}
