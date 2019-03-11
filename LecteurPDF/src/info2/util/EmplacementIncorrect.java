package info2.util;

/**
 * Levé si emplacement incoherent
 *
 */
public class EmplacementIncorrect extends Exception {

	/**
	 * ID de l'exception
	 */
	private static final long serialVersionUID = 5971289591606286175L;

	public EmplacementIncorrect() {
		super("Emplacement déja occupé ou erroné");
	}

	public EmplacementIncorrect(String texte) {
		super(texte);
	}

}
