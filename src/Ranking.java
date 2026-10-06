import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ranking {

    private static final String ARQUIVO = "ranking.txt";

    private final List<Pontuacao> pontuacoes = new ArrayList<>();

    public Ranking() {
        carregar();
    }

    public void adicionar(Pontuacao pontuacao) {
        pontuacoes.add(pontuacao);
        salvar();
    }

    public List<Pontuacao> melhores(String dificuldade, int quantidade) {
        List<Pontuacao> filtradas = new ArrayList<>();

        for (Pontuacao pontuacao : pontuacoes) {
            if (pontuacao.getDificuldade().equalsIgnoreCase(dificuldade)) {
                filtradas.add(pontuacao);
            }
        }

        Collections.sort(filtradas);

        List<Pontuacao> melhores = new ArrayList<>();
        for (Pontuacao pontuacao : filtradas) {
            if (melhores.size() == quantidade) {
                break;
            }
            melhores.add(pontuacao);
        }
        return melhores;
    }

    private void carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha = leitor.readLine();
            while (linha != null) {
                Pontuacao pontuacao = Pontuacao.lerDeLinhaDeArquivo(linha);
                if (pontuacao != null) {
                    pontuacoes.add(pontuacao);
                }
                linha = leitor.readLine();
            }
        } catch (IOException e) {
            System.out.println("Nao foi possivel ler o ranking: " + e.getMessage());
        }
    }

    private void salvar() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Pontuacao pontuacao : pontuacoes) {
                escritor.println(pontuacao.paraLinhaDeArquivo());
            }
        } catch (IOException e) {
            throw new RankingException("Nao foi possivel salvar o ranking", e);
        }
    }
}
