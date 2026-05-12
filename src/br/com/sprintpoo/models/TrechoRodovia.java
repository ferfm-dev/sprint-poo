package br.com.sprintpoo.models;

public class TrechoRodovia {
    private String titulo;
    private float quilometroInicial;
    private float quilometroFinal;
    private float nivelVegetacaoCM;

    public TrechoRodovia(String titulo, float quilometroInicial, float quilometroFinal, float nivelVegetacaoCM) {
        setTitulo(titulo);
        setQuilometroInicial(quilometroInicial);
        setQuilometroFinal(quilometroFinal);
        setNivelVegetacaoCM(nivelVegetacaoCM);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(!titulo.isEmpty()){
        this.titulo = titulo;
        return;
        }
        System.out.println("Nome inválido.");
    }

    public float getQuilometroInicial() {
        return quilometroInicial;
    }

    public void setQuilometroInicial(float quilometroInicial) {
        if (quilometroInicial > 0) {
        this.quilometroInicial = quilometroInicial;
        return;
        }
        System.out.println("Valor inválido.");
    }

    public float getQuilometroFinal() {
        return quilometroFinal;
    }

    public void setQuilometroFinal(float quilometroFinal) {
        if (quilometroFinal > 0) {
        this.quilometroFinal = quilometroFinal;
        return;
        }
        System.out.println("Valor inválido.");
    }

    public float getNivelVegetacaoCM() {
        return nivelVegetacaoCM;
    }

    public void setNivelVegetacaoCM(float nivelVegetacaoCM) {
        if (nivelVegetacaoCM > 0) {
        this.nivelVegetacaoCM = nivelVegetacaoCM;
        return;
        }
        System.out.println("Valor inválido.");
    }

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
        System.out.println("Nível Vegetação: " + getNivelVegetacaoCM());
        System.out.println("-------------------------");
        System.out.println("=========================");
    }
}