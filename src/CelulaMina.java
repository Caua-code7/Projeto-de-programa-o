public class CelulaMina extends Celula {

    @Override
    public boolean ehMina() {
        return true;
    }

    @Override
    public char getSimbolo() {
        return '*';
    }

    @Override
    public String getCor() {
        return Cores.VERMELHO;
    }
}
