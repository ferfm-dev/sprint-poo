package br.com.sprintpoo.models;

public class TrechoRodovia implements MonitoravelViaIoT {
    private Long id; // NOVO: chave primária no banco (null enquanto não persistido)
    private String titulo;
    private float quilometroInicial;
    private float quilometroFinal;
    private float nivelVegetacaoCM;
    private boolean possuiIoT;

    public TrechoRodovia(String titulo, float quilometroInicial, float quilometroFinal, float nivelVegetacaoCM) {
        setTitulo(titulo);
        setQuilometroInicial(quilometroInicial);
        setQuilometroFinal(quilometroFinal);
        setNivelVegetacaoCM(nivelVegetacaoCM);
        this.possuiIoT = false;
    }

    public TrechoRodovia(String titulo, float quilometroInicial, float quilometroFinal, float nivelVegetacaoCM, boolean possuiIoT) {
        this(titulo, quilometroInicial, quilometroFinal, nivelVegetacaoCM);
        this.possuiIoT = possuiIoT;
    }

    // Construtor usado pelo DAO ao reconstruir um objeto vindo do banco (já com ID)
    public TrechoRodovia(Long id, String titulo, float quilometroInicial, float quilometroFinal, float nivelVegetacaoCM, boolean possuiIoT) {
        this(titulo, quilometroInicial, quilometroFinal, nivelVegetacaoCM, possuiIoT);
        this.id = id;
    }

    // --- Interface MonitoravelViaIoT ---
    @Override
    public void transmitirDadosSensor() {
        if (!possuiIoT) {
            System.out.println("[IoT] Trecho '" + titulo + "' não possui sensor instalado.");
            return;
        }
        // Simula leitura de sensor: crescimento automático entre 1 e 8 cm
        float crescimentoDetectado = 1 + (float)(Math.random() * 7);
        crescimentoDetectado = Math.round(crescimentoDetectado * 10) / 10f;
        System.out.println("[IoT] Sensor do trecho '" + titulo + "' transmitiu: +" + crescimentoDetectado + " cm detectados.");
        this.nivelVegetacaoCM += crescimentoDetectado;
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) {
        if (!titulo.isEmpty()) {
            this.titulo = titulo;
            return;
        }
        System.out.println("Nome inválido.");
    }

    public float getQuilometroInicial() { return quilometroInicial; }

    public void setQuilometroInicial(float quilometroInicial) {
        if (quilometroInicial > 0) {
            this.quilometroInicial = quilometroInicial;
            return;
        }
        System.out.println("Valor inválido.");
    }

    public float getQuilometroFinal() { return quilometroFinal; }

    public void setQuilometroFinal(float quilometroFinal) {
        if (quilometroFinal > 0) {
            this.quilometroFinal = quilometroFinal;
            return;
        }
        System.out.println("Valor inválido.");
    }

    public float getNivelVegetacaoCM() { return nivelVegetacaoCM; }

    public void setNivelVegetacaoCM(float nivelVegetacaoCM) {
        if (nivelVegetacaoCM > 0) {
            this.nivelVegetacaoCM = nivelVegetacaoCM;
            return;
        }
        System.out.println("Valor inválido.");
    }

    public boolean isPossuiIoT() { return possuiIoT; }
    public void setPossuiIoT(boolean possuiIoT) { this.possuiIoT = possuiIoT; }

    public void registrarCrescimentoCM(float taxaCM) {
        if (taxaCM > 0) {
            this.nivelVegetacaoCM += taxaCM;
            System.out.println("Nível vegetação atual: " + this.nivelVegetacaoCM);
            return;
        }
        System.out.println("Valor inválido.");
    }

    public void rocarVegetacaoCM(float taxaCM) {
        if (taxaCM > 0 && taxaCM <= this.nivelVegetacaoCM) {
            this.nivelVegetacaoCM -= taxaCM;
            System.out.println("Nível vegetação atual: " + this.nivelVegetacaoCM);
            return;
        }
        System.out.println("Valor inválido.");
    }

    public void trechoInfo() {
        System.out.println("=========================");
        System.out.println("Informações do Trecho: " + getTitulo());
        System.out.println("-------------------------");
        System.out.println("Quilômetro Inicial: " + getQuilometroInicial());
        System.out.println("-------------------------");
        System.out.println("Quilômetro Final: " + getQuilometroFinal());
        System.out.println("-------------------------");
        System.out.println("Nível Vegetação: " + getNivelVegetacaoCM() + " cm");
        System.out.println("  Sensor IoT: " + (possuiIoT ? "Instalado" : "Não instalado"));
        System.out.println("=========================");
    }
}
