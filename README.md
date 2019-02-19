# LecteurDoubleAffichage

<h2>Présentation du projet</h2>
<p>
  Ce projet a pour but de développer un logiciel permettant de lire des fichiers au format PDF. La spécificité de ce logiciel est qu’il pourra lire les fichiers PDF en double affichage.

  Un lecteur PDF est un logiciel qui a la capacité d’ouvrir les fichiers au format “.pdf” et de lire leur contenu en l’affichant à l’écran dans une interface graphique. Le contenu ne pourra pas être modifié par l’utilisateur une fois affiché à l’écran.

  La spécificité “Double Affichage” permet à l’utilisateur d’ouvrir deux fichiers PDF différents en même temps sur une même fenêtre/écran ou sur des fenêtres/écrans différents. 
  Une fenêtre est une interface graphique faisant affichée sur une écran, il peut y en avoir plusieurs d’ouvertes sur un même écran. Un écran peut représenter un moniteur d’ordinateur physique ou bien un système de projection (comme un vidéo projecteur).
L’affichage d’une page d’un fichier PDF sera appelé une “vue” dans le cadre de ce projet. L’utilisateur aura aussi la possibilité d’ouvrir deux vues ( de la même page ou de pages différentes ) du même fichier en même temps. Il pourra aussi choisir le décalage entre les vues affichées, si elles sont issues du même fichier.
  Il sera tout de même possible de fonctionner en mode simple affichage classique.
  
</p>

<h2>Les différents affichages possibles</h2>
<p>Il y a plusieurs affichages possibles en fonction de ce que l’utilisateur souhaite afficher.

Les divers affichages seront visibles sur des écrans, un écran est représenté par un cadre noir, il est possible d’avoir deux écrans différents.</p>

<h3>Ecran unique</h3>
<i>
“Ecran” correspond à un unique écran
“x” correspond à page actuelle
“n” correspond au décalage de pages choisi par l’utilisateur
</i>
<h4>Lecteur Simple Affichage</h4>



<p>On parle ici d’une simple fenêtre dans laquelle le contenu d’un seul fichier PDF sera visible à l’intérieur.</p>



<h4>Lecteur Double Affichage, un seul écran avec séparation verticale</h4>


<p>On parle ici d’un écran scindé horizontalement avec deux vues A et B, il y a deux cas possibles :
Soit chaque vue affiche le même fichier, à la page numéro x pour A et à la page x+n pour B.
Soit A et B font parties de deux fichiers différents.</p>



<h4>Lecteur Double Affichage, un seul écran avec séparation horizontale</h4>


<p>Cette fenêtre a les mêmes propriétés que la fenêtre précédente, mais est scindée verticalement au lieux de horizontalement.</p>


<h3>Deux Écrans</h3>

<i>“Ecran 1” correspond à un unique écran, l’écran principal
“Ecran 2“ correspond à un autre écran, l’écran secondaire
“x” correspond à page actuelle
“n” correspond au décalage choisi par l’utilisateur</i>

<h4>Lecteur Double Affichage avec deux écrans</h4>


<p>On parle ici de deux écrans, il y a deux cas possibles :
Soit chacun affiche le même fichier, mais à la page numéro x pour la vue A et la page x+n pour la vue B.
Soit A et B sont deux fichiers différents et chacun est indépendant l’un de l’autre.</p>


<h4>Lecteur Double Affichage sur le premier écran et simple sur le deuxième<h4>

<p>Dans le cas présent, nous avons deux écrans. L'écran 1 scindé en deux va permettre d’afficher les vues A et B et l'écran 2 affichera uniquement la vue B. 
Soit les vues A et B sont celles d’un seul et même fichier, mais à la page numéro x pour A et la page x+n sur les parties B. Ce qui signifie que la partie B est affichée sur deux écrans différents.
Soit A et B peuvent être deux fichiers différents.</p>


<h4>Lecteur Double Affichage sur deux écrans</h4>

<p>Ici, nous retrouvons les écrans 1 et 2. Chacun est scindé en deux, afin vont permettre d’afficher une paire A et B sur les deux écrans. 
Soit les vues A et B peuvent faire partie de fichiers identiques, réparties sur les deux écrans. Avec la page numéro x pour A et x+n pour B.
Soit chaque A et B de chaque écran peuvent faire partie de fichiers différents. Donc un total de deux fichiers différents.</p>

