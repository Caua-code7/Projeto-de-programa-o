import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner entrada = new Scanner(System.in);
    private static final Ranking ranking = new Ranking();

    public static void main(String[] args) {
        boolean rodando = true;

        while (rodando) {
            mostrarMenu();
            String opcao = entrada.nextLine().trim();

            if (opcao.equals("1")) {
                jogar();
            } else if (opcao.equals("2")) {
                mostrarRanking();
            } else if (opcao.equals("3")) {
                mostrarInstrucoes();
            } else if (opcao.equals("4")) {
                Cores.alternar();
                if (Cores.estaoAtivadas()) {
                    System.out.println("\nCores ativadas.\n");
                } else {
                    System.out.println("\nCores desativadas.\n");
                }
            } else if (opcao.equals("5")) {
                rodando = false;
                System.out.println("\nAte mais!");
            } else {
                System.out.println("\nOpcao invalida.\n");
            }
        }

        entrada.close();
    }

    private static void mostrarMenu() {
        System.out.println("===== CAMPO MINADO =====");
        System.out.println("1 - Jogar");
        System.out.println("2 - Ver ranking");
        System.out.println("3 - Como jogar");
        System.out.println("4 - Ligar/desligar cores");
        System.out.println("5 - Sair");
        System.out.print("Opcao: ");
    }

    private static void jogar() {
        Dificuldade dificuldade = escolherDificuldade();
        if (dificuldade == null) {
            return;
        }

        Tabuleiro tabuleiro = new Tabuleiro(dificuldade);
        long inicio = System.currentTimeMillis();

        while (!tabuleiro.acabou()) {
            System.out.println();
            System.out.println("Dificuldade: " + dificuldade.getNome()
                    + " | Minas restantes: " + tabuleiro.getMinasRestantes()
                    + " | Tempo: " + segundosDesde(inicio) + "s");
            System.out.println();
            System.out.println(tabuleiro.desenhar());
            System.out.print("Comando (ex: 3 B | F 3 B | S para sair): ");

            String comando = entrada.nextLine().trim();

            if (comando.equalsIgnoreCase("S")) {
                System.out.println("\nPartida abandonada.\n");
                return;
            }

            processarComando(comando, tabuleiro);
        }

        int tempo = segundosDesde(inicio);
        System.out.println();
        System.out.println(tabuleiro.desenhar());

        if (tabuleiro.venceu()) {
            System.out.println("Parabens! Voce venceu em " + tempo + " segundos!");
            System.out.print("Digite seu nome para o ranking: ");
            String nome = entrada.nextLine().trim();
            try {
                ranking.adicionar(new Pontuacao(nome, tempo, dificuldade.getNome()));
                System.out.println("Pontuacao salva no ranking!\n");
            } catch (RankingException e) {
                System.out.println("Aviso: " + e.getMessage() + "\n");
            }
        } else {
            System.out.println("Voce pisou em uma mina! Fim de jogo.\n");
        }
    }

    private static void processarComando(String comando, Tabuleiro tabuleiro) {
        String[] partes = comando.split("\\s+");

        boolean marcar = partes.length == 3 && partes[0].equalsIgnoreCase("F");

        if (!marcar && partes.length != 2) {
            System.out.println("\nComando invalido. Use: 3 B (revelar) ou F 3 B (marcar)");
            return;
        }

        int posicaoLinha = marcar ? 1 : 0;
        int posicaoColuna = marcar ? 2 : 1;

        int linha;
        try {
            linha = Integer.parseInt(partes[posicaoLinha]) - 1;
        } catch (NumberFormatException e) {
            System.out.println("\nA linha precisa ser um numero.");
            return;
        }

        String textoColuna = partes[posicaoColuna].toUpperCase();
        if (textoColuna.length() != 1 || !Character.isLetter(textoColuna.charAt(0))) {
            System.out.println("\nA coluna precisa ser uma letra (ex: B).");
            return;
        }
        int coluna = textoColuna.charAt(0) - 'A';

        if (!tabuleiro.posicaoValida(linha, coluna)) {
            System.out.println("\nPosicao fora do tabuleiro.");
            return;
        }

        if (marcar) {
            tabuleiro.alternarMarcacao(linha, coluna);
        } else {
            tabuleiro.revelar(linha, coluna);
        }
    }

    private static Dificuldade escolherDificuldade() {
        Dificuldade[] opcoes = Dificuldade.values();

        System.out.println("\nEscolha a dificuldade:");
        for (int i = 0; i < opcoes.length; i++) {
            Dificuldade d = opcoes[i];
            System.out.println((i + 1) + " - " + d.getNome() + " ("
                    + d.getLinhas() + "x" + d.getColunas() + ", " + d.getMinas() + " minas)");
        }
        System.out.print("Opcao: ");

        String escolha = entrada.nextLine().trim();
        try {
            int indice = Integer.parseInt(escolha) - 1;
            if (indice >= 0 && indice < opcoes.length) {
                return opcoes[indice];
            }
        } catch (NumberFormatException e) {
            System.out.println("\nDificuldade invalida.\n");
            return null;
        }

        System.out.println("\nDificuldade invalida.\n");
        return null;
    }

    private static void mostrarRanking() {
        System.out.println("\n===== MELHORES TEMPOS =====");

        for (Dificuldade dificuldade : Dificuldade.values()) {
            String nomeDificuldade = dificuldade.getNome();
            System.out.println("\n" + nomeDificuldade + ":");

            List<Pontuacao> melhores = ranking.melhores(nomeDificuldade, 5);
            if (melhores.isEmpty()) {
                System.out.println("  (nenhuma partida registrada)");
            } else {
                for (int i = 0; i < melhores.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + melhores.get(i));
                }
            }
        }
        System.out.println();
    }

    private static void mostrarInstrucoes() {
        System.out.println("\n===== COMO JOGAR =====");
        System.out.println("O objetivo e revelar todas as casas que nao tem mina.");
        System.out.println();
        System.out.println("Simbolos:");
        System.out.println("  #       casa ainda escondida");
        System.out.println("  0 a 8   quantas minas existem nas casas ao redor");
        System.out.println("  F       bandeira que voce colocou");
        System.out.println("  *       mina (aparece quando voce perde)");
        System.out.println();
        System.out.println("Comandos durante a partida:");
        System.out.println("  3 B     revela a casa da linha 3, coluna B");
        System.out.println("  F 3 B   coloca ou tira uma bandeira nessa casa");
        System.out.println("  S       abandona a partida");
        System.out.println();
        System.out.println("Dica: quando a casa revelada tem 0 minas por perto,");
        System.out.println("o jogo abre automaticamente toda a area vizinha.");
        System.out.println();
    }

    private static int segundosDesde(long inicio) {
        return (int) ((System.currentTimeMillis() - inicio) / 1000);
    }
}
