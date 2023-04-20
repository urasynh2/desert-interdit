public class Joueur {
    private int x,y;
    private static int id = 0;
    private int gourde;
    public Joueur(int i,int j){
        x = i; y = j;
        gourde = 3 + (int)(Math.random() * 3);
        id++;
    }
    public Joueur(){
        int k,m;
        do {
            k = (int) (Math.random() * 5);
            m = (int) (Math.random() * 5);
            System.out.println(k + " " + m);
        }while((k == 2 && m == 2));
        x = k; y = m;
        gourde = 3 + (int)(Math.random() * 3);
        id++;
    }
    public void drink(){
        if(gourde == 0)
            return;
        gourde--;
    }
    public boolean nextTo(Case c){
        if(((c.getX() - x) <= 1 && (c.getX() - x) >= -1 && c.getY() - y ==0))
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

}
