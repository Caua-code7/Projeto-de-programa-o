public class Pontuacao implements Comparable<Pontuacao> {

    private final String nome;
    private final int tempoSegundos;
    private final String dificuldade;

    public Pontuacao(String nome, int tempoSegundos, String dificuldade) {
        String nomeLimpo = nome.replace(";", "").trim();
        if (nomeLimpo.isEmpty()) {
            nomeLimpo = "Anonimo";
        }
        this.nome = nomeLimpo;
        this.tempoSegundos = tempoSegundos;
        this.dificuldade = dificuldade;
    }

    public String getNome() {
        return nome;
    }

    public int getTempoSegundos() {
        return tempoSegundos;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    @Override
    public int compareTo(Pontuacao outra) {
        return this.tempoSegundos - outra.tempoSegundos;
    }

    public String paraLinhaDeArquivo() {
        return nome + ";" + tempoSegundos + ";" + dificuldade;
    }

    public static Pontuacao lerDeLinhaDeArquivo(String linha) {
        String[] partes = linha.split(";");
        if (partes.length != 3) {
            return null;
        }
        try {
            int tempo = Integer.parseInt(partes[1]);
            return new Pontuacao(partes[0], tempo, partes[2]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return nome + " - " + tempoSegundos + "s";
    }
}
