package br.com.sprintpoo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton responsável pela conexão com o Oracle via JDBC puro.
 * Ajuste HOST, PORTA, SERVICO, USER e PASSWORD com as credenciais
 * fornecidas pelo laboratório/FIAP.
 */
public class ConexaoBD {

    private static ConexaoBD instancia;
    private Connection connection;

    // TODO: troque pelas suas credenciais reais (RM e senha do Oracle FIAP)
    private static final String HOST = "oracle.fiap.com.br";
    private static final String PORTA = "1521";
    private static final String SERVICO = "ORCL";
    private static final String USER = "RM564297";
    private static final String PASSWORD = "310706";

    private static final String URL = "jdbc:oracle:thin:@//" + HOST + ":" + PORTA + "/" + SERVICO;

    private ConexaoBD() {
    }

    public static ConexaoBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexaoBD();
        }
        return instancia;
    }

    public Connection conectar() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[ConexaoBD] Conexão estabelecida com sucesso!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[ConexaoBD] Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ConexaoBD] Erro ao conectar: " + e.getMessage());
        }
        return connection;
    }

    public Connection getConnection() {
        // Garante que sempre existe uma conexão ativa antes de qualquer operação
        return conectar();
    }

    public void desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[ConexaoBD] Conexão encerrada.");
            }
        } catch (SQLException e) {
            System.err.println("[ConexaoBD] Erro ao desconectar: " + e.getMessage());
        }
    }
}
