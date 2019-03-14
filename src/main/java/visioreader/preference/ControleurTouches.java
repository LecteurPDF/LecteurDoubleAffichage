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
import visioreader.lecteurpdf.Main;
import visioreader.util.Preferences;

public class ControleurTouches implements Initializable{

	String pageSuiv,
	pagePrec,
	pleinEcran1,
	pleinEcran2,
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
		prefs.put("TOUCHE_PLEIN_ECRAN_1", pleinEcran1);
		prefs.put("TOUCHE_PLEIN_ECRAN_2", pleinEcran2);


		((Stage)btn_sauver.getScene().getWindow()).close();
		
	}

	/**
	 * Restauration des combinaisons de touches par 
	 * défaut definit dans le Main
	 * @param event
	 */
	@FXML
	void restauration(ActionEvent event) {
		prefs.initialiserRegistre();
	}

	@FXML
	void saveKeyFullScreen(KeyEvent event) {
		try {
			pleinEcran1 = Preferences.keyToString(event);
			txt_pleinEcran.setEditable(false);
			txt_pleinEcran.setText(pleinEcran1);
		} catch(IllegalArgumentException e) {
			//e.printStackTrace();
		}
	}

	@FXML
	void saveKeyFullScreenOut(KeyEvent event) {
		try {
			pleinEcran2 = Preferences.keyToString(event);
			txt_PleinEcranOut.setEditable(false);
			txt_PleinEcranOut.setText(pleinEcran2);
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

		pleinEcran1 = prefs.get("TOUCHE_PLEIN_ECRAN_1", "");
		txt_pleinEcran.setEditable(false);
		if(pleinEcran1 != null)
			txt_pleinEcran.setText(pleinEcran1);

		pleinEcran2 = prefs.get("TOUCHE_PLEIN_ECRAN_2", "");
		txt_PleinEcranOut.setEditable(false);
		if(pleinEcran2 != null)
			txt_PleinEcranOut.setText(pleinEcran2);

	}
}