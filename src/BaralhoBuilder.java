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
               baralho.add(new Cartas(cor, valor));
               if (valor != "Zero") {
                   baralho.add(new Cartas(cor, valor));
               }
           }
       }
       for (String especial : especiais) {
           for (int i = 0; i < 4; i++) {
               baralho.add(new Cartas("", especial)); // especiala não tem cor
           }
       }
       return baralho;
   }
}