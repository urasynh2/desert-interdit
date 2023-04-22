import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VueJoueur extends JPanel {
    private Desert desert;
    private JPanel panel;
    private int hPanel = 0;
    private int wPanel = 300;
    public VueJoueur(Desert desert){
        this.desert = desert;
        hPanel= 780 / desert.getJoueurs().size();
        panel = new JPanel();
    }
    public void paintComponent(Graphics g){
        int k = 0;
        for(Joueur j : desert.getJoueurs()) {
            g.setColor(new Color(240,90,110));
            g.fillRect(900,10+k,wPanel,hPanel);
            g.setColor(new Color(25,25,25));
            g.fillRect(900+10,20+k,wPanel-10,hPanel-10);
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Arial",Font.PLAIN,15));
            g.drawString("Joueur n°"+j.getId(),1000,34+k);
            g.drawString( "Niveau de la gourde : " + j.getGourde(),1000,50+k );
            for(int i = 0; i < j.getGourde();i++){
                g.setColor(new Color(80,180,240));
                g.fillOval(1000 + 25 * i,55+k,20,20);
            }
            k = k + hPanel;
        }
    }
    public JPanel nvPanel(Joueur joueur,Graphics gra){
        JPanel nv = new JPanel();

        return nv;
    }



}
