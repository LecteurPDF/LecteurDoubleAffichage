package info2.lecteurpdf;

public class EmplacementIncorrect extends Exception {

	public EmplacementIncorrect() {
		super("Emplacement déja occupé ou erroné");
	}

	public EmplacementIncorrect(String texte) {
		super(texte);
	}

}
