package br.com.sprintpoo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RELATORIO_PRIORIDADE")
public class RelatorioPrioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relatorio_seq")
    @SequenceGenerator(name = "relatorio_seq", sequenceName = "SEQ_RELATORIO", allocationSize = 1)
    private Long id;

    @Column(name = "DATA_GERACAO", insertable = false, updatable = false)
    private LocalDateTime dataGeracao;

    @Column(name = "QT_URGENTE")
    private int qtUrgente;

    @Column(name = "QT_CRITICO")
    private int qtCritico;

    @Column(name = "QT_ATENCAO")
    private int qtAtencao;

    @Column(name = "QT_NORMAL")
    private int qtNormal;

    @Lob
    @Column(name = "RESUMO")
    private String resumo;

    public RelatorioPrioridade() {}

    public RelatorioPrioridade(int qtUrgente, int qtCritico, int qtAtencao, int qtNormal, String resumo) {
        this.qtUrgente = qtUrgente;
        this.qtCritico = qtCritico;
        this.qtAtencao = qtAtencao;
        this.qtNormal = qtNormal;
        this.resumo = resumo;
    }

    // Gere os Getters
    public Long getId() { return id; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public String getResumo() { return resumo; }
    public int getQtUrgente() { return qtUrgente; }
    public int getQtCritico() { return qtCritico; }
    public int getQtAtencao() { return qtAtencao; }
    public int getQtNormal() { return qtNormal; }
}