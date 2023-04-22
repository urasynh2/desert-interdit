import java.util.ArrayList;
import java.util.Comparator;

enum Zone{
    Oeil,Crash,Piste,Oasis,FakeOasis,Tunnels,Piece,Normal;
};
public class Case implements Comparable {
    private int x,y;
    private int niveau;
    private ArrayList<Joueur> contenu;

    private final Zone z;
    private boolean exploredZone = false;
    public Case(int x, int y, int niveau,Zone type) {
        switch (type){
            case Piece,Crash,Piste,Tunnels,Oasis,Normal,FakeOasis -> {
                this.x = x;
                this.y = y;
                this.niveau = niveau;
            }case Oeil ->{
                this.x = Desert.xOeil;
                this.y = Desert.yOeil;
                this.niveau = 0;
            }


        }contenu = new ArrayList<>();
        z = type;
    }

    public Case(int x, int y, int niveau) {
        this(x,y,niveau,Zone.Normal);
    }

    public Case(int x, int y, int niveau, ArrayList<Joueur> contenu) {
        this(x,y,niveau);
        this.contenu = contenu;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNiveau() {
        return niveau;
    }
    public Zone getZone(){return z;}
    public ArrayList<Joueur> getContenu() {
        return contenu;
    }
    public boolean isEmpty(){
        return contenu.isEmpty();
    }
    public void enterPlayer(Joueur j){
        j.enter(this);
        contenu.add(j);
    }
    public void sortPlayer(Joueur j){
        contenu.remove(j);
    }
    public void desabler(){
        if(niveau >0)
            niveau--;
    }
    public boolean contains(Joueur j){
        return contenu.contains(j);
    }
    public void ensabler(){
        /**
         * On ne peux ensabler les zones si et seulement si elles ne sont pas du type Oeil,
         * Doit augmenter le niveau de la zone
         */
        if(z != Zone.Oeil)
            niveau++;
    }
    public void push(int dx, int dy ){
        /**
         * Methode assez conscise permettant de calculer les prochains x y des cases en fonction d'une force
         * qui doit etre donnée sous la forme push(0,y) ou push(x,0)
         */
        if(dx>=0)
            this.x = x+dx<5?x+dx:(x+dx)%5; //écriture simplifiée du if then else en ocaml
        else this.x = x-dx<=0?x-dx:4;
        if(dy>=0)
            this.y = dy+y<5?y+dy: (y+dy)%5 ;
        else this.y = dy-y<=0?y-dy: 4 ;
    }

    @Override
    public int compareTo(Object o) {
        /**
         * Surcharge de la méthode compareTo de Comparable qui permet de comparer
         * deux cases en se basant sur leurs coordonées. Permet notamment a la méthode
         * Desert.sort d'avoir un comparateur afin de remanipuler les données pour les mettre au bon Index
         * calculées selon leurs coordonées respectives.
         */
        if(o instanceof Case){
            return (this.x + this.y * 5) - (((Case) o).x + ((Case) o).y *5);
        }else throw new IllegalArgumentException("Pas du bon type");
    }
    public void setExploredZone(){
        exploredZone = true;
    }

    public boolean isExploredZone() {
        return exploredZone;
    }
//public Zone ramasserPiece(){

   // }
}
