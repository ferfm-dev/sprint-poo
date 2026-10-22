package br.com.sprintpoo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_TRECHO_RODOVIA")
public class TrechoRodovia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trecho_seq")
    @SequenceGenerator(name = "trecho_seq", sequenceName = "SEQ_TRECHO", allocationSize = 1)
    private Long id;

    @Column(name = "TITULO", nullable = false, length = 150)
    private String titulo;

    @Column(name = "KM_INICIAL", nullable = false)
    private float quilometroInicial;

    @Column(name = "KM_FINAL", nullable = false)
    private float quilometroFinal;

    @Column(name = "NIVEL_VEGETACAO_CM", nullable = false)
    private float nivelVegetacaoCM;

    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(name = "POSSUI_IOT", nullable = false, length = 1)
    private boolean possuiIoT;

    public TrechoRodovia() {}

    // Gere os Getters e Setters na sua IDE (Alt+Insert ou clique direito -> Generate)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public float getQuilometroInicial() { return quilometroInicial; }
    public void setQuilometroInicial(float quilometroInicial) { this.quilometroInicial = quilometroInicial; }
    public float getQuilometroFinal() { return quilometroFinal; }
    public void setQuilometroFinal(float quilometroFinal) { this.quilometroFinal = quilometroFinal; }
    public float getNivelVegetacaoCM() { return nivelVegetacaoCM; }
    public void setNivelVegetacaoCM(float nivelVegetacaoCM) { this.nivelVegetacaoCM = nivelVegetacaoCM; }
    public boolean isPossuiIoT() { return possuiIoT; }
    public void setPossuiIoT(boolean possuiIoT) { this.possuiIoT = possuiIoT; }
}