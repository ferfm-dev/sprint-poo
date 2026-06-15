package br.com.sprintpoo.models;

public class RocadaMecanizada extends IntervencaoOperacional {

    public RocadaMecanizada(String descricao, float extensaoKM) {
        super(descricao, extensaoKM);
    }

    @Override
    public void executarServico() {
        System.out.println("[ROÇADA MECANIZADA] Executando serviço em: " + descricao);
        System.out.println("  Extensão: " + extensaoKM + " KM | Equipamento: Roçadeira Tratorizada");
    }
}