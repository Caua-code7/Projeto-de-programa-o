import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board(9, 9, 10);

        System.out.println("=== CAMPO MINADO ===");
        System.out.println("Comandos: linha coluna (revelar) | F linha coluna (marcar/desmarcar) | Q (sair)");
        System.out.println("Linha eh numero, coluna eh letra. Exemplo: 3 B");
        System.out.println();

        while (!board.isGameOver() && !board.isWin()) {
            System.out.println(board.render());
            System.out.print("Comando: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Saindo do jogo...");
                scanner.close();
                return;
            }

            String[] parts = input.split("\\s+");
            boolean flag = parts.length == 3 && parts[0].equalsIgnoreCase("F");

            if (!flag && parts.length != 2) {
                System.out.println("Comando invalido. Use: linha coluna  ou  F linha coluna\n");
                continue;
            }

            int rowPart = flag ? 1 : 0;
            int colPart = flag ? 2 : 1;

            try {
                int row = Integer.parseInt(parts[rowPart]) - 1;
                String colText = parts[colPart].toUpperCase();

                if (colText.length() != 1 || !Character.isLetter(colText.charAt(0))) {
                    System.out.println("A coluna deve ser uma letra (ex: B).\n");
                    continue;
                }

                int col = colText.charAt(0) - 'A';

                if (!board.isValid(row, col)) {
                    System.out.println("Posicao fora do tabuleiro.\n");
                    continue;
                }

                if (flag) {
                    board.toggleFlag(row, col);
                } else {
                    board.reveal(row, col);
                }
            } catch (NumberFormatException e) {
                System.out.println("A linha precisa ser um numero.\n");
            }
        }

        System.out.println(board.render());
        if (board.isWin()) {
            System.out.println("Parabens! Voce venceu!");
        } else {
            System.out.println("Voce pisou em uma mina. Fim de jogo!");
        }

        scanner.close();
    }
}
