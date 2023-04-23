import java.util.ArrayList;

public class Joueur implements Comparable{
    private int x,y;
    private int id;
    private int gourde;
    private ArrayList<Piece> inventaire = new ArrayList<>();
    public Joueur(int i,int j){
        x = i; y = j;
        gourde = 3 + (int)(Math.random() * 3);
        id++;
    }
    public Joueur(int id){
        /**
         * On initialise aléatoirement les coordonnées du joueur tout en sachant qu'il
         * ne pourront pas être crées a l'intérieur de l'oeil
         */
        int k,m;
        do {
            k = (int) (Math.random() * 5);
            m = (int) (Math.random() * 5);
            //System.out.println(k + " " + m);
        }while((k == 2 && m == 2));
        x = k; y = m;
        gourde = 5;
        this.id = id;
    }
    public void enter(Case c){
        /**
         * Déplacement du joueur selon une case : Ici on assimile simplement les coordonées de la case avec celle du joueur
         */

        this.x = c.getX();
        this.y = c.getY();
    }
    public void drink(){
        if(gourde == 0)
            return;
        gourde--;
    }
    public void onOasis(){
        this.gourde += 2;
    }
    public boolean nextTo(Case c){
        /**
         * Calcul simple pour savoir si le joueur est a coté de la case cliquée sur les
         * directions suivantes : gauche droite haut bas
         */
        if(( (c.getX() - x) <= 1 && (c.getX() - x) >= -1 && c.getY() - y ==0))
                return true;
        else if ((c.getY() - y) <= 1 && (c.getY() - y) >= -1  && c.getX()- x == 0)
            return true;
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getGourde() {
        return gourde;
    }

    public int compareTo(Object o){
        if(o instanceof Joueur)
            return gourde - ((Joueur) o).gourde;
        else throw new IllegalArgumentException("Pas du bon type");
    }

    public void addInventaire(Piece k){
        inventaire.add(k);
        System.out.println(k);
    }

    public ArrayList<Piece> getInventaire() {
        return inventaire;
    }
}
