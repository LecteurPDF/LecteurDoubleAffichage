package visioreader.preference;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import visioreader.util.Preferences;

/**
 * Controleur qui permet de gérer la fenêtre des options avancées présentes dans le menu préférence
 * La fenêtre permet de choisir :
 *   - La qualité du fichier à ouvrir
 *   - Quelles sont les vues liées entre elles
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurOption implements Initializable{

    /** Permet d'accéder aux préférences de l'utilisateurs ( écrites dans le registre ) */
    private Preferences prefs = Preferences.getInstance();

    /** Représente en DPI ( faible, 100 ) la qualité de l'image qui sera affichée ( page pdf ) */
    private final float QUALITE_FAIBLE = 100;

    /** Représente en DPI ( moyenne, 150 ) la qualité de l'image qui sera affichée ( page pdf ) */
    private final float QUALITE_MOYENNE = 150;

    /** Représente en DPI ( haute, 300 ) la qualité de l'image qui sera affichée ( page pdf ) */
    private final float QUALITE_HAUTE = 300;

    /** Valide les changements des options */
    @FXML
    private Button btn_sauver;

    /** Permet de sélectionner la qualité faible */
    @FXML
    private RadioButton rbtn_qualFaible;

    /** Permet de sélectionner la qualité moyenne */
    @FXML
    private RadioButton rbtn_qualMoy;

    /** Permet de sélectionner la qualité haute */
    @FXML
    private RadioButton rbtn_qualHaute;

    /** Regroupe les différents RadioButton concernant la qualité ( rbtn_qualFaible, rbtn_qualMoy, rbtn_qualHaute )  */
    @FXML
    private ToggleGroup qualite;

    /** TODO */
    @FXML
    private Label lbl_combiSuiv;

    /** TODO */
    @FXML
    private Label lbl_combiPrec;

    /** Sélectionne la vue A */
    @FXML
    private CheckBox vueA;

    /** Sélectionne la vue B */
    @FXML
    private CheckBox vueB;

    /** Sélectionne la vue C */
    @FXML
    private CheckBox vueC;

    /** Sélectionne la vue D */
    @FXML
    private CheckBox vueD;

    /**
     * Permet de sauvegarder les changements effectués 'btn_sauver'
     * @param event
     */
    @FXML
    void changements(ActionEvent event) {

        /* Modification de la qualité */
        String valQual = qualite.getSelectedToggle().getUserData().toString();

        if(valQual.equals("faible")) {
            valQual = Float.toString(QUALITE_FAIBLE);
        } else if(valQual.equals("moyen")) {
            valQual = Float.toString(QUALITE_MOYENNE);
        } else if(valQual.equals("haute")) {
            valQual = Float.toString(QUALITE_HAUTE);
        }
        prefs.put("QUALITE", valQual);

        /* Permet de lier les vues dont la checkbow à été cochée */
        LinkedList<Boolean> vueLiees = new LinkedList<Boolean> ();
        vueLiees.add(0, vueA.isSelected());
        vueLiees.add(1, vueB.isSelected());
        vueLiees.add(2, vueC.isSelected());
        vueLiees.add(3, vueD.isSelected());
        prefs.setVueLiee(vueLiees);


        ((Stage) btn_sauver.getScene().getWindow()).close(); // fermeture fenêtre préférence
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        rbtn_qualFaible.setUserData("faible");
        rbtn_qualMoy.setUserData("moyen");
        rbtn_qualHaute.setUserData("haute");

        if(prefs.get("QUALITE", "").equals(Float.toString(QUALITE_FAIBLE))) {
            rbtn_qualFaible.setSelected(true);
        } else if(prefs.get("QUALITE", "").equals(Float.toString(QUALITE_HAUTE))){
            rbtn_qualHaute.setSelected(true);
        } else {
            rbtn_qualMoy.setSelected(true);
        }

        vueA.setSelected(prefs.getVueLiee().get(0));
        vueB.setSelected(prefs.getVueLiee().get(1));
        vueC.setSelected(prefs.getVueLiee().get(2));
        vueD.setSelected(prefs.getVueLiee().get(3));


        lbl_combiSuiv.setText( prefs.get("TOUCHE_PAGE_SUIVANTE", ""));
        lbl_combiPrec.setText( prefs.get("TOUCHE_PAGE_PRECEDENTE", ""));
    }

}
