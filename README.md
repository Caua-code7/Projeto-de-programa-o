# Campo Minado

Jogo do Campo Minado feito em Java, rodando no terminal.

## Como compilar e rodar

No PowerShell do Windows (não use `&&`, ele não é aceito por lá):

```
cd src
javac *.java
java Main
```

Ou tudo em uma linha, trocando `&&` por `;`:

```
cd src; javac *.java; java Main
```

## Funcionalidades

- Menu principal (jogar, ranking, instruções, ligar/desligar cores, sair)
- 3 níveis de dificuldade: Fácil (9x9), Médio (12x16) e Difícil (16x24)
- Números coloridos no terminal, como no Campo Minado original
- Cronômetro e contador de minas restantes durante a partida
- A primeira jogada nunca cai em uma mina
- Abertura em cascata das áreas vazias
- Ranking dos melhores tempos salvo no arquivo `ranking.txt`

## Comandos durante a partida

- `3 B` — revela a casa da linha 3, coluna B
- `F 3 B` — coloca ou tira uma bandeira nessa casa
- `S` — abandona a partida

## Símbolos

| Símbolo | Significado |
| --- | --- |
| `#` | casa ainda escondida |
| `0` a `8` | quantas minas existem nas casas ao redor |
| `F` | bandeira colocada pelo jogador |
| `*` | mina (aparece quando o jogador perde) |

## Organização das classes

| Classe | Responsabilidade |
| --- | --- |
| `Main` | Menu principal e controle das partidas |
| `Tabuleiro` | Matriz de células, sorteio das minas, abertura em cascata, vitória/derrota |
| `Celula` | Classe abstrata com o que toda célula tem em comum |
| `CelulaVazia` | Célula sem mina, guarda o número de minas vizinhas |
| `CelulaMina` | Célula com mina |
| `Dificuldade` | Tamanho do tabuleiro e quantidade de minas de cada nível |
| `Pontuacao` | Resultado de uma partida vencida (nome, tempo, dificuldade) |
| `Ranking` | Lista de pontuações e gravação/leitura do arquivo `ranking.txt` |
| `RankingException` | Erro ao gravar o arquivo do ranking |
| `Cores` | Códigos de cor usados no terminal |
