# UNO em Java

Bem-vindo ao projeto **UNO em Java**! Este é um jogo de cartas inspirado no clássico UNO, desenvolvido em Java com uma abordagem orientada a objetos. O jogo roda no console e conta com jogadores humanos e IA, implementando as regras e efeitos especiais do jogo.

---

## Visão Geral

Este projeto simula uma partida de UNO, onde:
- **Baralho:** Um conjunto de cartas é criado, embaralhado e distribuído entre os jogadores.
- **Jogadores:** Podem ser controlados pelo usuário ou pela IA. Cada jogador recebe uma mão inicial de 7 cartas.
- **Regras do Jogo:** São implementadas as jogadas válidas (mesma cor ou mesmo número/ação) e os efeitos das cartas especiais, como:
  - **Block**
  - **Inverte**
  - **Mais Dois**
  - **Coringa**
  - **Mais Quatro**

O jogo prossegue em turnos até que um dos jogadores fique sem cartas, declarando-o vencedor.

---

## Estrutura do Projeto

- **App.java**  
  Ponto de entrada do jogo. Inicializa o baralho e o jogo.

- **Baralho.java**  
  Responsável por criar e gerenciar o baralho, incluindo o método de embaralhar e distribuir as cartas.

- **BaralhoBuilder.java**  
  Monta o baralho completo, incluindo as cartas numeradas e as especiais.

- **Cartas.java**  
  Define a estrutura de uma carta com atributos de cor e valor.

- **Jogador.java**  
  Representa um jogador (humano ou IA) e gerencia a mão de cartas.

- **Jogo.java**  
  Contém a lógica principal do jogo: turnos, jogadas, efeitos das cartas e condições de vitória.

- **Mao.java**  
  Responsável por distribuir as cartas iniciais aos jogadores.

---

## Requisitos

- **Java JDK 8** (ou superior)
- Terminal/Prompt de Comando para execução

---

## Como Jogar

1. **Configuração Inicial:**  
   Ao iniciar, o jogo perguntará quantos jogadores participarão da partida.

2. **Cadastro dos Jogadores:**  
   Para cada jogador, insira o nome e escolha se ele será controlado pela IA ou se é um jogador humano (digite `S` para IA e `N` para humano).

3. **Distribuição das Cartas:**  
   Cada jogador receberá uma mão de 7 cartas. A carta inicial é sorteada e exibida na mesa.

4. **Realizando as Jogadas:**  
   - **Jogadas Válidas:** Uma carta pode ser jogada se tiver a mesma cor ou o mesmo valor da carta na mesa, ou se for uma carta especial (Coringa ou Mais Quatro).
   - **Comprando Cartas:** Caso o jogador não possua jogadas válidas, ele deverá comprar as cartas indicadas.
   - **Efeitos Especiais:** Cartas como **Block**, **Inverte**, **Mais Dois**, **Coringa** e **Mais Quatro** alteram o fluxo do jogo, exigindo ações especiais (como mudar a cor ou fazer o próximo jogador comprar cartas).

5. **Final do Jogo:**  
   O jogo termina quando um jogador fica sem cartas. Este jogador é declarado o vencedor.

---

## Exemplo de Execução

```plaintext
Quantos jogadores vão jogar?
2
Digite o nome do jogador 1: Alice
Esse jogador vai ser IA? S para sim, N para não: N
Digite o nome do jogador 2: Bob
Esse jogador vai ser IA? S para sim, N para não: S
A carta na mesa é: Vermelho Cinco
Alice tem as seguintes cartas na mão:
- Vermelho Zero
- Verde Dois
- Azul Mais Dois
...
```

---

## Contribuições

Contribuições são muito bem-vindas! Se você deseja melhorar este projeto:
- Abra uma **issue** para discutir novas ideias ou relatar bugs.
- Envie um **pull request** com suas melhorias.
