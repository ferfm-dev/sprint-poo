-- =========================================================
-- MOTIVA - Sprint 3 - Script de criação das tabelas (Oracle)
-- =========================================================

-- Limpeza (rode só se quiser recriar do zero)
-- DROP TABLE TB_INTERVENCAO_OPERACIONAL CASCADE CONSTRAINT;
-- DROP TABLE TB_RELATORIO_PRIORIDADE CASCADE CONSTRAINT;
-- DROP TABLE TB_TRECHO_RODOVIA CASCADE CONSTRAINT;
-- DROP TABLE TB_EQUIPE_MANUTENCAO CASCADE CONSTRAINT;
-- DROP SEQUENCE SEQ_TRECHO;
-- DROP SEQUENCE SEQ_EQUIPE;
-- DROP SEQUENCE SEQ_INTERVENCAO;
-- DROP SEQUENCE SEQ_RELATORIO;

-- ---------------------------------------------------------
-- Sequences (Oracle não tem AUTO_INCREMENT nativo)
-- ---------------------------------------------------------
CREATE SEQUENCE SEQ_TRECHO START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_EQUIPE START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_INTERVENCAO START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_RELATORIO START WITH 1 INCREMENT BY 1;

-- ---------------------------------------------------------
-- TB_TRECHO_RODOVIA
-- ---------------------------------------------------------
CREATE TABLE TB_TRECHO_RODOVIA (
    ID                  NUMBER          PRIMARY KEY,
    TITULO              VARCHAR2(150)   NOT NULL,
    KM_INICIAL          NUMBER(6,2)     NOT NULL,
    KM_FINAL            NUMBER(6,2)     NOT NULL,
    NIVEL_VEGETACAO_CM  NUMBER(6,2)     NOT NULL,
    POSSUI_IOT          CHAR(1)         DEFAULT 'N' CHECK (POSSUI_IOT IN ('S','N'))
);

-- ---------------------------------------------------------
-- TB_EQUIPE_MANUTENCAO
-- ---------------------------------------------------------
CREATE TABLE TB_EQUIPE_MANUTENCAO (
    ID                  NUMBER          PRIMARY KEY,
    NOME                VARCHAR2(100)   NOT NULL,
    ESPECIALIDADE       VARCHAR2(30)    NOT NULL CHECK (ESPECIALIDADE IN ('ROCADA','PULVERIZACAO','MISTA')),
    QTD_MEMBROS         NUMBER(3)       NOT NULL,
    DISPONIVEL          CHAR(1)         DEFAULT 'S' CHECK (DISPONIVEL IN ('S','N'))
);

-- ---------------------------------------------------------
-- TB_INTERVENCAO_OPERACIONAL (RocadaMecanizada / Pulverizacao)
-- ---------------------------------------------------------
CREATE TABLE TB_INTERVENCAO_OPERACIONAL (
    ID                  NUMBER          PRIMARY KEY,
    TIPO                VARCHAR2(20)    NOT NULL CHECK (TIPO IN ('ROCADA','PULVERIZACAO')),
    DESCRICAO           VARCHAR2(200)   NOT NULL,
    EXTENSAO_KM         NUMBER(6,2)     NOT NULL,
    ID_TRECHO           NUMBER,
    ID_EQUIPE           NUMBER,
    CONSTRAINT FK_INTERVENCAO_TRECHO FOREIGN KEY (ID_TRECHO) REFERENCES TB_TRECHO_RODOVIA (ID),
    CONSTRAINT FK_INTERVENCAO_EQUIPE FOREIGN KEY (ID_EQUIPE) REFERENCES TB_EQUIPE_MANUTENCAO (ID)
);

-- ---------------------------------------------------------
-- TB_RELATORIO_PRIORIDADE (histórico gerado pelo GeradorRelatorio)
-- ---------------------------------------------------------
CREATE TABLE TB_RELATORIO_PRIORIDADE (
    ID                  NUMBER          PRIMARY KEY,
    DATA_GERACAO        TIMESTAMP       DEFAULT SYSTIMESTAMP,
    QT_URGENTE          NUMBER(5)       DEFAULT 0,
    QT_CRITICO          NUMBER(5)       DEFAULT 0,
    QT_ATENCAO          NUMBER(5)       DEFAULT 0,
    QT_NORMAL           NUMBER(5)       DEFAULT 0,
    RESUMO              CLOB
);
