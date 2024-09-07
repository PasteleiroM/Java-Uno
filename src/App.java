public class App {
    public static void main(String[] args) throws Exception {
        Baralho baralho = new Baralho();

        //Mao mao1 = new Mao(baralho);
        //Mao mao2 = new Mao(baralho);

        //String nomeJog1 = Jogo.addJogadores();
        //String nomeJog2 = Jogo.addJogadores();
        //Jogador jogador1 = new Jogador(nomeJog1, mao1, false);
        //Jogador jogador2 = new Jogador(nomeJog2, mao2, false);

        //jogador1.mostrarMao();
       // jogador2.mostrarMao();

        //System.out.println("Baralho");
        //for (Cartas carta : baralho.getBaralhoa()) {
        //    System.out.println(carta);
        //}

        Jogo jogo = new Jogo(baralho, null, false, null);
        jogo.iniciarJogo();
    }
}
