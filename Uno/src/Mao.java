import java.util.List;
import java.util.ArrayList;

public class Mao {
    private Baralho baralho;

    public Mao(Baralho baralho) {
        this.baralho = baralho;          //para adicionar um metodo de outra classe, faço isso para instanciar um objeto daquela classe aqui
    }
    
    public List<Cartas> entregaMao() {

        List<Cartas> mao = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mao.add(baralho.entregador());
        }
        return mao;
    }
}
