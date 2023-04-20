import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VueDesert extends JPanel implements ActionListener {
    private Desert desert;
    private JFrame frame;

    private int width = 1200,height = 1200;

    public VueDesert(Desert d){
        desert = d;

        setBackground(Color.white);

        setPreferredSize(new Dimension(width,height));

        JFrame frame = new JFrame("Désert Interdit");
        this.frame = frame;

        for(Case c : desert.getGrilleCase()){
            JButton btn = new JButton(c.getX()+" " + c.getY());
            btn.setBounds(350+c.getX()*110,100+c.getY()*110,100,100);
            btn.addActionListener(e->{
            if(desert.playingJoueur().nextTo(c) && c.getNiveau()>0) {
                c.desabler();
                System.out.println("Cliqué");
                desert.nextPlayer();
                frame.repaint();
            }
            });
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(true);
            frame.add(btn);

        }
        JButton fdt = new JButton("Fin de tour");
        fdt.setBounds(650,10,100,50);
        fdt.addActionListener(e->{
            desert.nextPlayer();
            desert.action();
            frame.repaint();
        });
        frame.add(fdt);
        JButton f = new JButton("Reset Game");
        f.setBounds(0,10,100,50);
        f.addActionListener(e->{
            System.out.println("Reset");
            desert = new Desert();
            frame.repaint();
        });

        frame.add(f);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(60, 60, 60));
        g.fillRect(0, 0, width, height);
        for(Case c : desert.getGrilleCase()){
            g.setColor(new Color(156,21,95));
            if(c.getZone() == Zone.Oeil)
                g.setColor(new Color(0,0,0));
            g.fillRect(350+c.getX()*110,100+c.getY()*110,100,100);
            g.setColor(new Color(250,250,250));
            g.drawString("Niveau : " + c.getNiveau(),350+c.getX()*110,111+c.getY()*110 );
            ArrayList<Joueur> joueursCase = c.getContenu();
            if(!c.isEmpty()){
                g.setColor(new Color(40,180,120));
                g.fillOval(370+c.getX()*110,125+c.getY()*110,60,60);
                g.setColor(new Color(250,250,250));
                g.drawString("Joueurs : " + joueursCase.size(),350+c.getX()*110,125+c.getY()*110 );
            }
        }g.drawString("Niveau de sable total :" + desert.totalSandLevel(),2,height/2);
        g.drawString("Le joueur actuelle se trouve en : (" +desert.playingJoueur().getX() + ", " + desert.playingJoueur().getY() + ")",2,height/3);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
