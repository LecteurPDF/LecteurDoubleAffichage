package visioreader.preference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * Controleur qui permet de gérer la fenêtre de dispostion présente dans le menu préférence
 * La fenêtre permet de choisir la disposition des prochains fichiers à ouvrir, des fichiers déjà ouverts
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurDisposition {

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private Label lab1DG;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private Label lab2DG;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private Button btn_inverserDG;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private Button btn_inverserHB;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private Label lab1HB;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private Label lab2HB;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private RadioButton radDG;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private RadioButton radHB;

    /** Valide les changements de disposition */
    @FXML
    private Button btn_valider;

    /** Annule les changements de disposition */
    @FXML
    private Button btn_annuler;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private RadioButton horizontal;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private ToggleGroup choix;

    /** TODO commenter le rôle de ce champ (attribut, rôle d'association) */
    @FXML
    private RadioButton vertical;



    /**
     * Inverse les champs contenus dans les labels
     * lab1DG
     * et
     * lab2DG
     * @param event
     */
    @FXML
    void inversionDG(ActionEvent event) {
    	String temp = lab1DG.getText();
    	lab1DG.setText(lab2DG.getText());
    	lab2DG.setText(temp);
    }

    /**
     * Inverse les champs contenus dans les labels
     * lab1HB
     * et
     * lab2HB
     * @param event
     */
    @FXML
    void inversionHB(ActionEvent event) {
    	String temp = lab1HB.getText();
    	lab1HB.setText(lab2HB.getText());
    	lab2HB.setText(temp);
    }

    /**
     * TODO commenter le rôle de cette méthode
     * @param event
     */
    @FXML
    void inversion(MouseEvent event) {

    }

    /**
     * TODO commenter le rôle de cette méthode
     * @param event
     */
    @FXML
    void valider(ActionEvent event) {

    }


}
