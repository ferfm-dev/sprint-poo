import br.com.sprintpoo.models.TrechoRodovia;

public class Main {
    public static void main(String[] args) {
        TrechoRodovia BR116 = new TrechoRodovia("BR-166 KM 10 ao 15", 10, 15, 20);
        TrechoRodovia BR140 = new TrechoRodovia("BR140 KM 30 ao 45", 30, 45, 30);

        System.out.println("TESTE TRECHO 1");
        BR116.trechoInfo();
        System.out.println();
        BR116.registrarCrescimentoCM(5);
        BR116.rocarVegetacaoCM(3);
        System.out.println();
        BR116.trechoInfo();

        System.out.println("TESTE TRECHO 2");
        BR140.trechoInfo();
        System.out.println();
        BR140.registrarCrescimentoCM(1);
        BR140.rocarVegetacaoCM(10);
        System.out.println();
        BR140.trechoInfo();

    }
}