import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CardDisplay extends JFrame {
    public CardDisplay(List<Cartas> cartas) {
        setTitle("Cartas na Mão");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        for (Cartas carta : cartas) {
            System.out.println("Carregando imagem: " + carta.getImgDir());
            ImageIcon cardIcon = new ImageIcon(getClass().getClassLoader().getResource(carta.getImgDir()));

            JLabel cardLabel = new JLabel(cardIcon);
            add(cardLabel);
        }
        

        setVisible(true);
    }
}