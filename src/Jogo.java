import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Jogo {
    private String novaCor = "";
    private Baralho baralho;
    private List<Jogador> jogadores;
    private Cartas ultimaJogada;
    public Boolean bloqueado;
    public Boolean gritouUno;

    private int i;

    public Jogo(Baralho baralho, List<Jogador> jogadores, Cartas ultimaJogada) {
        this.baralho = baralho;
        this.jogadores = new ArrayList<>();
        this.ultimaJogada = ultimaJogada;
    }

    public static String addJogadores(int cont) {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite o nome do jogador " + cont);
        String jogador = leitor.nextLine();
        return jogador;

    }

    public void iniciarJogo() {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Quantos jogadores vão jogar?");
        int numJogadores = leitor.nextInt();
        leitor.nextLine();
        for (int i = 0; i < numJogadores; i++) {
            String nomeJogador = addJogadores(i + 1); // ria o nome
            Mao mao = new Mao(baralho);
            System.out.println("Esse jogador vai ser IA? S para sim, N para não");
            String respostaIA = leitor.nextLine();
            if (respostaIA.equalsIgnoreCase("S")) {
                Jogador jogador = new Jogador(nomeJogador, mao, true); // aqui cria o objeto jogador passando o nome que
                                                                       // acabou de ser criado
                jogadores.add(jogador);
            } else if (respostaIA.equalsIgnoreCase("N")) {
                Jogador jogador = new Jogador(nomeJogador, mao, false);
                jogadores.add(jogador);
            } else {
                System.out.println("Digite certo porra");
                i--;
            }
        }

        baralho.embaralha();
        for (Jogador jogador : jogadores) {
            jogador.setMao(jogador.getMao());
        }

        ultimaJogada = baralho.entregador();
        while (ultimaJogada.getValue().equalsIgnoreCase("Mais Quatro")
                || ultimaJogada.getValue().equalsIgnoreCase("Coringa")) {
            baralho.getBaralho().add(ultimaJogada);
            ultimaJogada = baralho.entregador();
            System.out.println("Ops, a carta inicial era um coringa ou mais quatro, vou trocar por outra");
        }
        System.out.println("A carta na mesa é: " + ultimaJogada);
        rodarJogo();
    }

    // metodo para rodar o jogo com while loop
    public void rodarJogo() {
        bloqueado = false;

        while (true) {
            for (i = 0; i < jogadores.size(); i++) { // aparentemente essa forma de for é melhor para aplicar os mais quatro e mais dois

                System.out.println("A carta na mesa é: " + ultimaJogada);
                Jogador jogador = jogadores.get(i);
                System.out.println("É a vez de " + jogador.getNome());
                if (!jogador.getJogadorIA()){
                jogador.mostrarMao();
                }
                if (bloqueado == true) {
                    System.out.println("Você foi bloqueado, passando a vez"); // precisa considerar que nao pode bloquear a mesma pessoa que jogou a carta
                    bloqueado = false; // reinicia o boolean
                    continue;
                }

                if (jogador.cartasAComprar > 0) { // faz comprar se for obrigado
                    compraDeCartas(jogador);
                    continue;
                }
                if (temJogadasValidas(jogador) == false) { // faz comprar se nao tiver jogadas validas, nessa ordem pois
                                                           // se ele comprar uma valida por obrigacao (+2 ou +4) ele nao
                                                           // deve comprar mais
                    compraDeCartas(jogador);
                    continue;
                }
                if (jogador.getJogadorIA()) {
                    logicaIA(jogador, i);
                } else {
                    logicaHumana(jogador, i);

                }
                    
                if (jogador.getMao().isEmpty()) {
                    System.out.println(jogador.getNome() + " venceu o jogo!");
                    return;
                }
            }
        }

    }


    public void compraDeCartas(Jogador jogador) {
        if (jogador.getJogadorIA()) {
            for (int i = 0; i < jogador.cartasAComprar; i++) {
                jogador.getMao().add(baralho.entregador());
                System.out.println("IA comprou " + (i + 1) + " cartas e agora tem " + jogador.getMao().size());
            }
            jogador.cartasAComprar = 0;
        } else {
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
    }

    private void maisDois(Jogador jogador, int i) {
        int proximoJogador = (i + 1) % jogadores.size();
        Jogador proxJog = jogadores.get(proximoJogador);
        proxJog.cartasAComprar += 2;
        System.out.println(proxJog.getNome() + " vai comprar 2 cartas");
    }

    private void maisQuatro(Jogador jogador, int i) {
        if (jogador.getJogadorIA()) {
            int contAzul = 0;
            int contAmarelo = 0;
            int contVerde = 0;
            int contVermelho = 0;
            int maximo = contAzul;
            for (Cartas carta : jogador.getMao()) {
                if (carta.getColor().equals("Azul")) {
                    contAzul += 1;
                } else if (carta.getColor().equals("Amarelo")) {
                    contAmarelo += 1;
                } else if (carta.getColor().equals("Verde")) {
                    contVerde += 1;
                } else if (carta.getColor().equals("Vermelho")) {
                    contVermelho += 1;
                }
            }
            if (contAmarelo > maximo) {
                maximo = contAmarelo;
            }
            if (contVerde > maximo) {
                maximo = contVerde;
            }
            if (contVermelho > maximo) {
                maximo = contVermelho;
            }
            if (maximo == contAzul) {
                novaCor = "Azul";
            }
            if (maximo == contAmarelo) {
                novaCor = "Amarelo";
            }
            if (maximo == contVermelho) {
                novaCor = "Vermelho";
            } 
            if (maximo == contVerde) {
                novaCor = "Verde";
            }

        } else {
            Scanner leitorcores = new Scanner(System.in);
            System.out.println("Escolha a cor da próxima carta");
            novaCor = leitorcores.nextLine();
        }
        ultimaJogada.setColor(novaCor);

        System.out.println("A cor escolhida é " + novaCor);

        int proximoJogador = (i + 1) % jogadores.size();
        Jogador proxJog = jogadores.get(proximoJogador);
        proxJog.cartasAComprar += 4;
        System.out.println(proxJog.getNome() + " vai comprar 4 cartas");
    }

    private void inverter() {
        Collections.reverse(jogadores);
        i = jogadores.size() - i - 1;
        System.out.println("O jogo foi invertido");
    }

    private void coringa(Jogador jogador) {
        if (jogador.getJogadorIA()) {
            int contAzul = 0;
            int contAmarelo = 0;
            int contVerde = 0;
            int contVermelho = 0;
            int maximo = contAzul;
            for (Cartas carta : jogador.getMao()) {
                if (carta.getColor().equals("Azul")) {
                    contAzul += 1;
                } else if (carta.getColor().equals("Amarelo")) {
                    contAmarelo += 1;
                } else if (carta.getColor().equals("Verde")) {
                    contVerde += 1;
                } else if (carta.getColor().equals("Vermelho")) {
                    contVermelho += 1;
                }
            }
            if (contAmarelo > maximo) {
                maximo = contAmarelo;
            }
            if (contVerde > maximo) {
                maximo = contVerde;
            }
            if (contVermelho > maximo) {
                maximo = contVermelho;
            }
            if (maximo == contAzul) {
                novaCor = "Azul";
            }
            if (maximo == contAmarelo) {
                novaCor = "Amarelo";
            }
            if (maximo == contVermelho) {
                novaCor = "Vermelho";
            } else {
                novaCor = "Verde";
            }

        } else {
            Scanner leitorcores = new Scanner(System.in);
            System.out.println("Escolha a cor da próxima carta");
            novaCor = leitorcores.nextLine();
        }
        ultimaJogada.setColor(novaCor);
        System.out.println("A cor escolhida é " + novaCor);
    }

    public boolean temJogadasValidas(Jogador jogador) {
        for (Cartas carta : jogador.getMao()) {
            if (carta.getColor().equals(ultimaJogada.getColor())
                    || carta.getValue().equals(ultimaJogada.getValue())
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


    private void logicaHumana(Jogador jogador, int i){
        Cartas cartaJogada = null;
                    String cartaDesejada = "";
                    String corDesejada = "";
                    String valorDesejado = "";

                    while (cartaJogada == null || (!cartaJogada.getColor().equals(ultimaJogada.getColor()) &&
                            !cartaJogada.getValue().equals(ultimaJogada.getValue()))) {
                        Scanner leitor = new Scanner(System.in);
                        System.out.println("Qual carta você quer jogar?");
                        cartaDesejada = leitor.nextLine().trim();
                        if (cartaDesejada.equalsIgnoreCase("Mais Quatro")
                                || cartaDesejada.equalsIgnoreCase("Coringa")) {
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

                        cartaJogada = encontrarCarta(jogador, corDesejada + " " + valorDesejado); // verifica se a carta
                                                                                                  // digitada existe na
                                                                                                  // mao
                        if (cartaJogada != null &&
                                (cartaJogada.getColor().equalsIgnoreCase(ultimaJogada.getColor()) ||
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
                        } else if (carta.getColor().isEmpty() && carta.getValue().equalsIgnoreCase(valorDesejado)) { // ve
                                                                                                                     // se
                                                                                                                     // carta
                                                                                                                     // sem
                                                                                                                     // cor
                                                                                                                     // existe
                                                                                                                     // na
                                                                                                                     // mao
                            cartaJogada = carta;
                            break;
                        }
                    }

                    if (cartaJogada != null) {
                        jogador.getMao().remove(cartaJogada);
                        ultimaJogada = cartaJogada;
                        System.out.println(jogador.getNome() + " jogou " + ultimaJogada);

                        if (ultimaJogada.getValue().equalsIgnoreCase("Mais Quatro")) { // LOGICA DO MAIS QUATRO
                            maisQuatro(jogador, i);
                            
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
                
    }
    private void logicaIA(Jogador jogador, int i) {
        System.out.println("IA tem " + jogador.getMao().size() + " cartas");
        Boolean jogouMaisQuatro = false;
        Boolean jogou = false;
        Boolean jogouBloqueado = false;
        Cartas cartaParaRemover = null;
        Boolean jogouMaisDois = false;
        Boolean jogouCoringa = false;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pressione Enter para a IA jogar...");
        scanner.nextLine();
        // se outros jogadores tem menos cartas, verificar se tem especiais primeiro
        
            
        

        for (Cartas carta : jogador.getMao()) {
            if (jogou) {
                break;
            }
            if ((carta.getColor().equalsIgnoreCase(ultimaJogada.getColor()) && 
             (carta.getValue().equalsIgnoreCase("Mais Dois") || 
              carta.getValue().equalsIgnoreCase("Inverte") || 
              carta.getValue().equalsIgnoreCase("Block"))) ||
            carta.getValue().equalsIgnoreCase("Mais Quatro")) {

            cartaParaRemover = carta;
            ultimaJogada = carta;
            jogou = true;

            if (carta.getValue().equalsIgnoreCase("Mais Quatro")) {
                jogouMaisQuatro = true;
                break;
            } else if (carta.getValue().equalsIgnoreCase("Coringa")) {
                jogouCoringa = true;
                break;
            } else if (carta.getValue().equalsIgnoreCase("Block")) {
                jogouBloqueado = true;
                break;
            } else if (carta.getValue().equalsIgnoreCase("Inverte")) {
                inverter();
                break;
            } else if (carta.getValue().equalsIgnoreCase("Mais Dois")) {
                jogouMaisDois = true;
                break;
            }
        }

        // Se não for uma carta especial, verifica se é válida pela cor ou valor
        else if (carta.getColor().equals(ultimaJogada.getColor()) || 
                 carta.getValue().equals(ultimaJogada.getValue()) || 
                 carta.getValue().equals("Coringa")) {

            cartaParaRemover = carta;
            ultimaJogada = carta;
            jogou = true;

            if (carta.getValue().equalsIgnoreCase("Mais Quatro")) {
                jogouMaisQuatro = true;
                break;
            } else if (carta.getValue().equalsIgnoreCase("Coringa")) {
                jogouCoringa = true;
                break;
            } else if (carta.getValue().equalsIgnoreCase("Block")) {
                jogouBloqueado = true;
                break;
            } else if (carta.getValue().equalsIgnoreCase("Inverte")) {
                inverter();
                break;
            } else if (carta.getValue().equalsIgnoreCase("Mais Dois")) {
                jogouMaisDois = true;
                break;
                }
            }

        }
        if (cartaParaRemover != null) {
            jogador.getMao().remove(cartaParaRemover); // Remoção fora do loop
            System.out.println(jogador.getNome() + " jogou: " + ultimaJogada);
        }
        if (jogouBloqueado) {
            bloqueado = true;
        }

        if (jogouCoringa) {
            coringa(jogador);
        }
        if (jogouMaisQuatro) {
            maisQuatro(jogador, i);
            jogouMaisQuatro = false;
        }
        if (jogouMaisDois) {
            maisDois(jogador, i);
            jogouMaisDois = false;
        }
    }

}