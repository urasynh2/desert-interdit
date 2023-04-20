import java.util.ArrayList;

public class Desert {
    private ArrayList<Joueur> joueurs;
    private final ArrayList<Case> grilleCase;
    private int joueurId = 0;
    static int xOeil = 2,yOeil =2;
    public Desert(){
        joueurs = new ArrayList<>();
        for(int i = 0 ; i < 4 ; i++){
            joueurs.add(new Joueur());
        }grilleCase = initGrille();
    }
    public Joueur playingJoueur(){
       return joueurs.get(joueurId);
    }
    public void nextPlayer(){

        //System.out.println(playingJoueur().getX());
        if(++joueurId==4)
            joueurId = 0;
        //System.out.println(playingJoueur().getX());
    }
    public int totalSandLevel(){
        int k = 0;
        for(Case c : grilleCase){
            k += c.getNiveau();
        }return k;
    }
    private ArrayList<Case> initGrille(){ //Initialisation des grilles  pour le moment sans notion de différentes formes de zones
        ArrayList<Case> a = new ArrayList<>();
        a.add(new Case(xOeil,yOeil,0,Zone.Oeil));
        for(int j = 0; j < 5 ; j++)
            for(int i = 0; i < 5 ; i++){
                if(!(i==2 && j == 2))
                    if(true) //(j == i && i == 1 | i == 3) | ( j ==2 && i == 0 | i ==4) | (j==4|j == 0 && i ==2 )||(j==1 && i==3)||(j==3 && i==1) )
                        a.add(new Case(i,j,1));
                    else a.add(new Case(i,j,0));
            }
        for(Joueur j : joueurs){
            if((j.getX()>= 2 && j.getY()>=2 ) || j.getY()>=3)
             a.get(j.getX() + j.getY() * 5).enterPlayer(j);
            else a.get(j.getX() +1 + j.getY() *5).enterPlayer(j);
        }
        return a;
    }

    public void action(){ // méthode qui produit une action dans le désert parmis les trois possibles
        int k = (int)(Math.random() *3);
       /** switch (k){
            case 0 -> leVentSouffle();
            case 1,2 ->{leVentSouffle();}
        }**/
       leVentSouffle();
    }
    public void leVentSouffle(){
        int force = 1; //+ (int)(Math.random() *3);

        float rand = (float)Math.random();
        System.out.println(rand + "<1 et  1= " + force);
        if(rand<0.5 && rand>0.25 ) {

            ligneOeil(force);
        }
        else if(rand<0.25) {

            ligneOeil(-force);
        }
        else if (rand>0.75) {

            colonneOeil(force);
        }
        else {

            colonneOeil(-force);
        }
    }
    public void ligneOeil(int force){ // méthode qui déplace la ligne de l'oeil dépendant de la force qui est donnée
        for(int i = 0 ; i < 5 ; i++){
            //if(!(grilleCase.get(i*5 + xOeil).getZone()==Zone.Oeil))
            pushX(force,grilleCase.get(i*5 + xOeil));
        }
    }
    public void colonneOeil(int force){  // pareil qu'en haut mais pour les colonnes

        for(int i = 0 ; i < 5 ; i++){
           // if(!(grilleCase.get(i*5 + xOeil).getZone()==Zone.Oeil))
            pushY(force,grilleCase.get(i*5 + xOeil));
        }
    }
    public  void pushX(int f, Case gc){
            gc.push(f,0);

    }
    public  void pushY(int f, Case gc){
        gc.push(0,f);

    }
    public ArrayList<Case> getGrilleCase() {
        return grilleCase;
    }
}
