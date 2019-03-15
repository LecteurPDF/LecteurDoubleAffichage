package visioreader.util;

import java.util.LinkedList;

import visioreader.vue.Vue;

/**
 * Permet de créer un nouvel Emplacement
 * Un emplacement permet à une vue d'être positionnée dans la fenêtre
 * Un emplacement à deux coordonnées :
 *  - fenetre  : 1 -> Première fenêtre
 *             : 2 -> Deuxième fenêtre
 *  - position : 1 -> Vue de Droite
 *             : 2 -> Vue de Gauche
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class Emplacement implements Comparable<Emplacement>{

    /** Nombre maximum de fenetres */
    private static final int NB_MAX_FENETRE = 2;

    /** Nommbre maximum de position possible dans une fenetre */
    private final static int NB_MAX_POSITION = 2;

    /** Donne le numéro de la fenetre */
    private int fenetre;
    /** Donne le numéro de la position dans la fenetre */
    private int position;

    /**
     * Correspond à un emplacement définit par sa fenêtre
     * et sa position dans cette dernière
     * Un Emplacement peut déjà exister dans la liste des vues
     *
     * @param fen indice de la fenêtre
     * @param pos indice de la position dans la fenêtre
     * @throws EmplacementIncorrect emplacement impossible
     */
    public Emplacement(int fen, int pos) throws EmplacementIncorrect {

        /* Verifications si les valeurs de la fenetre et de la position
         * sont dans la bonne intervalle
         */
        if(fen > NB_MAX_FENETRE && fen < 0 ) {
            throw new EmplacementIncorrect("Fenetre " + fen
                    + " N'est pas correcte, elle doit étre comprise entre 0 et "
                    + NB_MAX_FENETRE);
        }
        if(pos > NB_MAX_POSITION && pos < 0 ) {
            throw new EmplacementIncorrect("Position " + pos
                    + " N'est pas correcte, elle doit étre comprise entre 0 et "
                    + NB_MAX_POSITION);
        }

        // Définit les champs
        this.fenetre = fen;
        this.position = pos;
    }


    /**
     * valeur de fenetre
     * @return fenetre
     */
    public int getFenetre() {
        return fenetre;
    }

    /**
     * Permet de donner une nouvelle valeur à fenetre
     * @param fenetre nouvelle valeur de this.fenetre
     */
    public void setFenetre(int fenetre) {
        this.fenetre = fenetre;
    }

    /**
     * valeur de position
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Permet de donner une nouvelle valeur à position
     * @param position nouvelle valeur de this.position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /** ATTENTION PROBLEME ICI
     * Test si un Emplacement existe déjà dans la liste de vues
     * donné en argument
     * @param liste de vues dont ont veut savoir si l'element courant est dedans
     * @return	true si l'emplacement courant existe
     * 			false si l'emplacement n'existe pas
     */
    public boolean existe(LinkedList<Vue> liste) {

        for(Vue vue: liste) {
            if(this.equals(vue.getEmplacement())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Teste si deux emplacements sont égaux
     * @param aComparer Emplacement à comparé avec l'instance courante
     * @return 	true Emplacements identiques
     * 			false Emplacements different
     */
    public boolean equals(Emplacement aComparer) {

        if(this.getFenetre() != aComparer.getFenetre()) {
            return false;
        }

        if(this.getPosition() != aComparer.getPosition()) {
            return false;
        }

        return true;

    }

    @Override
    public String toString() {
        return "Fenetre " + fenetre + ";Position " + position;

    }

    @Override
    public int compareTo(Emplacement aComparer) {
        int dif;
        if( fenetre < aComparer.fenetre ) {
            dif = -1;
        } else if ( fenetre > aComparer.fenetre ) {
            dif = 1;
        } else { // fenetre == aComparer.fenetre
            if (position < aComparer.position) {
                dif = -1;
            } else if (position > aComparer.position){
                dif = 1;
            } else { // position == aComparer.position
                dif = 0;
            }
        }

        return dif;
    }

    /**
     * Donne une nouvelle valeur à this sans conserver le lien ( référence )
     * @param emplacement
     */
    public void setEmplacement(Emplacement emplacement) {
        fenetre = emplacement.getFenetre();
        position = emplacement.getPosition();

    }


}
