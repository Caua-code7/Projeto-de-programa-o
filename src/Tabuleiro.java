import java.util.Random;

public class Tabuleiro {

    private final int linhas;
    private final int colunas;
    private final int totalMinas;
    private final Celula[][] celulas;

    private boolean minasGeradas;
    private boolean venceu;
    private boolean perdeu;
    private int reveladas;

    public Tabuleiro(Dificuldade dificuldade) {
        this.linhas = dificuldade.getLinhas();
        this.colunas = dificuldade.getColunas();
        this.totalMinas = dificuldade.getMinas();
        this.celulas = new Celula[linhas][colunas];

        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                celulas[l][c] = new CelulaVazia();
            }
        }
    }

    public boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public void revelar(int linha, int coluna) {
        if (acabou()) {
            return;
        }

        if (!minasGeradas) {
            gerarMinas(linha, coluna);
        }

        Celula celula = celulas[linha][coluna];
        if (celula.estaMarcada() || celula.estaRevelada()) {
            return;
        }

        if (celula.ehMina()) {
            revelarTodasAsMinas();
            perdeu = true;
            return;
        }

        abrirEmCascata(linha, coluna);

        if (reveladas == linhas * colunas - totalMinas) {
            venceu = true;
        }
    }

    public void alternarMarcacao(int linha, int coluna) {
        celulas[linha][coluna].alternarMarcacao();
    }

    private void gerarMinas(int linhaSegura, int colunaSegura) {
        Random random = new Random();
        int colocadas = 0;

        while (colocadas < totalMinas) {
            int l = random.nextInt(linhas);
            int c = random.nextInt(colunas);

            boolean pertoDaPrimeiraJogada = Math.abs(l - linhaSegura) <= 1
                    && Math.abs(c - colunaSegura) <= 1;

            if (!pertoDaPrimeiraJogada && !celulas[l][c].ehMina()) {
                celulas[l][c] = new CelulaMina();
                colocadas++;
            }
        }

        calcularVizinhas();
        minasGeradas = true;
    }

    private void calcularVizinhas() {
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                if (!celulas[l][c].ehMina()) {
                    CelulaVazia vazia = (CelulaVazia) celulas[l][c];
                    vazia.setMinasVizinhas(contarMinasVizinhas(l, c));
                }
            }
        }
    }

    private int contarMinasVizinhas(int linha, int coluna) {
        int total = 0;
        for (int dl = -1; dl <= 1; dl++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dl == 0 && dc == 0) {
                    continue;
                }
                int l = linha + dl;
                int c = coluna + dc;
                if (posicaoValida(l, c) && celulas[l][c].ehMina()) {
                    total++;
                }
            }
        }
        return total;
    }

    private void abrirEmCascata(int linha, int coluna) {
        if (!posicaoValida(linha, coluna)) {
            return;
        }

        Celula celula = celulas[linha][coluna];
        if (celula.estaRevelada() || celula.estaMarcada() || celula.ehMina()) {
            return;
        }

        celula.revelar();
        reveladas++;

        CelulaVazia vazia = (CelulaVazia) celula;
        if (vazia.getMinasVizinhas() == 0) {
            for (int dl = -1; dl <= 1; dl++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dl != 0 || dc != 0) {
                        abrirEmCascata(linha + dl, coluna + dc);
                    }
                }
            }
        }
    }

    private void revelarTodasAsMinas() {
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                if (celulas[l][c].ehMina()) {
                    celulas[l][c].revelar();
                }
            }
        }
    }

    public int getMinasRestantes() {
        int marcadas = 0;
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                if (celulas[l][c].estaMarcada()) {
                    marcadas++;
                }
            }
        }
        return totalMinas - marcadas;
    }

    public boolean venceu() {
        return venceu;
    }

    public boolean perdeu() {
        return perdeu;
    }

    public boolean acabou() {
        return venceu || perdeu;
    }

    public String desenhar() {
        StringBuilder texto = new StringBuilder();

        texto.append("   ");
        for (int c = 0; c < colunas; c++) {
            texto.append(" ").append((char) ('A' + c));
        }
        texto.append("\n");

        for (int l = 0; l < linhas; l++) {
            texto.append(String.format("%2d ", l + 1));
            for (int c = 0; c < colunas; c++) {
                texto.append(" ").append(celulas[l][c].desenhar());
            }
            texto.append("\n");
        }

        return texto.toString();
    }
}
