import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Legend extends JPanel {
    private JFrame frame;
    private int width = 800, height = 600;
    private int tailleCase = 30;
    private int tailleCased = 35;
    public Legend(){
        setBackground(Color.white);

        setPreferredSize(new Dimension(width,height));

        frame = new JFrame("Legend");



        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }
    public  void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setFont(new Font("Arial",Font.PLAIN,20));

        g.setColor(new Color(60, 60, 60));
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(255,216,130));
        g.fillRect(20, 200 + tailleCased, tailleCase, tailleCase);

        g.setColor(new Color(229,44,67));
        g.fillRect(20, 200 + tailleCased*2, tailleCase, tailleCase);

        g.setColor(new Color(119,218,215));
        g.fillRect(20, 200 + tailleCased*3, tailleCase, tailleCase);


        g.setColor(new Color(166,166,166));
        g.fillRect(20, 200 + tailleCased*4, tailleCase, tailleCase);


        g.setColor(new Color(99,44,19));
        g.fillRect(20, 200 + tailleCased*5, tailleCase, tailleCase);

        g.setColor(new Color(176,97,63));
        g.fillRect(20, 200 + tailleCased*6, tailleCase, tailleCase);

        g.setColor(new Color(255,10,145));
        g.fillRect(20, 200 + tailleCased*7, tailleCase, tailleCase);

        g.setColor(new Color(255,255,255));
        g.drawString("Type de la Case : Sable Normal, Case par défaut non-explorée.",50,220 + tailleCased);
        g.drawString("Type de la Case : Oeil de la tempete, attention c'est inaccessible.",50,220 + tailleCased*2);
        g.drawString("Type de la Case : Oasis ou Mirage : Explorez la case et revigorez vous.",50,220 + tailleCased*3);
        g.drawString("Type de la Case : Un tunnel lorsqu'il est explorée.",50,220 + tailleCased*4);
        g.drawString("Type de la Case : Une zone contenant une des 4 pièces.",50,220 + tailleCased*5);
        g.drawString("Type de la Case : Une zone ne contenant aucune pièce..",50,220 + tailleCased*6);
        g.drawString("Type de la Case : Il s'agit de la piste de décollage ",50,220 + tailleCased*7);
    }

}
