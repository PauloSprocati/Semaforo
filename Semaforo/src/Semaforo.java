
import java.util.Scanner;

public class Semaforo {
    public static Scanner sc = new Scanner(System.in);
    public static String[] coresSemaforo = new String[3];
    public static void main(String[] args) {
        System.out.println("Bem Vindo ao Sistema do Semáforo!");
        var primeiraCor = corSemaforo("Digite a primeira cor do Semáforo: ");
        coresSemaforo[0] = primeiraCor;
        var segundaCor = corSemaforo("Digite a segunda cor do Semáforo: ");
        coresSemaforo[1] = segundaCor;
        var terceiraCor = corSemaforo("Digite a terceira cor do Semáforo: ");
        coresSemaforo[2] = terceiraCor;

        var tempoPrimeiraCor = determinaTempo("Quantos segundos deseja que a cor " + coresSemaforo[0] + " fique ativa no semáforo?: ");
        var tempoSegundaCor = determinaTempo("Quantos segundos deseja que a cor " + coresSemaforo[1] + " fique ativa no semáforo?: ");
        var tempoTerceiraCor = determinaTempo("Quantos segundos deseja que a cor " + coresSemaforo[2] + " fique ativa no semáforo?: ");

        System.out.println("Qual das cores você deseja que seja o alerta?");
        System.out.println("1. " + primeiraCor);
        System.out.println("2. " + segundaCor);
        System.out.println("3. " + terceiraCor);
        var corAlerta = verificaAlerta();
        var tempoAlerta = determinaTempoAlerta(corAlerta, tempoPrimeiraCor, tempoSegundaCor, tempoTerceiraCor);
        sc.nextLine();
        var iniciaAlerta = determinaTempo("A cada quantos ciclos o alerta deve ser emitido?: ");
        var fechaAlerta = determinaTempo("Quantos ciclos o alerta irá durar?: ");

        var ciclo = 0;
        var iniciaSemaforo = validaSemaforo();
        while (iniciaSemaforo) {
            contaSemaforo(tempoPrimeiraCor, primeiraCor);
            contaSemaforo(tempoSegundaCor, segundaCor);
            contaSemaforo(tempoTerceiraCor, terceiraCor);
            ciclo = ciclo + 1;
            ContaAlerta(ciclo, iniciaAlerta, fechaAlerta, tempoAlerta, corAlerta);
        }
    }

    private static void ContaAlerta(int ciclo, int iniciaAlerta, int fechaAlerta, int tempoAlerta, String corAlerta) {
        if(ciclo % iniciaAlerta == 0) {
            var cicloAlerta = 0;
            while (cicloAlerta < fechaAlerta) {
                contaSemaforo(tempoAlerta, corAlerta);
                cicloAlerta++;
            }
        }
    }

    private static int determinaTempoAlerta(String corAlerta, int tempoPrimeiraCor, int tempoSegundaCor, int tempoTerceiraCor) {
        if (corAlerta.equalsIgnoreCase(coresSemaforo[0])) {
            return tempoPrimeiraCor;
        }else if (corAlerta.equalsIgnoreCase(coresSemaforo[1])) {
            return tempoSegundaCor;
        }else {
            return tempoTerceiraCor;
        }
    }

    private static String verificaAlerta() {
        var corAlerta = sc.nextInt();
        while (true) {
            switch (corAlerta) {
                case 1:
                    return coresSemaforo[0];
                case 2:
                    return coresSemaforo[1];
                case 3:
                    return coresSemaforo[2];
                default:
                    System.out.println("Digite uma cor válida!");
                    corAlerta = sc.nextInt();
            }
        }

    }

    private static boolean validaSemaforo() {
        System.out.print("Deseja Iniciar o semáforo agora? [(S)im ou (N)ao]: ");
        var iniciaSemaforo = sc.nextLine();
        do {
            if("S".equalsIgnoreCase(iniciaSemaforo) || "Sim".equalsIgnoreCase(iniciaSemaforo)) {
                System.out.println("Iniciando Semáforo.");
                return true;
            }else if("N".equalsIgnoreCase(iniciaSemaforo) || "Nao".equalsIgnoreCase(iniciaSemaforo)) {
                System.out.println("Semáforo fechado.");
                System.exit(-1);
            }else {
                System.out.print("Digite uma informação válida! [(S)im ou (N)ao]: ");
                iniciaSemaforo = sc.nextLine();
            }
        }while (true);
    }

    private static void contaSemaforo(int tempo, String cor) {
        try {
            var tempoTotal = tempo * 1000;
            System.out.println(cor);
            Thread.sleep(tempoTotal);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static int determinaTempo(String  label) {
        var tempo = verificaTempo(label);
        while (tempo <= 0) {
            System.out.println("Informação inválida. Digite os segundos novamente.");
            tempo = verificaTempo(label);
        }
        return tempo;
    }

    private static int verificaTempo(String label) {
        var tempo = 0;
        boolean confirma;
        do {
            try {
                System.out.print(label);
                var textoLido = sc.nextLine();
                tempo = Integer.parseInt(textoLido);
                confirma = false;
            } catch (NumberFormatException ex) {
                System.out.println("Informação invalida. Digite os segundos novamente.");
                confirma = true;
            }
        }while (confirma);
        return tempo;
    }

    private static String corSemaforo(String label) {
        System.out.print(label);
        var cor = sc.nextLine();
        var continua = true;
        while (continua) {
            continua = false;
            boolean isNumeric =  cor.matches("[0-9]+");
            if(cor.isBlank() || isNumeric) {
                System.out.print("Digite a cor novamente: ");
                cor = sc.nextLine();
                continua = true;
            }
        }

        do {
            continua = false;
            for (int i = 0; i < 3; i++) {
                if (coresSemaforo[i] != null && coresSemaforo[i].equalsIgnoreCase(cor)) {
                    System.out.println("Essa cor já foi utilizada. Digite novamente.");
                    System.out.println(label);
                    cor = sc.nextLine();
                    continua = true;
                    break;
                }
            }
        } while (continua);

        return cor;
    }
}
