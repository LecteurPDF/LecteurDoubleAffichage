package visioreader.preference;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * Controleur qui permet de gérer la fenêtre de dispostion présente dans le menu préférence
 * La fenêtre permet de choisir la disposition des prochains fichiers à ouvrir, des fichiers déjà ouverts
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurDisposition implements Initializable {

    /** Bouton qui permet de valider */
    @FXML
    private Button btn_valider;

    /** Bouton qui permet d'annuler */
    @FXML
    private Button btn_annuler;

    /** RadioBouton qui permet de passer en affichage 1:1 */
    @FXML
    private RadioButton btn11;

    /** Groupe btn11 et btn121 */
    @FXML
    private ToggleGroup choix;

    /** RadioBouton qui permet de passer en affichage 12:1 */
    @FXML
    private RadioButton btn121;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn11.setSelected(true);

    }

    /**
     * TODO commenter le rôle de cette méthode
     * @param event
     */
    @FXML
    void changements(ActionEvent event) {
        //System.out.println("coucou");

    }

}
