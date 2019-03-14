package visioreader.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import visioreader.lecteurpdf.Main;
import javafx.scene.input.KeyEvent;

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

	private LinkedList<Boolean> liaisonVue;

	/**
	 * Singleton
	 */
	private Preferences() {
		prefs = java.util.prefs.Preferences.userNodeForPackage(Main.class);
		liaisonVue = new LinkedList<>();
		liaisonVue.add(true);
		liaisonVue.add(true);
		liaisonVue.add(true);
		liaisonVue.add(true);
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

	/**
	 * Initialise le registre avec des valeurs par defaut
	 */
	public void initialiserRegistre() {
    	if((get("TOUCHE_PAGE_SUIVANTE", "")).equals("")) {
    		put("TOUCHE_PAGE_SUIVANTE", "Ctrl+Right");
    	}

    	if((get("TOUCHE_PAGE_PRECEDENTE", "")).equals("")) {
    		put("TOUCHE_PAGE_PRECEDENTE", "Ctrl+Left");
    	}

    	if((get("TOUCHE_PLEIN_ECRAN_1", "")).equals("")) {
    		put("TOUCHE_PLEIN_ECRAN_1", "Ctrl+F5");
    	}

    	if((get("TOUCHE_PLEIN_ECRAN_2", "")).equals("")) {
    		put("TOUCHE_PLEIN_ECRAN_2", "F5");
    	}

	}

	public void putDernierFichier(String def) {
		int i;
		for(i = 0; prefs.get("DERNIER_FICHIER_" + i, null) != null && i < NB_HISTORIQUE_MAX ; i++ );

		for(int j = i ; j > 0 ; j-- ) {
			prefs.put("DERNIER_FICHIER_" + j, prefs.get("DERNIER_FICHIER_" + (j-1), null));
		}

		prefs.put("DERNIER_FICHIER_" + 0, def);
		System.out.println(i);
	}

	public LinkedList<String> getDerniersFichiers() {
		LinkedList<String> fichiers = new LinkedList<String>();
		for(int i = 0; i < NB_HISTORIQUE_MAX && prefs.get("DERNIER_FICHIER_" + i, null) != null ; i++) {
			fichiers.add(prefs.get("DERNIER_FICHIER_" + i, null));
		}
		return fichiers;
	}

	public static String keyToString(KeyEvent event) {

		KeyCombination keyCombi;
		Modifier modif;

		if (event.isShiftDown() && !(event.getCode() == KeyCode.SHIFT)) {
			modif = KeyCombination.SHIFT_DOWN;
		} else if(event.isAltDown() && !(event.getCode() == KeyCode.ALT)) {
			modif = KeyCombination.ALT_DOWN;
		} else if (event.isControlDown() && !(event.getCode() == KeyCode.CONTROL)) {
			modif = KeyCombination.CONTROL_DOWN;
		} else if(event.isMetaDown() && !(event.getCode() == KeyCode.META)) {
			modif = KeyCombination.META_DOWN;
		} else if( event.isShortcutDown() && !(event.getCode() == KeyCode.SHORTCUT)) {
			modif = KeyCombination.SHORTCUT_DOWN;
		} else {
			return event.getCode().getName();
		}

		keyCombi = new KeyCodeCombination(event.getCode(), modif);
		return keyCombi.getName();

	}

	public LinkedList<Boolean> getVueLiee() {
		return liaisonVue;
	}

	public void setVueLiee(int indice, boolean vue) {
		liaisonVue.add(indice, vue);
	}
}
