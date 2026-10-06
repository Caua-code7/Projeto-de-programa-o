public enum Dificuldade {

    FACIL("Facil", 9, 9, 10),
    MEDIO("Medio", 12, 16, 35),
    DIFICIL("Dificil", 16, 24, 80);

    private final String nome;
    private final int linhas;
    private final int colunas;
    private final int minas;

    Dificuldade(String nome, int linhas, int colunas, int minas) {
        this.nome = nome;
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
    }

    public String getNome() {
        return nome;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public int getMinas() {
        return minas;
    }
}
