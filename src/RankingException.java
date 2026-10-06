public class RankingException extends RuntimeException {

    public RankingException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
