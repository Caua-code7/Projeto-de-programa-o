public abstract class Celula {

    private boolean revelada;
    private boolean marcada;

    public abstract boolean ehMina();

    public abstract char getSimbolo();

    public abstract String getCor();

    public void revelar() {
        if (!marcada) {
            revelada = true;
        }
    }

    public boolean estaRevelada() {
        return revelada;
    }

    public void alternarMarcacao() {
        if (!revelada) {
            marcada = !marcada;
        }
    }

    public boolean estaMarcada() {
        return marcada;
    }

    public String desenhar() {
        if (marcada) {
            return Cores.pintar("F", Cores.AMARELO);
        }
        if (!revelada) {
            return Cores.pintar("#", Cores.CINZA);
        }
        return Cores.pintar(String.valueOf(getSimbolo()), getCor());
    }
}
