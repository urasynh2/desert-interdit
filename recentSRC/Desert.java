import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Desert {
    private final ArrayList<Joueur> joueurs;
    private final ArrayList<Case> grilleCase; // La grille qui nous permet de joueur et de display des cases dessus
    private int joueurId = 0; //?
    static int xOeil = 2,yOeil =2;
    static public float nivTempete = 2;
    private int nbAction = 0;
    private static final ArrayList<Zone> caseType = new ArrayList<>(Arrays.asList(
            Zone.Normal,Zone.Normal,Zone.Normal,Zone.Normal,Zone.Normal,Zone.Normal,Zone.Normal,Zone.Normal,Zone.Normal,
            Zone.Oasis,Zone.Oasis,Zone.FakeOasis,
            Zone.Crash,Zone.Piste,
            Zone.Piece,Zone.Piece,Zone.Piece,Zone.Piece,Zone.Piece,Zone.Piece,Zone.Piece,Zone.Piece,
            Zone.Tunnels,Zone.Tunnels)
    ); // talbleau représentant les cases disponibles dans le jeu

    public Desert(){
        joueurs = new ArrayList<>();
        for(int i = 0 ; i < 4 ; i++){
            joueurs.add(new Joueur(i));
        }
        Collections.shuffle(caseType);
        caseType.add(12,Zone.Oeil);
        grilleCase = initGrille();
    }
    public Joueur playingJoueur(){
        /**
         * Getter trivial du Joueur actuelle
         */
       return joueurs.get(joueurId);
    }
    public void nextPlayer(){
        /**
         * Méthode permettant de switcher de joueur de manière non aléatoire et continu.
         * Doit etre appelée a chaque fois qu'une action d'un joueur est effectuée.
         */
        //System.out.println(playingJoueur().getX());
        if(++joueurId==4)
            joueurId = 0;
        //System.out.println(playingJoueur().getX());
    }
    public int totalSandLevel(){
        /**
         * Methode qui permete de calculer tous les niveaux de sables présent dans la grille,
         * Afin de vérifier l'une des conditions de gameOver()
         */
        int k = 0;
        for(Case c : grilleCase){
            k += c.getNiveau();
        }return k;
    }
    private ArrayList<Case> initGrille(){ //Initialisation des grilles  pour le moment sans notion de différentes formes de zones
        ArrayList<Case> a = new ArrayList<>();

        for(int j = 0; j < 5 ; j++)
            for(int i = 0; i < 5 ; i++){
                if(!(i==2 && j == 2)) // on place correctement les tonnes de sable respective comme désirée dans le sujet
                    if((j == i && i == 1 | i == 3) | ( j ==2 && i == 0 | i ==4) | (j==4|j == 0 && i ==2 )||(j==1 && i==3)||(j==3 && i==1) )
                        a.add(new Case(i,j,1,caseType.get(i+j*5)));
                    else a.add(new Case(i,j,0,caseType.get(i+j*5)));

                else a.add(new Case(xOeil,yOeil,0,caseType.get(i+j*5)));
            }
        for(Joueur j : joueurs){ // on ajoute les différetns joueurs au terrain en prenant en compte qu'il ne doivent pas etre dans l'oeil
            if((j.getX()>= 2 && j.getY()>=2 ) || j.getY()>=3)
             a.get(j.getX() + j.getY() * 5).enterPlayer(j);
            else a.get(j.getX()  + j.getY() *5).enterPlayer(j);
        }
        return a;
    }

    public void goOutCase(Joueur j){
        grilleCase.get(j.getX() + j.getY()*5).sortPlayer(j);
    }
    public void explore(){
        switch(grilleCase.get( playingJoueur().getX() + playingJoueur().getY()*5 ).getZone()){
            case Oasis -> {
                for(Joueur j : grilleCase.get( playingJoueur().getX() + playingJoueur().getY()*5 ).getContenu())
                    j.onOasis();
            }case FakeOasis ->{
                grilleCase.get( playingJoueur().getX() + playingJoueur().getY()*5 ).setExploredZone();
            }
            case Tunnels -> {
                goOutCase(playingJoueur());
                getOtherTunnel(grilleCase.get(playingJoueur().getX() + playingJoueur().getY()*5)).enterPlayer(playingJoueur());

            }

        }
    }
    public int action(){
        /**
         *  méthode qui produit une action dans le désert parmis les trois possibles
         *  apres que le vent ait soufflée il faut impérativement un tri de grilleCase afin de
         *  remettre en place les cases pour ne pas aux itérations suivantes manipuler les mauvaises cases
         */
        int k = (int)(Math.random() *3);
        switch (k){
            case 0 -> {
                int a = leVentSouffle();
                sortGrille();
                return a;
            }
            case 1 ->{ //augmentation du niveau de la tempete
                augmentLevel();
                return 0;
            }
            default -> {
                heatWave();
                return 0;
            }
        }
    }
    public int getNbAction(){
        return nbAction;
    }
    public void increNbAction(){
        nbAction++;
    }
    public void resetNbAction(){
        nbAction = 0;
    }
    public void update(){
    /**
        ArrayList<Case> newGrille = new ArrayList<>(10000);
        while(!grilleCase.isEmpty()) {
            for (int j = 0; j < 5; j++){
                for (int i = 0; i < 5; i++) {
                    if (grilleCase.get(i + j * 5).getX() == i && grilleCase.get(i + j * 5).getY() == j)
                        newGrille.add(grilleCase.get(i + j * 5));

                }
            }if(newGrille.size() == 24){
                grilleCase.removeAll(grilleCase);
            }
        }
        grilleCase = newGrille;
    */}
    public void sortGrille(){

        Collections.sort(grilleCase);
    }
    public void augmentLevel() {
        nivTempete += 0.5;
    }
    public void heatWave(){
        for(Joueur j : joueurs){
            if (!( grilleCase.get(j.getX() + j.getY() *5 ).getZone() == Zone.Tunnels))
                j.drink();
        }
    }
    public int leVentSouffle(){
        int force = 1+ (int)(Math.random() *3);

        float rand = (float)Math.random();
        System.out.println(rand + "<1 et  1= " + force);
        if(rand<0.5 && rand>0.25 ) {
            ligneOeil(force);
            return force;
        }
        else if(rand<0.25) {
            ligneOeil(force);
            return force;
        }
        else if (rand>0.75) {
            colonneOeil(force);
            return -force;
        }
        else {
            colonneOeil(force);
            return -force;
        }
    }
    public void ligneOeil(int force){ // méthode qui déplace la ligne de l'oeil dépendant de la force qui est donnée
        for(int i = 0 ; i < 5 ; i++){
            //if(!(grilleCase.get(i*5 + xOeil).getZone()==Zone.Oeil))
            grilleCase.get(i +5* yOeil).ensabler();
            pushX(force,grilleCase.get(i +5* yOeil));

        }
    }
    public void colonneOeil(int force){  // pareil qu'en haut mais pour les colonnes

        for(int i = 0 ; i < 5 ; i++){
           // if(!(grilleCase.get(i*5 + xOeil).getZone()==Zone.Oeil))
            grilleCase.get(i*5 + xOeil).ensabler();
            pushY(force,grilleCase.get(i*5 + xOeil));

        }
    }
    public  void pushX(int f, Case gc){

        gc.push(f,0);
        if(gc.getZone()==Zone.Oeil)
            xOeil = gc.getX();
        if(!gc.getContenu().isEmpty()){
            for(Joueur j : gc.getContenu()){
                j.enter(gc);
            }
        }
    }
    public  void pushY(int f, Case gc){
        gc.push(0,f);
        if(gc.getZone()==Zone.Oeil)
            yOeil = gc.getY();
        if(!gc.getContenu().isEmpty()){
            for(Joueur j : gc.getContenu()){
                j.enter(gc);
            }
        }
    }
    public boolean gameOver(){
        return totalSandLevel()>=43 || nivTempete>=7 || deHydrated();
    }

    public boolean deHydrated(){
        for(Joueur j : joueurs){
            if(j.getGourde() == 0)
                return true;
        }return false;
    }
    public ArrayList<Case> getGrilleCase() {
        return grilleCase;
    }
    public ArrayList<Joueur> getJoueurs(){
        return joueurs;
    }
    public Case getOtherTunnel(Case c){
        /**
         * Récupere l'autre bout du tunnel afin de téléporter le joueur dans cette case si et seulement s'il explore le premier tunnel
         */
        for(Case ca : grilleCase){
            if(c.getZone() == ca.getZone() && (c.getX() != ca.getX()) && (c.getY() != ca.getY()))
                return ca;
        }throw new RuntimeException("Aucun autre tunnel repéré");
    }
    public void giveWater(Joueur j ){

        for(Joueur jo : joueurs){

        }
    }
}