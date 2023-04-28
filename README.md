Deroulé du travail:
La grande majorité s’est faite pendant des appels audios sur discord, etant donné qu'Amine n’arrivait pas a faire fonctionner git, nous nous sommes communiquer toutes les méthodes, classes, etc. Et a chaque fois Ryan faisait les commit.

Description:
Nous n’avons pas utilisé de classes abstraites ou d’interfaces. On a preferé ecrire les classes dans des fichiers individuels différents, on a donc 7 fichiers:
Case.java, Desert.java, Joueur.java, VueDesert.java, VueJoueur.java, Legend.java et Main.java.


Case est simplement une classe qui code les cases du desert pouvant prendre plusieurs type: Normal, Oasis, FakeOasis, Tunnel, Piece
les differentes zone se reconnaissent grace a un type enuméré Zone pouvant prendre comme valeur:
Oeil,Crash,Piste,Oasis,FakeOasis,Tunnels,Piece,Normal;
au lancement du jeu on s'assure qu'il y a toujours un seul est toujours initialisé au milieu de la grille de jeu aux coordonnées(2,2);
Les pièces de la machine volante sont egalement faites avec un type enumeré: Moteur, Helice, Gouvernail, Energie et Ind (indéfini).
Quand le joueur ramasse une piece de la machine volante elle est mise dans son inventaire et est remplacée par Ind dans la grille, (qui n'est donc pas affiché sur le jeu)
La classe Case contient egalement des methodes permetant d'ensabler ou de desensabler, de faire entrer ou sortir un joeur d'une case et une methode push qui sert a faire avancer les cases dans une certaine direction et avec une certaine force et est utilisé pour quand le vent souffle.

La classe Desert contient la majorité du code.
Le desert est un Arraylist de Case