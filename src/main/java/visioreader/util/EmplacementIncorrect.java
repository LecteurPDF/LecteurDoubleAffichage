package visioreader.util;

/**
 * Levé si emplacement incoherent
 *
 */
public class EmplacementIncorrect extends Exception {

	/**
	 * ID de l'exception
	 */
	private static final long serialVersionUID = 5971289591606286175L;

	/**
	 * Nouvelle exception avec pour message d'erreur "Emplacement déja occupé ou erroné"
	 */
	public EmplacementIncorrect() {
		super("Emplacement déja occupé ou erroné");
	}

	/**
	 * Nouvelle exception avec pour message d'erreur texte
	 * @param texte Le message d'erreur
	 */
	public EmplacementIncorrect(String texte) {
		super(texte);
	}

}
