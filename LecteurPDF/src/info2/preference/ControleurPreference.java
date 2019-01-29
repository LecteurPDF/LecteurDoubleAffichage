package info2.preference;

import java.io.IOException;

import info2.lecteurpdf.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;

/**
 * Permet de controler les différents objets de SceneBuilder -> fichier "preference.fxml"
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurPreference {

    /** Menu de Gauche -> Affiche les différentes catégories du menu préférence */
    @FXML
    private ScrollPane affichageInfos;

    /**
     * Affiche sur la partie de droite les informations liées ( fichier fxml  'touches.fxml')
     *   à la catégorie 'Touches'
     * @param event inutilisé
     */
    @FXML
    void optionsTouches(ActionEvent event) {
        try {
            affichageInfos.setContent(FXMLLoader.load(getClass().getResource("touches.fxml")));
        } catch (IOException e) {
            Main.journaux.warning("Problème lors de l'accés au fichier 'touches.fxml'");
        }
    }

    /**
     * Affiche sur la partie de droite les informations liées ( fichier fxml 'disposition.fxml' )
     *   à la catégorie 'Disposition'
     * @param event inutilisé
     */
    @FXML
    void optionDisposition(ActionEvent event) {
        try {
            affichageInfos.setContent(FXMLLoader.load(getClass().getResource("disposition.fxml")));
        } catch (IOException e) {
            Main.journaux.warning("Problème lors de l'accés au fichier 'disposition.fxml'");
        }

    }
}
