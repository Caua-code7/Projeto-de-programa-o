# Projeto-de-programa-o

Campo Minado em Java, rodando no terminal.

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

## Comandos do jogo

- `linha coluna` — revela uma célula. Linha é número, coluna é letra (ex: `3 B`)
- `F linha coluna` — marca/desmarca uma bandeira (ex: `F 3 B`)
- `Q` — sai do jogo

Tabuleiro padrão: 9x9 com 10 minas.
