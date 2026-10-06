public class CelulaVazia extends Celula {

    private int minasVizinhas;

    @Override
    public boolean ehMina() {
        return false;
    }

    @Override
    public char getSimbolo() {
        return (char) ('0' + minasVizinhas);
    }

    @Override
    public String getCor() {
        return Cores.doNumero(minasVizinhas);
    }

    public void setMinasVizinhas(int minasVizinhas) {
        this.minasVizinhas = minasVizinhas;
    }

    public int getMinasVizinhas() {
        return minasVizinhas;
    }
}
