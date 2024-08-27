public class App {
    public static void main(String[] args) throws Exception {
        Baralho baralho = new Baralho();


        Mao mao1 = new Mao(baralho);    
        Mao mao2 = new Mao(baralho);

        Jogador jogador1 = new Jogador("Artur", mao1, false);
        Jogador jogador2 = new Jogador("Arthur", mao2, false);

        jogador1.mostrarMao();
        jogador2.mostrarMao();

        System.out.println("Baralho");
        for (Cartas carta : baralho.getBaralho()) {
            System.out.println(carta);
        }
    }
}
