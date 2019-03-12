package info2.util;

import java.util.LinkedList;

import info2.vue.Vue;

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
	 * Un Emplacement ne peut déjà exister dans la liste des vues
	 *
	 * @param fen indice de la fenêtre
	 * @param pos indice de la position dans la fenêtre
	 * @throws EmplacementIncorrect emplacement impossible
	 * @throws EmplacementRedondant emplacement déjà existant
	 */
	public Emplacement(int fen, int pos) throws EmplacementIncorrect, EmplacementRedondant {
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

		if(this.existe(Vue.getListeVues())) {
			throw new EmplacementRedondant();
		}

		// Définit les champs
		this.fenetre = fen;
		this.position = pos;

		// Regarde si il est seul sur la fenetre
		//TODO: a decommenter et tester
//		try {
//			this = new Emplacement(fen, pos-1);
//		} catch(EmplacementRedondant | EmplacementIncorrect e){}

	}

	public int getFenetre() {
		return fenetre;
	}

	public void setFenetre(int fenetre) {
		this.fenetre = fenetre;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Test si un Emplacement existe déjà dans la liste de vues
	 * donné en argument
	 * @param Liste de vues dont ont veut savoir si l'element courant est dedans
	 * @return	true si l'emplacement courant existe
	 * 			false si l'emplacement n'existe pas
	 */
	public boolean existe(LinkedList<Vue> liste) {

		for(Vue vue: liste) {
			if(equals(vue.getEmplacement())) {
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

	public void setEmplacement(Emplacement emplacement) {
		fenetre = emplacement.getFenetre();
		position = emplacement.getPosition();

	}


}
