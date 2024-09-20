import java.util.ArrayList;
import java.util.Collections;
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

            String nomeJogador = addJogadores(i + 1); //ria o nome
            Mao mao = new Mao(baralho);
            Jogador jogador = new Jogador(nomeJogador, mao, false); //aqui cria o objeto jogador passando o nome que acabou de ser criado
            jogadores.add(jogador); 
        }
        baralho.embaralha();
        // for loop para entregar as cartas iniciais para os jogadores
        for (Jogador jogador : jogadores) {
            jogador.setMao(jogador.getMao());
        }
        boolean ordem = true;
        // colocar a primeira carta na mesa usando o metodo entregador da classsse
        // baralho
        
        ultimaJogada = baralho.entregador();
        while(ultimaJogada.getValue().equalsIgnoreCase("Mais Quatro") || ultimaJogada.getValue().equalsIgnoreCase("Coringa")) {
            baralho.getBaralho().add(ultimaJogada);
            ultimaJogada = baralho.entregador();
            System.out.println("Acabei de evitar que a carta inicial fosse um +4 ou Coringa"); 
        }
        System.out.println("A carta na mesa é: " + ultimaJogada);
        rodarJogo();
    }

    // metodo para rodar o jogo com while loop
    public void rodarJogo() {
        Boolean bloqueado = false;
        
        while (true) {
            // for jogadores

            for (int i = 0; i < jogadores.size(); i++) { // aparentemente essa forma de for é melhor para aplicar os mais quatro e mais dois
                Jogador jogador = jogadores.get(i);
                jogador.mostrarMao();

                 if (bloqueado == true) {
                    System.out.println("Você foi bloqueado, passando a vez"); //precisa considerar que nao pode bloquear a mesma pessoa que jogou a carta
                    bloqueado = false; //reinicia o boolean
                    continue;
                 }

                if (jogador.cartasAComprar > 0) { // faz comprar se for obrigado
                    compraDeCartas(jogador);
                    continue;
                }
                if (temJogadasValidas(jogador) == false) { // faz comprar se nao tiver jogadas validas, nessa ordem pois se ele comprar uma valida por obrigacao (+2 ou +4) ele nao deve comprar mais
                    compraDeCartas(jogador);
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

                    cartaJogada = encontrarCarta(jogador, corDesejada + " " + valorDesejado); //verifica se a carta digitada existe na mao
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
                        maisQuatro(jogador, i); 
                        continue;
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Coringa")) {
                        coringa(jogador);
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Block")) {
                        bloqueado = true;
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Inverte")) {
                        inverter();
                    } else if (ultimaJogada.getValue().equalsIgnoreCase("Mais Dois")) {
                        maisDois(jogador, i);
                    }
                } else {
                    System.out.println("Carta inválida, escolha outra carta");
                }
                if (jogador.getMao().isEmpty()) {
                    System.out.println(jogador.getNome() + " venceu o jogo!");
                    return;
                }
            }
        }

    }




    public void compraDeCartas(Jogador jogador) { 
        System.out.println("Você não tem jogadas válidas, compre " + jogador.cartasAComprar + " cartas");
        Scanner leitor = new Scanner(System.in);
        while (!leitor.nextLine().equalsIgnoreCase("comprar")) {
            System.out.println("Digite 'comprar' meu deus cara burro");
        }
        for (int i = 0; i < jogador.cartasAComprar; i++) {
            jogador.getMao().add(baralho.entregador());
        }
        jogador.cartasAComprar = 0;
        System.out.println("Essa é sua mão agora: ");
        jogador.mostrarMao();
    }


    private void maisDois(Jogador jogador, int i) {
        int proximoJogador = (i + 1) % jogadores.size();
        Jogador proxJog = jogadores.get(proximoJogador);
        proxJog.cartasAComprar += 2;
        System.out.println(proxJog.getNome() + " vai comprar 2 cartas");
    }

    private void maisQuatro(Jogador jogador, int i) {
        Scanner leitorcores = new Scanner(System.in);
        System.out.println("Escolha a cor da próxima carta");
        novaCor = leitorcores.nextLine();
        ultimaJogada.setColor(novaCor);
        System.out.println("A cor escolhida é " + novaCor);
        int proximoJogador = (i + 1) % jogadores.size();
        Jogador proxJog = jogadores.get(proximoJogador);
        proxJog.cartasAComprar += 4;
        System.out.println(proxJog.getNome() + " vai comprar 4 cartas");
    }

    private void inverter() {
        Collections.reverse(jogadores);
        // logica pensar depois
    }

    private void coringa(Jogador jogador) {
        Scanner leitorcores = new Scanner(System.in);
        System.out.println("Escolha a cor da próxima carta");
        novaCor = leitorcores.nextLine();
        ultimaJogada.setColor(novaCor);
        System.out.println("A cor escolhida é " + novaCor);
    }

    public boolean temJogadasValidas(Jogador jogador) {
        for (Cartas carta : jogador.getMao()) {
            if (carta.getColor().equalsIgnoreCase(novaCor) || carta.getColor().equals(ultimaJogada.getColor()) || carta.getValue().equals(ultimaJogada.getValue())
                    || carta.getValue().equals("Coringa") || carta.getValue().equals("Mais Quatro")) {
                return true;

            }
        }
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