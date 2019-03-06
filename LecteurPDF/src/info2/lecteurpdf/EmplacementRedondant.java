package info2.lecteurpdf;

/**
 * Levé si l'emplacement existe déjà
 */
public class EmplacementRedondant extends Exception {

	/**
	 * ID de l'exception
	 */
	private static final long serialVersionUID = 6192059797819927036L;

	public EmplacementRedondant() {
		super("Emplacement redondant");
	}

}
