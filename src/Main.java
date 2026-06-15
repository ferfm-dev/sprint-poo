import br.com.sprintpoo.models.*;

public class Main {
    public static void main(String[] args) {

        // =========================================================
        // BLOCO 1: Classes Abstratas
        // Demonstra que IntervencaoOperacional não pode ser instanciada
        // diretamente, apenas suas filhas concretas.
        // =========================================================
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║     BLOCO 1 - INTERVENÇÕES OPERACIONAIS   ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        IntervencaoOperacional intervencao1 = new RocadaMecanizada("BR-116 KM 10 ao 15", 5);
        IntervencaoOperacional intervencao2 = new Pulverizacao("BR-140 KM 30 ao 45", 15);

        intervencao1.executarServico();
        System.out.println();
        intervencao2.executarServico();

        // =========================================================
        // BLOCO 2: Interface MonitoravelViaIoT
        // Trechos com IoT atualizam seu nível de vegetação
        // automaticamente via sensor, sem inspeção visual.
        // =========================================================
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║     BLOCO 2 - MONITORAMENTO VIA IoT       ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        TrechoRodovia trechoComIoT    = new TrechoRodovia("BR-116 KM 10 ao 15", 10, 15, 20, true);
        TrechoRodovia trechoSemIoT   = new TrechoRodovia("BR-116 KM 20 ao 25", 20, 25, 18, false);

        // Polimorfismo via interface: referência do tipo da interface
        MonitoravelViaIoT sensor = trechoComIoT;

        trechoComIoT.trechoInfo();
        System.out.println();
        System.out.println(">> Transmissão automática do sensor:");
        sensor.transmitirDadosSensor();   // atualiza sem inspeção visual
        trechoSemIoT.transmitirDadosSensor(); // trecho sem sensor
        System.out.println();
        System.out.println(">> Nível de vegetação após transmissão do sensor:");
        trechoComIoT.trechoInfo();

        // =========================================================
        // BLOCO 3: Relatório de Prioridade
        // Varre um array de trechos e indica o tipo de intervenção
        // necessária com base no nível de vegetação.
        //
        // Regras de prioridade:
        //   >= 40 cm → Roçada Mecanizada (urgente)
        //   >= 25 cm → Pulverização
        //   < 25 cm  → Sem intervenção necessária
        // =========================================================
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║       BLOCO 3 - RELATÓRIO DE PRIORIDADE   ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        TrechoRodovia[] trechos = {
                new TrechoRodovia("BR-116 KM 10 ao 15", 10, 15, 20, true),
                new TrechoRodovia("BR-116 KM 20 ao 25", 20, 25, 45, false),
                new TrechoRodovia("BR-116 KM 30 ao 40", 30, 40, 30, true),
                new TrechoRodovia("BR-140 KM 05 ao 12", 5,  12, 10, false),
                new TrechoRodovia("BR-140 KM 30 ao 45", 30, 45, 50, true),
        };

        // Atualiza trechos com IoT via sensor antes de gerar o relatório
        System.out.println(">> Atualizando trechos com sensor IoT...");
        for (TrechoRodovia trecho : trechos) {
            if (trecho.isPossuiIoT()) {
                trecho.transmitirDadosSensor();
            }
        }

        // Gera o relatório varrendo o array
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║         RELATÓRIO DE PRIORIDADE           ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        for (TrechoRodovia trecho : trechos) {
            float nivel = trecho.getNivelVegetacaoCM();
            String prioridade;
            String tipoIntervencao;

            if (nivel >= 40) {
                prioridade      = "URGENTE";
                tipoIntervencao = "Roçada Mecanizada";
            } else if (nivel >= 25) {
                prioridade      = "MODERADO";
                tipoIntervencao = "Pulverização";
            } else {
                prioridade      = "OK";
                tipoIntervencao = "Sem intervenção necessária";
            }

            System.out.println("-----------------------------------------");
            System.out.println("Trecho : " + trecho.getTitulo());
            System.out.println("Nível  : " + nivel + " cm");
            System.out.println("Status : " + prioridade);
            System.out.println("Ação   : " + tipoIntervencao);
        }
        System.out.println("-----------------------------------------");
    }
}