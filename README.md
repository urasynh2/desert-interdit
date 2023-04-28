Deroulé du travail:
La grande majorité s’est faite pendant des appels audios sur discord, etant donné qu'Amine n’arrivait pas a faire fonctionner git,
nous nous sommes communiquer toutes les méthodes, classes, etc. Et a chaque fois Ryan faisait les commit.
Finir le projet a pris a peu pres 4 apres midis/soirées. La repartition s'est faite arbitrairement, on a tout les 2 travailler sur un peu de tout.


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
Le desert est un Arraylist de Case, donc pour acceder à une case de coordonnées (a,b) on  fait a*b*5,
a chaque action unen attribut nbaction est decrementé et quand il atteint 0 le joueur doit finir son tour
Les methodes colonneoeil et ligneoeil permettent au vent de deplacer des cases sur la ligne ou colonne ou se trouve l'oeil de la tempete.

La classe Joueur est simple contient getter setter, des methodes qui premettent de savoir si un joueur est a proximité d'une case donnée.

Les case Vuedesert et VueJoueur sont tout simplement des classes qui affichent le jeu
et la classe Legende affiche le code couleur pour les differentes classes (bleu pour oasis, etc)

et Le Main lance tout simplement le programme entier.

Difficultés rencontrées:
La comprehension du sujet, les regles n'etaient toujours simples a comprendre on a du voir plusieurs videos sur le jeu de sociéte pour comprendre
son fonctionnement.


Conclusion:
C'etait un projet tres interessant, un peu compliqué, on a appris des choses de nous memes comme les classes comparables par exemple
si il fallait le refaire, je crois que le plus grods changement serait de suivre le diagramme qu'on avait effectué en classe