package visioreader.util;

/**
 * Levé si l'emplacement existe déjà
 */
public class EmplacementRedondant extends Exception {

	/**
	 * ID de l'exception
	 */
	private static final long serialVersionUID = 6192059797819927036L;

	       /**
         * Nouvelle exception avec pour message d'erreur "Emplacement redondanté"
         */
	public EmplacementRedondant() {
		super("Emplacement redondant");
	}

}
