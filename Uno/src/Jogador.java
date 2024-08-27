
public class Jogador {
    private Mao mao;
    private String nome;
    private Boolean jogadorIA;


  

    public void setNome(String nome) {
        this.nome = nome;
    }

    


    
    public void mostrarMao() {
        System.out.println(nome + " tem as seguintes cartas na mão");
        for (Cartas carta : mao.entregaMao()) {
            System.out.println(carta);
        }
    }

    public Jogador(String nome, Mao mao, Boolean jogadorIA) {
        this.nome = nome;
        this.mao = mao;
        this.jogadorIA = jogadorIA;
    }
    
    public String getNome() {
        return nome;
    }
}

