import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Baralho baralho = new Baralho();

        Mao mao = new Mao(baralho);
        List<Cartas> cartas = mao.entregaMao();

        System.out.println("Cartas na mão do jogador:");
        for (Cartas carta : cartas) {
            System.out.println(carta);
        }
        new CardDisplay(cartas);
        //Jogo jogo = new Jogo(baralho, null, null);
        //jogo.iniciarJogo();
    }
}
