package visioreader.preference;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
/**
 * Controleur qui permet de gérer la fenêtre du menu préférence
 * La fenêtre se coupe en deux parties verticales :
 *   - A droite : Les libellés des menus, au clic on charge dans la partie gauche le contenu correspondant
 *   - A gauche : Les options liées à la partie de droite sélectionnée
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurPreference implements Initializable{

    /** Permet de séparer en deux parties l'écran */
    @FXML
    private ScrollPane affichageInfos;


    /**
     * Ouvre le menu qui permet des gérer les raccourcis des touches
     * @param event
     */
    @FXML
    void optionsTouches(ActionEvent event) {
        try {
            affichageInfos.setContent(FXMLLoader.load(getClass().getResource("/layout/Touches.fxml")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre le menu qui permet des gérer les dispositions des vues
     * @param event
     */
    @FXML
    void optionDisposition(ActionEvent event) {
        try {
            affichageInfos.setContent(FXMLLoader.load(getClass().getResource("/layout/Disposition.fxml")));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Ouvre le menu qui permet des gérer les options avancées
     * @param event
     */
    @FXML
    void optionOption(ActionEvent event) {

        try {
            affichageInfos.setContent(FXMLLoader.load(getClass().getResource("/layout/Option.fxml")));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    /**
     * Detecte le survol du bouton du menu ( change la couleur au survol )
     * @param event
     */
    @FXML
    void survolEntreeBouton(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color:#f17d49;-fx-text-fill:white;");
    }

    /**
     * Detecte la sortie du survol du bouton du menu ( change la couleur au survol )
     * @param event
     */
    @FXML
    void survolSortieBouton(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color:#e16731;-fx-text-fill:white;");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        optionsTouches(null);

    }

}
