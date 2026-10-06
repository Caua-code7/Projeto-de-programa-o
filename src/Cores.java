import java.util.HashMap;
import java.util.Map;

public class Cores {

    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CIANO = "\u001B[36m";
    public static final String BRANCO = "\u001B[37m";
    public static final String CINZA = "\u001B[90m";

    private static final Map<Integer, String> CORES_DOS_NUMEROS = new HashMap<>();

    private static boolean ativadas = true;

    static {
        CORES_DOS_NUMEROS.put(1, AZUL);
        CORES_DOS_NUMEROS.put(2, VERDE);
        CORES_DOS_NUMEROS.put(3, VERMELHO);
        CORES_DOS_NUMEROS.put(4, MAGENTA);
        CORES_DOS_NUMEROS.put(5, AMARELO);
        CORES_DOS_NUMEROS.put(6, CIANO);
        CORES_DOS_NUMEROS.put(7, BRANCO);
        CORES_DOS_NUMEROS.put(8, CINZA);
    }

    public static String doNumero(int numero) {
        return CORES_DOS_NUMEROS.getOrDefault(numero, CINZA);
    }

    public static String pintar(String texto, String cor) {
        if (!ativadas) {
            return texto;
        }
        return cor + texto + RESET;
    }

    public static void alternar() {
        ativadas = !ativadas;
    }

    public static boolean estaoAtivadas() {
        return ativadas;
    }
}
