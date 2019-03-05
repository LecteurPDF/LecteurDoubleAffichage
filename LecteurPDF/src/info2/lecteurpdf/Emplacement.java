package info2.lecteurpdf;

import java.util.LinkedList;

public class Emplacement implements Comparable<Emplacement>{

	private static final int NB_MAX_FENETRE = 2;

	private final static int NB_MAX_POSITION = 2;

	private int fenetre;

	private int position;

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

		this.fenetre = fen;
		this.position = pos;
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


}
