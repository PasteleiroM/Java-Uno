import java.util.Collections;
import java.util.List;

public class Baralho {
    private List<Cartas> baralho; 

    public Baralho() {
        BaralhoBuilder builder = new BaralhoBuilder();
        this.baralho = builder.montaBaralho();
        embaralha();
    }



    public void embaralha() {
        Collections.shuffle(baralho);
    }

    public List<Cartas> getBaralho() {
        return baralho;
    }

    public Cartas entregador() { //serve para comnprar cartas novas também no meio do jogo
        return baralho.remove(0);
    }

    public int restantes() {
        return baralho.size();
    }
}
