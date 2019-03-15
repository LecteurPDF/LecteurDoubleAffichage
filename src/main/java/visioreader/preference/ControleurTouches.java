package visioreader.preference;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import visioreader.util.Preferences;

/**
 * Controleur qui permet de gérer la fenêtre de raccourcis clavier de l'application dans le menu préférence
 * La fenêtre permet de choisir les différents raccours claviers de l'application
 * Un raccourci peut avoir la forme suivant" :
 *   - N'importe quelle touche, exemple 'K','P','5', ..
 *   - Une combinaison commancant par 'CTRL', 'SHIFT' OU 'CAPS LOCK' + N'importe quelle touche différente
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurTouches implements Initializable{

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

	// private KeyCodeCombination combineSuiv = new KeyCodeCombination(key_pageSuiv, KeyCombination.CONTROL_DOWN);

	/** Stocke les préférences actuelles */
	Preferences prefs = Preferences.getInstance();

	/**
	 * Sauvegarde des changements liées aux raccourcis de touches
	 * @param event
	 */
	@FXML
	void changements(ActionEvent event) {
		prefs.put("TOUCHE_PAGE_SUIVANTE", pageSuiv); //Entre dans le registre l'element
		prefs.put("TOUCHE_PAGE_PRECEDENTE", pagePrec);
		prefs.put("TOUCHE_PAGE_OUVRIR_FICHIER", ouvertureFichier);
		prefs.put("TOUCHE_PLEIN_ECRAN_1", pleinEcran1);
		prefs.put("TOUCHE_PLEIN_ECRAN_2", pleinEcran2);


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
			pleinEcran1 = Preferences.keyToString(event);
			txt_pleinEcran1.setEditable(false);
			txt_pleinEcran1.setText(pleinEcran1);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//TODO: Faire une boucle pour eviter de copié coller
		pageSuiv = prefs.get("TOUCHE_PAGE_SUIVANTE", "");
		txt_pageSuiv.setEditable(false);
		if(pageSuiv != null)
			txt_pageSuiv.setText(pageSuiv);


		pagePrec = prefs.get("TOUCHE_PAGE_PRECEDENTE", "");
		txt_pagePrec.setEditable(false);
		if(pagePrec != null)
			txt_pagePrec.setText(pagePrec);

		ouvertureFichier = prefs.get("TOUCHE_PAGE_OUVRIR_FICHIER", "");
		txt_ouvertureFichier.setEditable(false);
		if(ouvertureFichier != null)
			txt_ouvertureFichier.setText(ouvertureFichier);

		pleinEcran1 = prefs.get("TOUCHE_PLEIN_ECRAN_1", "");
		txt_pleinEcran1.setEditable(false);
		if(pleinEcran1 != null)
			txt_pleinEcran1.setText(pleinEcran1);

		pleinEcran2 = prefs.get("TOUCHE_PLEIN_ECRAN_2", "");
		txt_pleinEcran2.setEditable(false);
		if(pleinEcran2 != null)
			txt_pleinEcran2.setText(pleinEcran2);

	}
}