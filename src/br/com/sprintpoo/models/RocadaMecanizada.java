package br.com.sprintpoo.models;

public class RocadaMecanizada extends IntervencaoOperacional {

    public static final String TIPO = "ROCADA";

    public RocadaMecanizada(String descricao, float extensaoKM) {
        super(descricao, extensaoKM);
    }

    public RocadaMecanizada(Long id, String descricao, float extensaoKM) {
        super(id, descricao, extensaoKM);
    }

    @Override
    public void executarServico() {
        System.out.println("[ROÇADA MECANIZADA] Executando serviço em: " + descricao);
        System.out.println("  Extensão: " + extensaoKM + " KM | Equipamento: Roçadeira Tratorizada");
    }

    @Override
    public String getTipo() {
        return TIPO;
    }
}
