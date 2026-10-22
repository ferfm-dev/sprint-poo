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

    @Column(name = "POSSUI_IOT", length = 1)
    private Boolean possuiIoT = false;

    public TrechoRodovia() {}

    // Getters e Setters
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

    public Boolean getPossuiIoT() { return possuiIoT; }
    public void setPossuiIoT(Boolean possuiIoT) { this.possuiIoT = possuiIoT; }
}