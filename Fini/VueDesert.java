import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class VueDesert extends JPanel implements ActionListener {
    private Desert desert;
    private JFrame frame;
    Legend help;
    private final VueJoueur vueJoueur;
    private final ArrayList<JButton> buttons;
    private final int width = 1200,height = 1200;
    private boolean perdu = false;
    private boolean gagne = true;
    private int diff = 0;
    private boolean switchAction = false; // Un boolean pour modéliser le fait que lorsque l'on tape sur le bouton switch Action
    // On peux passer du mode desemsabler au mode déplacement
    public VueDesert(Desert d){
        desert = d;
        vueJoueur = new VueJoueur(d);

        buttons = new ArrayList<>();
        setBackground(Color.white);

        setPreferredSize(new Dimension(width,height));

        JFrame frame = new JFrame("Désert Interdit");
        this.frame = frame;
        updateButtons(diff);
        /**
         * Bouton fin de tour ainsi que bouton de reset Game
         */
        JButton fdt = new JButton("Fin de tour");
        fdt.setBounds(0,51,100,50); // 650, 10
        fdt.addActionListener(e->{
            desert.nextPlayer();
            diff = desert.action();
            //updateButtons(diff);
            desert.resetNbAction();
            frame.repaint();
        });
        frame.add(fdt);
        /**
         * Bouton qui permet de Switch les actions du joueur:
         * switchAction false = Desemsabler
         * switchAction vaut True = déplacement
         */
        JButton switchA = new JButton("Passer du mode Déplacement au mode Désensablage");
        switchA.setBounds(250,10,350,50);
        switchA.addActionListener(e->{
            switchAction = ! switchAction;
            frame.repaint();
        });
        frame.add(switchA);
        /**
         * Bouton afin d'explorer la case du joueur actuelle
         */
        JButton explore = new JButton("Explorer");
        explore.setBackground(new Color(180,180,150));
        explore.setBounds(710,0,100,80);
        explore.addActionListener(e->{
          if(desert.getNbAction()<4) {
              desert.explore();
              desert.increNbAction();
              frame.repaint();
          }
        });
        frame.add(explore);
        /**
         * Bouton Ramasse du joueur qui est disponible a n'importe quel moment mais utilisable
         * exclusivement si le joueur a explorer la case et s'il sagit d'une case de type Zone.piece
         */
        JButton ramasse = new JButton("Ramasser");
        ramasse.setBackground(new Color(250,250,0));
        ramasse.setBounds(0,100,100,50);
        ramasse.addActionListener(e->{
            if(desert.getNbAction()<4 && desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY() *5).getZone() == Zone.Piece
                && desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY() *5).isExploredZone()) {
                desert.ramassePiece(desert.playingJoueur());
                desert.increNbAction();
                frame.repaint();
            }
        });
        frame.add(ramasse);

        JButton f = new JButton("Légende");
        f.setBounds(0,0,100,50);
        f.addActionListener(e->{
            /**
             * Bouton afin d'aider le joueur pour qu'il puisse comprendre les différents choix artistiques
             * notamment quelle couleur représente certaines zones.
             */
            help = new Legend();
        });
        frame.add(f);

       /* int i = 1;
        for(Joueur j : desert.getJoueurs()) {
            JButton btn = new JButton("Donner de l'eau");
            btn.setBounds(1000, 500 * i, 60, 60);
            btn.setBackground(new Color(Math.max(255 - i, 0), 0, 0));
            btn.addActionListener(e -> {
                System.out.println("aaaaacasdcqsdc");
                desert.giveWater(j);
            });
            System.out.println("aaaaa");
            frame.add(btn);
            i = i + 780 / desert.getJoueurs().size();
        }
        */
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g){

        if(!desert.gameOver() && !desert.win()) {
            super.paintComponent(g);
            //updateButtons(diff);

            /**
             * Affichage graphique par défaut modélisant les différentes cases et les joueurs
             * tout en modifiant la couleur du joueur actuel
             */

            g.setColor(new Color(60, 60, 60));
            g.fillRect(0, 0, width, height);
            for (Case c : desert.getGrilleCase()) {
                g.setColor(new Color(255,197,70));
                if (c.getZone() == Zone.Oeil)
                    g.setColor(new Color(229,44,67));
                else if (c.getZone() == Zone.Oasis || (c.getZone() == Zone.FakeOasis && !c.isExploredZone()))
                    g.setColor(new Color(119,218,215));
                else if (c.getZone() == Zone.Tunnels && c.isExploredZone()) {
                    g.setColor(new Color(166,166,166));
                }
                else if (c.getZone() == Zone.Piece && c.getPiece() != Piece.Ind && c.isExploredZone()) {
                    g.setColor(new Color(99,44,19));
                }
                else if (c.getZone() == Zone.Piece && c.getPiece() == Piece.Ind && c.isExploredZone()) {
                    g.setColor(new Color(176,97,63));
                }
                else if (c.getZone() == Zone.Piste && c.isExploredZone()) {
                    g.setColor(new Color(255,196,145));
                }
                g.fillRect(350 + c.getX() * 110, 100 + c.getY() * 110, 100, 100);
                g.setColor(new Color(250, 250, 250));
                g.drawString("Niveau : " + c.getNiveau(), 350 + c.getX() * 110, 111 + c.getY() * 110);
                ArrayList<Joueur> joueursCase = c.getContenu();
                if (!c.isEmpty()) {
                    g.setColor(new Color(255,177,181));
                    if(c.contains(desert.playingJoueur()))
                        g.setColor(new Color(255,136,114));
                    g.fillRect(370 + c.getX() * 110, 125 + c.getY() * 110, 60, 60);
                    g.setColor(new Color(250, 250, 250));
                    g.drawString("Joueurs : " + joueursCase.size(), 350 + c.getX() * 110, 125 + c.getY() * 110);
                }
            }
            //g.setColor(new Color());
            g.setFont(new Font("Arial",Font.PLAIN,15));
            g.drawString("Action Actuelle :" + getAction(), 510, 85);
            g.drawString("Action Restante : " + (4 - desert.getNbAction()),2,height/3-60);
            g.drawString("Niveau de sable total :" + desert.totalSandLevel(), 2, height / 3);
            g.drawString("Niveau de la tempete actuel :" + desert.nivTempete, 2, height / 3 -20);
            g.drawString("Le joueur actuelle se trouve en : (" + desert.playingJoueur().getX() + ", " + desert.playingJoueur().getY() + ")", 2, height /3-40);

            vueJoueur.paintComponent(g);
        }
        else if(desert.gameOver()){
            /**
             * Ici on veut faire en sorte que l'affichage s'éteigne puis se rallume avec une fenetre différent si la partie est perdu de
             * des manieres suivantes : Niveau de la tempete >= 7 || totalSandLevel >=43
             */
            if(!perdu)
                newFrame("Game over");
            g.setColor(new Color(115,155,155));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(125,0,0));
            g.setFont(new Font("Arial",Font.BOLD,58));
            g.drawString("Perdu ! " , 350, 350);
            frame.getContentPane().add(this);
            frame.pack();
            frame.setVisible(true);
        }else {

            if (!perdu)
                newFrame("Fini ");
            g.setColor(new Color(115, 155, 155));
            g.fillRect(0, 0, width, height);
            g.setColor(new Color(125, 0, 0));
            g.setFont(new Font("Arial", Font.BOLD, 58));
            g.drawString("Gagné ! ", 350, 350);
            frame.getContentPane().add(this);
            frame.pack();
            frame.setVisible(true);
        }
    }
    public void newFrame(String s){
        frame.setVisible(false);
        JFrame frame = new JFrame(s);
        this.frame = frame;
        perdu = true;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public int moveButtonX(int pos, int differential){
        if(differential>0){
            return (pos + differential<5)?pos+differential: (pos + differential) %5; //x+dx<5?x+dx:(x+dx)%5;
        }return pos;
    }
    public int moveButtonY(int pos, int differential){
        if(differential<0){
            return (pos + differential<5)?pos+differential: (pos + differential) %5; //x+dx<5?x+dx:(x+dx)%5;
        }return pos;
    }
    public void updateButtons(int diff){

        for(Case c : desert.getGrilleCase()){
            JButton btn = new JButton(c.getX()+" " + c.getY());
            btn.setBounds(350+ moveButtonX(c.getX(),diff)*110,100+moveButtonY(c.getY(),diff)*110,100,100);
            /**
             * Ajout des boutons par défaut du jeu ou l'on peut cliquer si et seulement si le joueur actuel est
             * présent a coté de la case cliquée
             */
            btn.addActionListener(e->{
                if(desert.playingJoueur().nextTo(c) && desert.getNbAction() <4) {
                    if(!switchAction && c.getNiveau()>0){
                        c.desabler();
                        desert.increNbAction();
                    }
                    else if(c.getNiveau()<2 && c.getZone() != Zone.Oeil){
                        desert.goOutCase(desert.playingJoueur());
                        c.enterPlayer(desert.playingJoueur());
                        desert.increNbAction();
                    }
                    System.out.println("Cliqué");
                    frame.repaint();
                }
            });
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(true);
            buttons.add(btn);
            frame.add(btn);
        }
    }
    public String getAction(){
        if(switchAction){
            return "Déplacement";
        }else return "Désensabler";
    }


}
