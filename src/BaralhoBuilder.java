import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaralhoBuilder {
    public List<Cartas> montaBaralho() {
        List<String> cores = Arrays.asList("Vermelho", "Verde", "Azul", "Amarelo");
        List<String> valores = Arrays.asList("Zero", "Um", "Dois", "Tres", "Quatro", "Cinco", "Seis", "Sete", "Oito", "Nove", "Block", "Inverte", "Mais Dois");
        List<String> especiais = Arrays.asList("Coringa", "Mais Quatro");
   
        List<Cartas> baralho = new ArrayList<>(); 
   
        for (String cor : cores) {
            for (String valor : valores) {
                String caminho = "cards/" + cor + "/" + valor + ".png";
                baralho.add(new Cartas(cor, valor, caminho));
                if (valor != "Zero") {
                    baralho.add(new Cartas(cor, valor, caminho));
                }
            }
        }
        for (String especial : especiais) {
            String caminho = "cards/coringa/" + especial + ".png"; 
            for (int i = 0; i < 4; i++) {
                baralho.add(new Cartas("", especial, caminho)); // especiala não tem cor
            }
        }
        return baralho;
    }
}