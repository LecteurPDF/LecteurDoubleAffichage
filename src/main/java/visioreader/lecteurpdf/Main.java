package visioreader.lecteurpdf;


import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import visioreader.util.Preferences;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


/**
 * Permet de lancer de logiciel,
 *   -  Initialise les préférences de l'utilisateur,
 *   -  Initialisation des logger 'journaux'
 *   -  Ouverture de la fenêtre 'ControleurPrincipal.fxml' ainsi que de son controleur 'controller'
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class Main extends Application {

    /** Le différents logs de l'apllication */
    public static Logger journaux = Logger.getLogger("Journaux de logs");

    /** Permet d'accéder aux préférences de l'utilisateurs ( écrites dans le registre ) */
    static Preferences prefs = Preferences.getInstance();

    /** Le controleur de la fenêtre de l'application */
    public static ControleurPrincipal controller;

    @Override
    public void start(Stage primaryStage) {

        try {

        	prefs.initialiserRegistre();

            /* PdfRenderer -> Plus rapide -> java 8 ou +*/
            System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

            /* Import FXML */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/Principal.fxml"));
            VBox root = (VBox) loader.load();
            Scene scene = new Scene(root,900,600);
            controller = loader.getController();

            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

            primaryStage.setTitle("Visio Reader - Lecteur PDF Double Affichage");
            primaryStage.getIcons().add(new Image("/image/icone.png"));

            primaryStage.setScene(scene);
            primaryStage.show();

            /* Quand on ferme la fenêtre principale, on ferme l'application */
            primaryStage.setOnCloseRequest((WindowEvent eventClose) -> {
                System.exit(0);
            });



        } catch(Exception e) {
            journaux.severe("Problème au lancement de l'app graphique");
            e.printStackTrace();
        }
    }

	/**
     * Lancement de l'application graphique
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }
}
