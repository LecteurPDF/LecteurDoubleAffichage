package info2.lecteurpdf;


import java.util.logging.Logger;

import info2.util.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


/**
 * Ouverture de la fen�tre principal
 *
 */
public class Main extends Application {

	public static Logger journaux = Logger.getLogger("Journaux de logs");

	/* Preference de l'application */
	static Preferences prefs = Preferences.getInstance();

    @Override
    public void start(Stage primaryStage) {


    	try {

            /* PdfRenderer -> Plus rapide -> java 8 ou +*/
            System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

            /* Import FXML */
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("principal.fxml"));
            Scene scene = new Scene(root,900,600);

//            primaryStage.initStyle(StageStyle.UNDECORATED);

            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setTitle("Visio Reader - Lecteur PDF Double Affichage");
            primaryStage.getIcons().add(new Image("icone.png"));

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            journaux.severe("Probl�me lancement de l'app graphique");
            //e.printStackTrace();
        }
    }

    /**
     * Lancement de l'application graphique
     */
    public static void main(String[] args) {
        launch(args);
    }
}
