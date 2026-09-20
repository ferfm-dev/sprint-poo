package br.com.sprintpoo.models;

public class Pulverizacao extends IntervencaoOperacional {

    public static final String TIPO = "PULVERIZACAO";

    public Pulverizacao(String descricao, float extensaoKM) {
        super(descricao, extensaoKM);
    }

    public Pulverizacao(Long id, String descricao, float extensaoKM) {
        super(id, descricao, extensaoKM);
    }

    @Override
    public void executarServico() {
        System.out.println("[PULVERIZAÇÃO] Executando serviço em: " + descricao);
        System.out.println("  Extensão: " + extensaoKM + " KM | Método: Aplicação de Herbicida");
    }

    @Override
    public String getTipo() {
        return TIPO;
    }
}
