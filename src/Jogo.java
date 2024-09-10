import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {
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
        //pergunta quantos jogadores vão jogar
        Scanner leitor = new Scanner(System.in);
        System.out.println("Quantos jogadores vão jogar?");
        int numJogadores = leitor.nextInt();
        for (int i = 0; i < numJogadores; i ++) {


            String nomeJogador = addJogadores(i +1); //cria o nome
            Mao mao = new Mao(baralho);
            Jogador jogador = new Jogador(nomeJogador, mao, false); //aqui cria o objeto jogador passando o nomeque acabou de ser criado
            jogadores.add(jogador); //manda o jogador criado no loop pra lista de jogadores.
        }
        //embaralhar o baralho
        baralho.embaralha();
        //for loop para entregar as cartas iniciais para os jogadores
        for (Jogador jogador : jogadores) {
            jogador.setMao(jogador.getMao());
        }
        boolean ordem = true;
        //colocar a primeira carta na mesa usando o metodo entregador da classsse baralho
        ultimaJogada = baralho.entregador();
        System.out.println("A carta na mesa é: " + ultimaJogada);
    }
    
    
    // metodo para rodar o jogo com while loop
    public void rodarJogo(){
        while(true){
            //for jogadores
            //mostrar mao do jogador
            //verificar se tem jogadas validas com o metodo temJogadasValidas, retorna true ou false
            //se tiver, pergunta qual carta quer jogaar
            //se nao tiver, pede para comprar carta uma vez, e se for valida, já joga, se nao for passa a vez
            //verificar se jogador tem zero cartas na mao
            // se tiver, encerrar loop com break
        }
    }

    //metodo para verificar se ultima carta jogada é especial e implementar acao dela
    public void ultimaJogadaEspecial(Cartas ultimaJogada, Jogador jogador){
        //verifica +2 chama acao
        if (ultimaJogada.getValue().equals("Mais Dois")){
            maisDois(jogador);
        } else if (ultimaJogada.getValue().equals("Mais Quatro")){
            maisQuatro(jogador);
        } else if (ultimaJogada.getValue().equals("Inverte")){
            inverter(jogador);
        } else if (ultimaJogada.getValue().equals("Bloqueia")){
            bloquear(jogador);
        } else if (ultimaJogada.getValue().equals("Coringa")){
            coringa(jogador);
        }
    }

    private void maisDois(Jogador jogador){
        jogador.cartasAComprar += 2;
        //logica pensar depois
    }

    private void maisQuatro(Jogador jogador){
        jogador.cartasAComprar += 4;
        //logica pensar depois
    }

    private void inverter(Jogador jogador){
        ordem = !ordem;
        //logica pensar depois
    }

    private void bloquear(Jogador jogador){
        //logica pensar depois
        //hardset cartas validas para zero é uma boa ideia para implementar isso???
        //vejo depois que fizer o verificador de jogadas validas
    }

    private void coringa(Jogador jogador){
        //logica pensar depois
        //print opcoes de cor e peditr para escolher com input
        //verificar se a cor escolhida é valida
        //colocar uma carta invisivel na mesa com a cor escolhida mas valor vazio para o verificador de jogadas validas aceitar
    }
    
   


    // metodo para verificar se jogador atual tem jogadas validas
    public boolean temJogadasValidas(Jogador jogador){
        //verifica se na mao tem carta com a cor da ultima jogada
        //verifica se na mao tem carta com o mesmo valor da ultima jogada
        //verifica se na mao tem carta especial
        //se tiver, retorna true
        //se nao tiver, retorna false
        return true;
    }

}
