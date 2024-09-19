import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {
    private String novaCor = "";
    // adicionar baralho
    // adicionar baralho
    private Baralho baralho;
    // lista dos jogadores
    // lista dos jogadores
    private List<Jogador> jogadores;
    // boolean para ordem de jogada
    private boolean ordem; // true = horario, false = anti-horario
    // carta que esta na mesa
    // boolean para ordem de jogada
    private Cartas ultimaJogada;

    public Jogo(Baralho baralho, List<Jogador> jogadores, boolean ordem, Cartas ultimaJogada) {
        this.baralho = baralho;
        this.jogadores = new ArrayList<>();
        this.ordem = ordem;
        this.ultimaJogada = ultimaJogada;
    }

    // metodo para add novos jogadores
    public static String addJogadores(int cont) {

        Scanner leitor = new Scanner(System.in); // fechar esse leitor quando o jogo acabar
        System.out.println("Digite o nome do jogador " + cont);
        String jogador = leitor.next();
        return jogador;

    }

    // metodo para iniciar o jogo
    public void iniciarJogo() {
        // pergunta quantos jogadores vão jogar
        Scanner leitor = new Scanner(System.in);
        System.out.println("Quantos jogadores vão jogar?");
        int numJogadores = leitor.nextInt();
        for (int i = 0; i < numJogadores; i++) {

            String nomeJogador = addJogadores(i + 1); // cria o nome
            Mao mao = new Mao(baralho);
            Jogador jogador = new Jogador(nomeJogador, mao, false); // aqui cria o objeto jogador passando o nomeque
                                                                    // acabou de ser criado
            jogadores.add(jogador); // manda o jogador criado no loop pra lista de jogadores.
        }
        // embaralhar o baralho
        baralho.embaralha();
        // for loop para entregar as cartas iniciais para os jogadores
        for (Jogador jogador : jogadores) {
            jogador.setMao(jogador.getMao());
        }
        boolean ordem = true;
        // colocar a primeira carta na mesa usando o metodo entregador da classsse
        // baralho
        ultimaJogada = baralho.entregador();
        System.out.println("A carta na mesa é: " + ultimaJogada);
        rodarJogo();
    }

    // metodo para rodar o jogo com while loop
    public void rodarJogo() {
        Boolean bloqueado = false;
        
        while (true) {
            // for jogadores

            for (int i = 0; i < jogadores.size(); i++) { // aparentemente essa forma de for é melhor para aplicar os
                                                         // mais quatro e mais dois
                Jogador jogador = jogadores.get(i);
                jogador.mostrarMao();

                 if (bloqueado == true) {
                    System.out.println("Você foi bloqueado, passando a vez"); //precisa considerar que nao pode bloquear a mesma pessoa que jogou a carta
                    bloqueado = false;
                    continue;
                 }

                if (jogador.cartasAComprar > 0) { // faz comprar se nao tem valida
                    Scanner leitor = new Scanner(System.in);
                    System.out.println("Você não tem jogadas válidas, compre " + jogador.cartasAComprar + " cartas");
                    while (!leitor.nextLine().equalsIgnoreCase("comprar")) {
                        System.out.println("Digite 'comprar' meu deus cara burro");
                    }
                    compraDeCartas(jogador);
                    System.out.println("Essa é sua mão agora: ");
                    jogador.mostrarMao();
                    continue;
                }
                if (temJogadasValidas(jogador) == false) {

                    System.out.println("Você não tem cartas validas, compre uma digitando 'comprar'");

                    Scanner leitor = new Scanner(System.in);
                    while (!leitor.nextLine().equalsIgnoreCase("comprar")) {
                        System.out.println("Digite 'comprar' meu deus cara burro");
                    }
                    compraDeCartas(jogador);
                    System.out.println("Essa é sua mão agora: ");
                    jogador.mostrarMao();
                    continue;
                }

                Cartas cartaJogada = null;
                String cartaDesejada = "";
                String corDesejada = "";
                String valorDesejado = "";

                while (cartaJogada == null || (!cartaJogada.getColor().equals(ultimaJogada.getColor()) &&
                        !cartaJogada.getValue().equals(ultimaJogada.getValue()))) {
                    Scanner leitor = new Scanner(System.in);
                    System.out.println("Qual carta você quer jogar?");
                    cartaDesejada = leitor.nextLine().trim();

                    if (cartaDesejada.equalsIgnoreCase("Mais Quatro") || cartaDesejada.equalsIgnoreCase("Coringa")) {

                        cartaJogada = encontrarCarta(jogador, cartaDesejada);
                        if (cartaJogada != null) {
                            break;
                        }

                    }

                    String[] partes = cartaDesejada.split(" "); // divide a carta em cor e valor
                    if (partes.length == 2) {
                        corDesejada = partes[0];
                        valorDesejado = partes[1];
                    } else if (partes.length == 3) {
                        corDesejada = partes[0];
                        valorDesejado = partes[1] + " " + partes[2];
                    } else {
                        System.out.println("Formato inválido, digita certo porra");
                        continue;
                    }

                    cartaJogada = encontrarCarta(jogador, corDesejada + " " + valorDesejado);

                    // Verificar se a carta foi encontrada e é válida
                    if (cartaJogada != null &&
                            (cartaJogada.getColor().equalsIgnoreCase(novaCor) ||
                                cartaJogada.getColor().equalsIgnoreCase(ultimaJogada.getColor()) ||
                                    cartaJogada.getValue().equalsIgnoreCase(ultimaJogada.getValue()) ||
                                    cartaJogada.getValue().equalsIgnoreCase("Mais Quatro") ||
                                    cartaJogada.getValue().equalsIgnoreCase("Coringa"))) {
                        break;
                    } else {
                        System.out.println("Carta inválida, escolha outra.");
                        cartaJogada = null; // Reseta para que o loop continue pedindo uma carta válida
                    }

                }

                for (Cartas carta : jogador.getMao()) {
                    if (carta.getColor().equalsIgnoreCase(corDesejada)
                            && carta.getValue().equalsIgnoreCase(valorDesejado)) { // ve se a carta existe na mao
                        cartaJogada = carta;
                        break;
                    } else if (carta.getColor().isEmpty() && carta.getValue().equalsIgnoreCase(valorDesejado)) { // ve se  carta sem cor existe na mao
                        cartaJogada = carta;
                        break;
                    }
                }


                if (cartaJogada != null) {
                    jogador.getMao().remove(cartaJogada);
                    ultimaJogada = cartaJogada;
                    System.out.println("A carta na mesa é: " + ultimaJogada);

                    if (ultimaJogada.getValue().equalsIgnoreCase("Mais Quatro")) { // LOGICA DO MAIS QUATRO
                        Scanner leitorcores = new Scanner(System.in);
                        System.out.println("Escolha a cor da próxima carta");
                        novaCor = leitorcores.nextLine();
                        ultimaJogada.setColor(novaCor);
                        System.out.println("A cor escolhida é " + novaCor);
                        int proximoJogador = (i + 1) % jogadores.size();
                        Jogador proxJog = jogadores.get(proximoJogador);
                        proxJog.cartasAComprar += 4;
                        System.out.println(proxJog.getNome() + " vai comprar 4 cartas");
                        /*
                         * Scanner leitor2 = new Scanner(System.in);
                         * System.out.println("Escolha a cor da próxima carta");
                         */
                        continue;
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Coringa")) {
                        Scanner leitorcores = new Scanner(System.in);
                        System.out.println("Escolha a cor da próxima carta");
                        novaCor = leitorcores.nextLine();
                        ultimaJogada.setColor(novaCor);
                        System.out.println("A cor escolhida é " + novaCor);
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Block")) {
                        //if (ordem == true) {
                        //    i = (i + 1) % jogadores.size();
                        //} else {
                        //    i = (i - 1 + jogadores.size()) % jogadores.size();
                        //}
                        //System.out.println("O jogador " + jogadores.get(i).getNome() + " foi bloqueado");
                        bloqueado = true;
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Inverte")) {
                        ordem = !ordem;
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Mais Dois")) {
                        int proximoJogador = (i + 1) % jogadores.size();
                        Jogador proxJog = jogadores.get(proximoJogador);
                        proxJog.cartasAComprar += 2;
                        System.out.println(proxJog.getNome() + " vai comprar 2 cartas");
                    }
                } else {
                    System.out.println("Carta inválida, escolha outra carta");
                }
                if (jogador.getMao().isEmpty()) {
                    System.out.println(jogador.getNome() + " venceu o jogo!");
                    // leitor.close();

                    return;
                }
            }
        }

    }

    // mostrar mao do jogador
    // verificar se tem jogadas validas com o metodo temJogadasValidas, retorna true
    // ou false
    // se tiver, pergunta qual carta quer jogaar
    // se nao tiver, pede para comprar carta uma vez, e se for valida, já joga, se
    // nao for passa a vez
    // verificar se jogador tem zero cartas na mao
    // se tiver, encerrar loop com break

    public void compraDeCartas(Jogador jogador) { // botar o codigo que esta no metodo rodarjogo aqui para controlar
                                                  // melhor o que acontece
        for (int i = 0; i < jogador.cartasAComprar; i++) {
            jogador.getMao().add(baralho.entregador());
        }
        jogador.cartasAComprar = 0;

    }

    // metodo para verificar se ultima carta jogada é especial e implementar acao
    // dela
    public void ultimaJogadaEspecial(Cartas ultimaJogada, Jogador jogador) {
        // verifica +2 chama acao
        if (ultimaJogada.getValue().equals("Mais Dois")) {
            maisDois(jogador);
        } else if (ultimaJogada.getValue().equals("Mais Quatro")) {
            maisQuatro(jogador);
        } else if (ultimaJogada.getValue().equals("Inverte")) {
            inverter(jogador);
        } else if (ultimaJogada.getValue().equals("Block")) { // estava "Bloqueia", mas o valor da carta é "Block"
            bloquear(jogador);
        } else if (ultimaJogada.getValue().equals("Coringa")) {
            coringa(jogador);
        }
    }

    private void maisDois(Jogador jogador) {
        jogador.cartasAComprar += 2;
        // logica pensar depois
    }

    private void maisQuatro(Jogador jogador) {
        jogador.cartasAComprar += 4;
        // logica pensar depois
    }

    private void inverter(Jogador jogador) {
        ordem = !ordem;
        // logica pensar depois
    }

    private void bloquear(Jogador jogador) {
        if (ultimaJogada.getValue().equals("Block")) {

        }

        // hardset cartas validas para zero é uma boa ideia para implementar isso???
        // vejo depois que fizer o verificador de jogadas validas
    }

    private void coringa(Jogador jogador) {
        // logica pensar depois
        // print opcoes de cor e peditr para escolher com input
        // verificar se a cor escolhida é valida
        // colocar uma carta invisivel na mesa com a cor escolhida mas valor vazio para
        // o verificador de jogadas validas aceitar
    }

    // metodo para verificar se jogador atual tem jogadas validas
    public boolean temJogadasValidas(Jogador jogador) {
        for (Cartas carta : jogador.getMao()) {
            if (carta.getColor().equalsIgnoreCase(novaCor) || carta.getColor().equals(ultimaJogada.getColor()) || carta.getValue().equals(ultimaJogada.getValue())
                    || carta.getValue().equals("Coringa") || carta.getValue().equals("Mais Quatro")) {
                return true;

            }
        }
        // verifica se na mao tem carta com a cor da ultima jogada
        // verifica se na mao tem carta com o mesmo valor da ultima jogada
        // verifica se na mao tem carta especial
        // se tiver, retorna true
        // se nao tiver, retorna false
        jogador.cartasAComprar += 1;
        return false;
    }

    private Cartas encontrarCarta(Jogador jogador, String cartaDesejada) {
        for (Cartas carta : jogador.getMao()) {
            if (carta.getValue().equalsIgnoreCase(cartaDesejada)
                    || (carta.getColor() + " " + carta.getValue()).equalsIgnoreCase(cartaDesejada)) {
                return carta;
            }
        }
        return null;
    }

}