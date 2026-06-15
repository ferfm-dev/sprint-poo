package br.com.sprintpoo.models;

public class Pulverizacao extends IntervencaoOperacional {

    public Pulverizacao(String descricao, float extensaoKM) {
        super(descricao, extensaoKM);
    }

    @Override
    public void executarServico() {
        System.out.println("[PULVERIZAÇÃO] Executando serviço em: " + descricao);
        System.out.println("  Extensão: " + extensaoKM + " KM | Método: Aplicação de Herbicida");
    }
}