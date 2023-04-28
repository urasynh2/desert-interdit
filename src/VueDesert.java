import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class VueDesert extends JPanel implements ActionListener,KeyListener {
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
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    public void paintComponent(Graphics g){
        if(desert.getNbAction()==4){
            desert.action();
            desert.nextPlayer();
            desert.resetNbAction();
        }
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
                    g.setColor(new Color(255,10,145));
                }
                g.fillRect(350 + c.getX() * 110, 100 + c.getY() * 110, 100, 100);
                g.setColor(new Color(250, 250, 250));
                g.setFont(new Font("Arial",Font.PLAIN,15));
                g.drawString("Niveau : " + c.getNiveau(), 350 + c.getX() * 110, 111 + c.getY() * 110);


                ArrayList<Joueur> joueursCase = c.getContenu();
                if (!c.isEmpty()) {
                    g.setColor(new Color(100,100,20));
                    if(c.contains(desert.playingJoueur()))
                        g.setColor(new Color(255,80,40));
                    g.fillOval(370 + c.getX() * 110, 125 + c.getY() * 110, 30, 30);
                    g.setColor(new Color(250, 250, 250));
                    g.drawString("Joueurs : " + joueursCase.size(), 350 + c.getX() * 110, 125 + c.getY() * 110);

                }

                g.setFont(new Font("Arial",Font.BOLD,20));
                g.setColor(new Color(0,0,0));
                g.drawString(c.getX()+" " + c.getY(),400+c.getX()*110,150+c.getY()*110);
            }
            //g.setColor(new Color());
            g.setFont(new Font("Arial",Font.PLAIN,15));
            g.setColor(new Color(255,255,255));
            g.drawString("Action Actuelle :" + getAction(), 510, 85);
            g.drawString("Action Restante : " + (4 - desert.getNbAction()),2,height/3-60);
            g.drawString("E + {z,q,s,d,Space} -> Explorer une zone " ,2,height-530);
            g.drawString("Espace pour affecter l'action a la case du joueur actuelle " ,2,height-450);
            g.drawString("R -> Switch le type d'action " ,2,height-510);
            g.drawString("L -> Ouvre la légende " ,2,height-490);
            g.drawString("A -> Ramasser une pièce " ,2,height-470);
            g.drawString("Action disponible : " ,2,height-550);
            g.drawString("Niveau de sable total :" + desert.totalSandLevel(), 2, height / 3);
            g.drawString("Niveau de la tempete actuel :" + desert.nivTempete, 2, height / 3 -20);
            g.drawString("Le joueur actuelle se trouve en : (" + desert.playingJoueur().getX() + ", " + desert.playingJoueur().getY() + ")", 2, height /3-40);

            /**,
             * Interface des touches pour le déplacement du joueur
             */
            g.setColor(new Color(91,80,225));
            g.drawRect(0,410,280,200);
            g.fillRect(1,411,278,198);

            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString("Touche d'action de mobilité :",0,430);
            for(int i = 0 ; i < 3 ; i++){
                g.setColor(new Color(255,255,255,200));
                g.fillRect(30 + (i) * 50, 450,50,50);
                if(i==2)
                g.fillRect(30 + (i+1) * 50, 450,50,50);
                g.fillRect(30 + i * 50, 500,50,50);

                g.setColor(new Color(0,0,0));
                g.drawRect(30 + i * 50, 500,50,50);
                g.drawRect(30 + (i+1) * 50, 450,50,50);
                g.drawRect(30 + (i) * 50, 450,50,50);
                g.setFont(new Font("Arial",Font.BOLD,20));

                switch (i){
                    case 0 -> {
                        g.drawString("Z",50+(i+1)*50,480);
                        g.drawString("Q", 50 + i * 50, 530);
                    }
                    case 1 -> {
                        g.drawString("E",50+(i+1)*50,480);
                        g.drawString("S", 50 + i * 50, 530);
                    }
                    case 2 -> {
                        g.drawString("R",50+(i+1)*50,480);
                        g.drawString("D", 50 + i * 50, 530);
                    }
                }

            }
            g.drawString("A",50+(0)*50,480);
            g.setColor(new Color(255,255,255,200));
            g.fillRect(30 + 0 * 50, 550,50,50);

            g.setColor(new Color(0,0,0));
            g.drawRect(30 , 550,50,50);
            g.drawString("L",50+0*50,580);

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


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /**
         * Fonction de déplacement similaire et de creusage qui palie  aux problemes des boutons
         * qui permet au joueur de se déplacer avec les touches z q s d et permet de passer par exemple de la ligne 2 a la fin de ligne
         * a la ligne 3 en début de ligne tranquille.
         */
        if(e.getExtendedKeyCode()==KeyEvent.VK_E && !desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY()*5).isExploredZone()){
            if(desert.getNbAction()<4) {
                desert.explore();
                desert.increNbAction();
                frame.repaint();
            }
        }
        if(e.getExtendedKeyCode() == KeyEvent.VK_A && desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY()*5).getZone() == Zone.Piece
           && desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY()*5).getPiece() != Piece.Ind){
            desert.ramassePiece(desert.playingJoueur());
            desert.increNbAction();
            frame.repaint();
        }
        if(e.getExtendedKeyCode()==KeyEvent.VK_L)
            help = new Legend();

        if(e.getExtendedKeyCode()==KeyEvent.VK_R) {
            switchAction = !switchAction;
            frame.repaint();
        }
        if(!switchAction && e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {

            desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY() * 5).desabler();
            desert.increNbAction();
            frame.repaint();
        }
        else if(switchAction && e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {

            desert.increNbAction();
            frame.repaint();
        }
        if(this.switchAction) {
            if(desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY()*5).getNiveau()<2)
                try {
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_S
                            && desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY() + 1) * 5).getZone()!=Zone.Oeil) {
                        desert.goOutCase(desert.playingJoueur());
                        desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY() + 1) * 5).enterPlayer(desert.playingJoueur());
                        desert.increNbAction();
                        frame.repaint();
                    }
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_Z
                        && desert.getGrilleCase().get(desert.playingJoueur().getX()  + (desert.playingJoueur().getY() - 1) * 5).getZone() != Zone.Oeil) {
                        desert.goOutCase(desert.playingJoueur());
                        desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY() - 1) * 5).enterPlayer(desert.playingJoueur());
                        desert.increNbAction();
                        frame.repaint();
                    }
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_Q
                        && desert.getGrilleCase().get(desert.playingJoueur().getX() - 1 + desert.playingJoueur().getY() * 5).getZone() != Zone.Oeil) {
                        desert.goOutCase(desert.playingJoueur());
                        desert.getGrilleCase().get(desert.playingJoueur().getX() - 1 + desert.playingJoueur().getY() * 5).enterPlayer(desert.playingJoueur());
                        desert.increNbAction();
                        frame.repaint();
                    }
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_D
                        && desert.getGrilleCase().get(desert.playingJoueur().getX() + 1 + desert.playingJoueur().getY() * 5).getZone() != Zone.Oeil) {
                        desert.goOutCase(desert.playingJoueur());
                        desert.getGrilleCase().get(desert.playingJoueur().getX() + 1 + desert.playingJoueur().getY() * 5).enterPlayer(desert.playingJoueur());
                        desert.increNbAction();
                        frame.repaint();
                    }
                } catch (IndexOutOfBoundsException exception) {
                    System.out.println('b');
                }
            }else{
                try {
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_S) {
                        if(desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY()+1) * 5).getNiveau()>0
                            && desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY() + 1) * 5).getZone()!=Zone.Oeil) {
                            desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY() + 1) * 5).desabler();
                            desert.increNbAction();
                            frame.repaint();
                        }
                    }
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_Z) {
                        if (desert.getGrilleCase().get(desert.playingJoueur().getX() +  (desert.playingJoueur().getY()-1) * 5).getNiveau() > 0
                        && desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY()-1) * 5).getZone() != Zone.Oeil) {
                            desert.getGrilleCase().get(desert.playingJoueur().getX() + (desert.playingJoueur().getY() - 1) * 5).desabler();
                            desert.increNbAction();
                            frame.repaint();
                        }
                    }
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_Q) {
                        if(desert.getGrilleCase().get(desert.playingJoueur().getX() - 1 + desert.playingJoueur().getY() * 5).getNiveau()>0
                        && desert.getGrilleCase().get(desert.playingJoueur().getX() - 1 + desert.playingJoueur().getY() * 5).getZone() != Zone.Oeil){
                            desert.getGrilleCase().get(desert.playingJoueur().getX() - 1 + desert.playingJoueur().getY() * 5).desabler();
                            desert.increNbAction();
                            frame.repaint();
                            }
                    }
                    if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_D) {
                        if(desert.getGrilleCase().get(desert.playingJoueur().getX() + 1 + desert.playingJoueur().getY() * 5).getNiveau()>0
                        && desert.getGrilleCase().get(desert.playingJoueur().getX() + 1 + desert.playingJoueur().getY() * 5).getZone()!=Zone.Oeil){
                        desert.getGrilleCase().get(desert.playingJoueur().getX() + 1 + desert.playingJoueur().getY() * 5).desabler();
                        desert.increNbAction();
                        frame.repaint();
                        }
                        if (desert.getNbAction() < 4 && e.getExtendedKeyCode() == KeyEvent.VK_Q){
                            if(desert.getGrilleCase().get(desert.playingJoueur().getX()  + desert.playingJoueur().getY() * 5).getNiveau()>0
                                    && desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY() * 5).getZone()!=Zone.Oeil){
                                desert.getGrilleCase().get(desert.playingJoueur().getX() + desert.playingJoueur().getY() * 5).desabler();
                                desert.increNbAction();
                                frame.repaint();
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException exception) {
                    System.out.println('c');
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public String getAction(){
        if(switchAction){
            return "Déplacement";
        }else return "Désensabler";
    }

    public void afficheButton(){
        /**
         * Méthode non appellée qui pourrait permetre un jeu avec des controles de types boutons
         */

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
}
