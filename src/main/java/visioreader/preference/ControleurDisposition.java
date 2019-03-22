package visioreader.preference;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import visioreader.util.Emplacement;
import visioreader.util.EmplacementIncorrect;
import visioreader.util.Preferences;
import visioreader.vue.Vue;

/**
 * Controleur qui permet de gérer la fenêtre de dispostion présente dans le menu préférence
 * La fenêtre permet de choisir la disposition des prochains fichiers à ouvrir, des fichiers déjà ouverts
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurDisposition implements Initializable {

    @FXML
    private Button btn_valider;

    @FXML
    private Button btn_annuler;

    @FXML
    private RadioButton btn11;

    @FXML
    private ToggleGroup choix;

    @FXML
    private RadioButton btn121;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn11.setSelected(true);

	}

	@FXML
    void changements(ActionEvent event) {
		//System.out.println("coucou");

    }

}
