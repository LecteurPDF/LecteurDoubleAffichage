package visioreader.preference;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import visioreader.util.Preferences;

/**
 * Controleur qui permet de gérer la fenêtre de raccourcis clavier de l'application dans le menu préférence
 * La fenêtre permet de choisir les différents raccours claviers de l'application
 * Un raccourci peut avoir la forme suivant" :
 *   - N'importe quelle touche, exemple 'K','P','5', ..
 *   - Une combinaison commancant par 'CTRL', 'SHIFT', 'ALT', 'SHORTCUT' OU 'CAPS LOCK' + N'importe quelle touche différente
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurTouches implements Initializable{

	/** tableau qui répertorie les combinaisons de touches */
	String[] listePrefs;

	/** tableau qui répertorie les champs de texte */
	TextField[] listeTextePrefs;

	/** Le Keycode de la page suivante */
	String pageSuiv;

	/** Le Keycode de la page précédente */
	String pagePrec;

	/** Le Keycode du plein écran de la fenêtre principale */
	String pleinEcran1;

	/** Le Keycode du plein écran de la fenêtre secondaire */
	String pleinEcran2;

	/** Le Keycode qui permet d'ouvrir un nouveau fichier */
	String ouvertureFichier = "";

	/** Le Keycode de chaque vue du lecteur pour page suivante et précédente */
	String pageSuivA, pagePrecA, pageSuivB, pagePrecB,
		   pageSuivC, pagePrecC, pageSuivD, pagePrecD;

	/** Sauvegarde les raccours claviers affectés */
	@FXML
	private Button btn_sauver;

	/** Affiche le raccourci clavier pour : page suivante*/
	@FXML
	private TextField txt_pageSuiv;

	/** Affiche le raccourci clavier pour : page précédente */
	@FXML
	private TextField txt_pagePrec;

	/** Affiche le raccourci clavier pour : plein ecran principal */
	@FXML
	private TextField txt_pleinEcran1;

	/** Affiche le raccourci clavier pour : plein ecran secondaire */
	@FXML
	private TextField txt_pleinEcran2;

	/** Affiche le raccourci clavier pour : ouverture fichier */
	@FXML
	private TextField txt_ouvertureFichier;

	/** Affiche le raccourcis clavier pour : page suivante/précédente
	 * de chaque vue individuellement.
	 */
	@FXML
    private TextField txt_suivA, txt_precA, txt_suivB, txt_precB,
    				  txt_suivC, txt_precC, txt_suivD, txt_precD;

	// private KeyCodeCombination combineSuiv = new KeyCodeCombination(key_pageSuiv, KeyCombination.CONTROL_DOWN);

	/** Stocke les préférences actuelles */
	Preferences prefs = Preferences.getInstance();

	/**
	 * Sauvegarde des changements liées aux raccourcis de touches
	 * @param event
	 */
	@FXML
	void changements(ActionEvent event) {
		prefs.put("TOUCHE_PAGE_SUIVANTE", listePrefs[0]); //Entre dans le registre l'element
		prefs.put("TOUCHE_PAGE_PRECEDENTE", listePrefs[1]);
		prefs.put("TOUCHE_PAGE_OUVRIR_FICHIER", listePrefs[2]);
		prefs.put("TOUCHE_PLEIN_ECRAN_1", listePrefs[3]);
		prefs.put("TOUCHE_PLEIN_ECRAN_2", listePrefs[4]);

		prefs.put("TOUCHE_PAGE_SUIVANTE_A", listePrefs[5]);
		prefs.put("TOUCHE_PAGE_PRECEDENTE_A", listePrefs[6]);

		prefs.put("TOUCHE_PAGE_SUIVANTE_B", listePrefs[7]);
		prefs.put("TOUCHE_PAGE_PRECEDENTE_B", listePrefs[8]);

		prefs.put("TOUCHE_PAGE_SUIVANTE_C", listePrefs[9]);
		prefs.put("TOUCHE_PAGE_PRECEDENTE_C", listePrefs[10]);

		prefs.put("TOUCHE_PAGE_SUIVANTE_D", listePrefs[11]);
		prefs.put("TOUCHE_PAGE_PRECEDENTE_D", listePrefs[12]);


		((Stage)btn_sauver.getScene().getWindow()).close();

	}

	/**
	 * Restauration des combinaisons de touches par
	 * défaut definit dans le Main ( au lancement de l'application )
	 * @param event
	 */
	@FXML
	void restauration(ActionEvent event) {
		prefs.initialiserRegistre();
	}

	/**
	 * Sauvegarde la combinaison de touche pour :  le plein Ecran de la fenêtre principale
	 * @param event
	 */
	@FXML
	void saveKeyFullScreenUn(KeyEvent event) {
		try {
			listePrefs[3] = Preferences.keyToString(event);
			listeTextePrefs[3].setEditable(false);
			listeTextePrefs[3].setText(listePrefs[3]);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

        /**
         * Sauvegarde la combinaison de touche pour : le plein Ecran de la fenêtre secondaire
         * @param event
         */
	@FXML
	void saveKeyFullScreenDeux(KeyEvent event) {
		try {
			pleinEcran2 = Preferences.keyToString(event);
			txt_pleinEcran2.setEditable(false);
			txt_pleinEcran2.setText(pleinEcran2);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

        /**
         * Sauvegarde la combinaison de touche pour : l'ouverture de fichier
         * @param event
         */
	@FXML
	void saveKeyOpen(KeyEvent event) {
		try {
			ouvertureFichier = Preferences.keyToString(event);
			txt_ouvertureFichier.setEditable(false);
			txt_ouvertureFichier.setText(ouvertureFichier);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

    /**
     * Sauvegarde la combinaison de touche pour : la page précédente
     * @param event
     */
	@FXML
	void saveKeyPrec(KeyEvent event) {
		try {
			pagePrec = Preferences.keyToString(event);
			txt_pagePrec.setEditable(false);
			txt_pagePrec.setText(pagePrec);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

    /**
     * Sauvegarde la combinaison de touche pour : la page suivante
     * @param event
     */
	@FXML
	void saveKeySuiv(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pageSuiv = Preferences.keyToString(event);
			txt_pageSuiv.setEditable(false);
			txt_pageSuiv.setText(pageSuiv);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	/**
     * Sauvegarde la combinaison de touche pour : la page suivante pour la vue A
     * @param event
     */
	@FXML
	void saveKeySuivA(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pageSuivA = Preferences.keyToString(event);
			txt_suivA.setEditable(false);
			txt_suivA.setText(pageSuivA);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	/**
     * Sauvegarde la combinaison de touche pour : la page suivante pour la vue B
     * @param event
     */
	@FXML
	void saveKeySuivB(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pageSuivB = Preferences.keyToString(event);
			txt_suivB.setEditable(false);
			txt_suivB.setText(pageSuivB);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	/**
     * Sauvegarde la combinaison de touche pour : la page suivante pour la vue C
     * @param event
     */
	@FXML
	void saveKeySuivC(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pageSuivC = Preferences.keyToString(event);
			txt_suivC.setEditable(false);
			txt_suivC.setText(pageSuivC);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	/**
     * Sauvegarde la combinaison de touche pour : la page suivante pour la vue D
     * @param event
     */
	@FXML
	void saveKeySuivD(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pageSuivD = Preferences.keyToString(event);
			txt_suivD.setEditable(false);
			txt_suivD.setText(pageSuivD);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	/**
     * Sauvegarde la combinaison de touche pour : la page précédente A
     * @param event
     */
	@FXML
	void saveKeyPrecA(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pagePrecA = Preferences.keyToString(event);
			txt_precA.setEditable(false);
			txt_precA.setText(pagePrecA);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	/**
     * Sauvegarde la combinaison de touche pour : la page précédente B
     * @param event
     */
	@FXML
	void saveKeyPrecB(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pagePrecB = Preferences.keyToString(event);
			txt_precB.setEditable(false);
			txt_precB.setText(pagePrecB);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}


	/**
     * Sauvegarde la combinaison de touche pour : la page précédente C
     * @param event
     */
	@FXML
	void saveKeyPrecC(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pagePrecC = Preferences.keyToString(event);
			txt_precC.setEditable(false);
			txt_precC.setText(pagePrecC);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	/**
     * Sauvegarde la combinaison de touche pour : la page précédente D
     * @param event
     */
	@FXML
	void saveKeyPrecD(KeyEvent event) {

		/* Convertion de l'evenement en combinaison ou en touche en String*/
		try {
			pagePrecD = Preferences.keyToString(event);
			txt_precD.setEditable(false);
			txt_precD.setText(pagePrecD);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		txt_ouvertureFichier.setFocusTraversable(false);
		txt_pagePrec.setFocusTraversable(false);
		txt_pageSuiv.setFocusTraversable(false);
		txt_pleinEcran1.setFocusTraversable(false);
		txt_pleinEcran2.setFocusTraversable(false);

		/* initialisaion de la liste des préférences */
		listePrefs = new String[] {
				prefs.get("TOUCHE_PAGE_SUIVANTE", ""),
				prefs.get("TOUCHE_PAGE_PRECEDENTE", ""),
				prefs.get("TOUCHE_PAGE_OUVRIR_FICHIER", ""),
				prefs.get("TOUCHE_PLEIN_ECRAN_1", ""),
				prefs.get("TOUCHE_PLEIN_ECRAN_2", ""),
				prefs.get("TOUCHE_PAGE_SUIVANTE_A", ""),
				prefs.get("TOUCHE_PAGE_PRECEDENTE_A", ""),
				prefs.get("TOUCHE_PAGE_SUIVANTE_B", ""),
				prefs.get("TOUCHE_PAGE_PRECEDENTE_B", ""),
				prefs.get("TOUCHE_PAGE_SUIVANTE_C", ""),
				prefs.get("TOUCHE_PAGE_PRECEDENTE_C", ""),
				prefs.get("TOUCHE_PAGE_SUIVANTE_D", ""),
				prefs.get("TOUCHE_PAGE_PRECEDENTE_D", "")

		};

		/* tableau qui répertorie les champs de texte */
		listeTextePrefs = new TextField[] {
				txt_pageSuiv,
				txt_pagePrec,
				txt_ouvertureFichier,
				txt_pleinEcran1,
				txt_pleinEcran2,
				txt_suivA,
				txt_precA,
				txt_suivB,
				txt_precB,
				txt_suivC,
				txt_precC,
				txt_suivD,
				txt_precD,
		};

		/* Applique le traitement sur les éléments de listeTextePrefs */
		for (int i=0; i < listePrefs.length; i++) {
			listeTextePrefs[i].setEditable(false);
			if(listePrefs[i] != null)
				listeTextePrefs[i].setText(listePrefs[i]);
		}
	}
}