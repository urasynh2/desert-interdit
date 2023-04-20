import java.util.ArrayList;
enum Zone{
    Oeil,Crash,Piste,Oasis,Tunnels,Piece,Normal;
};
public class Case {
    private int x,y;
    private int niveau;
    private ArrayList<Joueur> contenu;

    private final Zone z;
    public Case(int x, int y, int niveau,Zone type) {
        switch (type){
            case Piece,Crash,Piste,Tunnels,Oasis,Normal -> {
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
        contenu.add(j);
    }
    public void desabler(){
        if(niveau >0)
            niveau--;
    }
    public void push(int x, int y ){
        this.x += x; this.y +=y;
    }
    //public Zone ramasserPiece(){

   // }
}
