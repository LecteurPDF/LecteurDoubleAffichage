/*
 * ControleurPrincipal.java                            22/11/2018
 */

package info2.lecteurpdf;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import info2.util.OutilLecture;
import info2.util.OutilLecture.PageInexistante;
import info2.util.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Permet de controler les diff�rents objets de SceneBuilder
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurPrincipal {

	private LinkedList<Vue> vues = new LinkedList<Vue>();

	private Preferences prefs = Preferences.getInstance();

	@FXML
	private AnchorPane vueGauche;

	@FXML
	private VBox parentVBox;

	@FXML
	private Button btnPleinEcran;

	private ImageView imageAfficher;


	public void initialize() {

	}

	/**
	 * Prise de la touche clavier utilis�
	 *
	 * @param event
	 */
	//	@FXML
	//	void entreeClavier(KeyEvent event) {
	//
	//		KeyCode entreeClavier = event.getCode();
	//
	//		if (entreeClavier == KeyCode.getKeyCode(prefs.get("TOUCHE_PAGE_SUIVANTE", KeyCode.CHANNEL_DOWN.toString() ))) {
	//
	//			try {
	//				imageAfficher.setImage(pdf.getNextPage().getImage());
	//				/* On met l'ImageView � la bonne �chelle */
	//				setImagePrefs();
	//				txbNbPage.setText(Integer.toString(pdf.getPagesCour()));
	//			} catch (PageInexistante e) {
	//				System.out.println( e.getMessage() );
	//			}
	//
	//		} else if(entreeClavier == KeyCode.getKeyCode(prefs.get("TOUCHE_PAGE_PRECEDENTE", KeyCode.CHANNEL_UP.toString() ))){
	//			//TODO
	//		} else {
	//			System.out.println("Pas de preferences");
	//
	//		}
	//
	//	}

	/**
	 * Permet de d�finir le fichier que l'on va afficher
	 * @param event
	 */
	public void changerFichier() {

		final FileChooser choixFichier = new FileChooser(); // Choisisseur de fichier

		/* Extension obligatoire : .PDF */
		FileChooser.ExtensionFilter filtreFichierPdf = new FileChooser.ExtensionFilter("Fichier PDF (*.pdf)", "*.pdf");
		choixFichier.getExtensionFilters().add(filtreFichierPdf);
		try {
			/* Ouverture de la fen�tre pour choix du fichier */
			File file = choixFichier.showOpenDialog(new Stage());
			prefs.put("DERNIER_FICHIER", file.getAbsolutePath());

			chargementFichier(file);



		} catch (NullPointerException e) {
			Main.journaux.warning("Aucun fichier selectionn�");
		}

	}

	private void chargementFichier(File fich) {
		vues.add(new Vue());
			vues.get(0).getControlleur().chargementFichier(fich);

			AnchorPane.setTopAnchor(vues.get(0).getVue(), 0.0);
			AnchorPane.setLeftAnchor(vues.get(0).getVue(), 0.0);
			AnchorPane.setRightAnchor(vues.get(0).getVue(), 0.0);
			AnchorPane.setBottomAnchor(vues.get(0).getVue(), 0.0);
			vueGauche.getChildren().add(vues.get(0).getVue());
	}

	@FXML
	void chargerDernierFichier(ActionEvent event) {

		try {
			//TODO Afficher liste des fichiers ouvert
			chargementFichier(new File(prefs.get("DERNIER_FICHIER", null)));
		} catch( NullPointerException e ) {
			Main.journaux.info("Aucun fichier en mémoire");
		}

	}



	/**
	 * Permet l'ouverture de la fen�tre pr�f�rence
	 * @param event non utilis�
	 */
	@FXML
	void ouvrirPref(ActionEvent event) {
		//TODO: Fenetre preference
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("../preference/preference.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 300, 500);
			Stage stage = new Stage();
			stage.setTitle("Préférence - Lecteur PDF");
			stage.setScene(scene);

			stage.setResizable(false);

			/* Fenetre modale */
			stage.initOwner( parentVBox.getScene().getWindow() );
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.showAndWait();
		} catch (IOException e) {
			Main.journaux.severe("Problème de lancement de la fenetre préférence");
			e.printStackTrace();
		}
	}

	/**
	 * Permet de fermer proprement le fichier et la fen�tre
	 * @param event
	 */
	@FXML
	void fermetureFenetre(ActionEvent event) {

		try {
			//>TODOvues.get(0).fermetureVue();
		} catch( NullPointerException e) {

		}
		Platform.exit();
	}

}
