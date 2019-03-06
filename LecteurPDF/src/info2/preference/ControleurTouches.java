package info2.preference;


import info2.util.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class ControleurTouches {

	String pageSuiv,
		   pagePrec,
		   pleinEcran,
		   pleinEcranOut,
		   ouvertureFichier;

	KeyCode key_pageSuiv,
		    key_pagePrec,
		    key_pleinEcran,
		    key_pleinEcranOut,
		    key_ouvertureFichier;

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
    	prefs.put("TOUCHE_PAGE_SUIVANTE", pageSuiv);
    	prefs.put("TOUCHE_PAGE_PRECEDENTE", pagePrec);
    	prefs.put("TOUCHE_PLEIN_ECRAN", pleinEcran);
    	prefs.put("TOUCHE_PLEIN_ECRAN_OUT", pleinEcranOut);
    	prefs.put("TOUCHE_PAGE_OUVRIR_FICHIER", ouvertureFichier);

    }

    @FXML
    void saveKeyFullScreen(KeyEvent event) {
    	txt_pleinEcran.setEditable(false);
    	txt_pleinEcran.setText(event.getCode().getName());
    	key_pleinEcran = event.getCode();
    	pleinEcran = event.getCode().getName();
    }

    @FXML
    void saveKeyFullScreenOut(KeyEvent event) {
    	txt_PleinEcranOut.setEditable(false);
    	txt_PleinEcranOut.setText(event.getCode().getName());
    	key_pleinEcranOut = event.getCode();
    	pleinEcranOut = event.getCode().getName();
    }

    @FXML
    void saveKeyOpen(KeyEvent event) {
    	txt_ouvertureFichier.setEditable(false);
    	txt_ouvertureFichier.setText(event.getCode().getName());
    	key_ouvertureFichier = event.getCode();
    	ouvertureFichier = event.getCode().getName();
    }

    @FXML
    void saveKeyPrec(KeyEvent event) {
    	txt_pagePrec.setEditable(false);
    	txt_pagePrec.setText(event.getCode().getName());
    	key_pagePrec = event.getCode();
    	pagePrec = event.getCode().getName();
    }

    @FXML
    void saveKeySuiv(KeyEvent event) {
    	txt_pageSuiv.setEditable(false);
    	txt_pageSuiv.setText(event.getCode().getName());
    	key_pageSuiv = event.getCode();
    	pageSuiv = event.getCode().getName();
    }
}