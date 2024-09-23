public class App {
    public static void main(String[] args) throws Exception {
        Baralho baralho = new Baralho();
        Jogo jogo = new Jogo(baralho, null, null);
        jogo.iniciarJogo();
    }
}
