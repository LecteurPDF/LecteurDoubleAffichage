package info2.lecteurpdf;

public class Emplacement {

	private int fenetre;

	private int position;
	
	public Emplacement(int fen, int pos) throws EmplacementIncorrect {
		//TODO: verifications d'existences + valeurs correctes
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



}
