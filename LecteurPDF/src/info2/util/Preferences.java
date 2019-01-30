package info2.util;

import java.util.LinkedList;

import info2.lecteurpdf.Main;

/**
 * Classe préférence sous forme de Singleton
 * Qui permet de gérer et d'enregistrer les preferences
 * dans le registre grace a la Classe du JDK java.util.prefs.Preferences
 *
 * @author Alexis
 *
 */
public class Preferences {

	private static final int NB_HISTORIQUE_MAX = 5;

	private static Preferences instance;

	private java.util.prefs.Preferences prefs;

	/**
	 * Singleton
	 */
	private Preferences() {
		prefs = java.util.prefs.Preferences.userNodeForPackage(Main.class);
	}

	/**
	 * Recupére l'unique instance de l'application
	 * @return
	 */
	public static synchronized Preferences getInstance() {
		if(instance == null) {
			instance = new Preferences();
		}
		return instance;
	}

	public java.util.prefs.Preferences getPrefs() {
		return prefs;
	}

	/**
	 * Ajoute dans le registre une valeur lié a une clée
	 * @param cle Cle pour retrouevr la valeur
	 * @param valeur Valeur a ajouter
	 */
	public void put(String cle, String valeur) {
		prefs.put(cle, valeur);
	}

	public String get(String cle, String def) {
		return prefs.get(cle, def);
	}

	public void putDernierFichier(String def) {
		int i;
		for(i = 0; prefs.get("DERNIER_FICHIER_" + i, null) != null && i <= NB_HISTORIQUE_MAX ; i++ );

		if(i >= NB_HISTORIQUE_MAX + 1) {
			for(int j = 0 ; j < NB_HISTORIQUE_MAX ; j++ ) {
				prefs.put("DERNIER_FICHIER_" + (j + 1), def);
				System.out.println("--->" + j);
			}
			i = 0;
		}

		prefs.put("DERNIER_FICHIER_" + i, def);
		System.out.println(i);
	}

	public LinkedList<String> getDerniersFichiers() {
		LinkedList<String> fichiers = new LinkedList<String>();
		for(int i = 0; i < NB_HISTORIQUE_MAX; i++) {
			fichiers.add(prefs.get("DERNIER_FICHIER_" + i, null));
		}
		return fichiers;
	}
}
