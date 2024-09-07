import java.util.List;

public class Jogador {
    public int cartasAComprar = 0;
    private List<Cartas> mao;
    public List<Cartas> getMao() {
        return mao;
    }

    public void setMao(List<Cartas> mao) {
        this.mao = mao;
    }

    private String nome;
    //private Boolean jogadorIAa;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void mostrarMao() {
        System.out.println(nome + " tem as seguintes cartas na mão");
        for (Cartas carta : mao) { //percorre as cartas que o entregaMao vai entregando e imprime
            System.out.println(carta);
        }
    }

    public Jogador(String nome, Mao mao, Boolean jogadorIA) {  //construtor para poder puxar esses atributos em outras classes, passando os atributos entre ()
        this.nome = nome;
        this.mao = mao.entregaMao();
       // this.jogadorIA = jogadorIA;
    }
    
    public String getNome() {
        return nome;
    }
}

