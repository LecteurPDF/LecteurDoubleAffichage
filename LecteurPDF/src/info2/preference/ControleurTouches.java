package info2.preference;


import java.net.URL;
import java.util.ResourceBundle;

import info2.util.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ControleurTouches implements Initializable{

	String pageSuiv,
	pagePrec,
	pleinEcran,
	pleinEcranOut,
	ouvertureFichier = "";

	@FXML
	private Button btn_sauver;

	@FXML
	private TextField txt_pageSuiv;

	@FXML
	private TextField txt_pagePrec;

	@FXML
	private TextField txt_pleinEcran;

	@FXML
	private TextField txt_PleinEcranOut;

	@FXML
	private TextField txt_ouvertureFichier;

	// private KeyCodeCombination combineSuiv = new KeyCodeCombination(key_pageSuiv, KeyCombination.CONTROL_DOWN);


	Preferences prefs = Preferences.getInstance();

	/**
	 * Sauvegarde des changements liées aux raccourcis de touches
	 * @param event
	 */
	@FXML
	void changements(ActionEvent event) {
		//TODO: Effectuer les changements
		prefs.put("TOUCHE_PAGE_SUIVANTE", pageSuiv); //Entre dans le registre l'element
		prefs.put("TOUCHE_PAGE_PRECEDENTE", pagePrec);
		prefs.put("TOUCHE_PAGE_OUVRIR_FICHIER", ouvertureFichier);
		prefs.put("TOUCHE_PLEIN_ECRAN", pleinEcran);
		prefs.put("TOUCHE_PLEIN_ECRAN_OUT", pleinEcranOut);


	}

	@FXML
	void saveKeyFullScreen(KeyEvent event) {
		try {
			pleinEcran = Preferences.keyToString(event);
			txt_pleinEcran.setEditable(false);
			txt_pleinEcran.setText(pleinEcran);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	@FXML
	void saveKeyFullScreenOut(KeyEvent event) {
		try {
			pleinEcranOut = Preferences.keyToString(event);
			txt_PleinEcranOut.setEditable(false);
			txt_PleinEcranOut.setText(pleinEcranOut);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

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

		pleinEcran = prefs.get("TOUCHE_PLEIN_ECRAN", "");
		txt_pleinEcran.setEditable(false);
		if(pleinEcran != null)
			txt_pleinEcran.setText(pleinEcran);

		pleinEcranOut = prefs.get("TOUCHE_PLEIN_ECRAN_OUT", "");
		txt_PleinEcranOut.setEditable(false);
		if(ouvertureFichier != null)
			txt_PleinEcranOut.setText(pleinEcranOut);

	}
}