/*
 * ControleurPrincipal.java                            22/11/2018
 */

package info2.lecteurpdf;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import info2.util.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Permet de controler les différents objets de SceneBuilder -> fichier "principal.fxml"
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurPrincipal implements Initializable {

	/** Permet d'accéder aux préférences de l'utilisateurs ( écrites dans le registre ) */
	private Preferences prefs = Preferences.getInstance();

	/** Contient tous les éléments de la fenêtre */
	@FXML
	private VBox parentVBox;

	/** Contient 1 ou 2 AnchorPane qui eux même contiendront les diffférentes vues */
	@FXML
	private SplitPane splitPanePdf;

	@FXML
	private Menu menuDerniersFichiers;

	private SplitPane fenDeux;

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
			prefs.putDernierFichier(file.getAbsolutePath());

			chargementFichier(file);



		} catch (NullPointerException e) {
			Main.journaux.warning("Aucun fichier selectionn�");
		}

	}

	/**
	 * Permet pour une fenêtre d'ajouter une nouvelle vue avec un nouveau fichier
	 * @param fich Le fichier que l'on souhaite afficher dans la vue
	 */
	private void chargementFichier(File fich) {
		LinkedList<Vue> vues = Vue.getListeVues();
		int i = vues.size(); // Index pour l'ajout

		/* Supression de la fenetre si fermé */
		if( i <= 2) {
			fenDeux = null;
		}

		if( i >= 4 ) { /* Trop de fenétre demandé */
			//TODO: demander à l'utilisateur quesqu'il veut changer
			Main.journaux.info("Max de vue atteint : " + i);
			Alert alerte = new Alert(AlertType.WARNING, "Vous ne pouvez pas ouvrir plus de 4 vues.", ButtonType.OK);
			alerte.showAndWait();

		} else { /* Cas d'un ajout sur la fenetre principal */
			Emplacement emplacement;
			try {
			if (i >= 2 ){
//			new Alert(AlertType.WARNING, "TODO: Ouvrir deuxiéme fenetre", ButtonType.OK).showAndWait();
				emplacement = new Emplacement(2,i%2+1);
				// Creation de la fenétre si inexistante
				if(fenDeux == null) {
					fenDeux = new SplitPane();

					Stage stage = new Stage();
					Scene scene = new Scene(fenDeux, 900, 600);
					stage.setScene(scene);
					stage.show();
				}

			} else {

				emplacement = new Emplacement(1,i%2+1);

			}

			// Ajout de la vue à la fenêtre actuelle ( maximum 2 vues par fenétre)
			new Vue(emplacement);
			vues = Vue.getListeVues();
			vues.get(i).getControleur().chargementFichier(fich);

			// Ajout de l'AnchorPane au SplitPane ( Parent ) -> contiendra la vue
			AnchorPane newAnchor = new AnchorPane();

			if( i >= 2 ) {
				fenDeux.getItems().add(newAnchor);
			} else {
				//On ajoute l'AnchorPane contenant la vue au SplitPane
				splitPanePdf.getItems().add(newAnchor);
			}

			// On positionne et ajoute la vue dans l'AnchorPane
			AnchorPane.setTopAnchor(vues.get(i).getVue(), 0.0);
			AnchorPane.setLeftAnchor(vues.get(i).getVue(), 0.0);
			AnchorPane.setRightAnchor(vues.get(i).getVue(), 0.0);
			AnchorPane.setBottomAnchor(vues.get(i).getVue(), 0.0);
			newAnchor.getChildren().add(vues.get(i).getVue());

			for(Vue vue: vues) {
				System.out.print("|" + vue.getEmplacement().toString() + "|");
			}
			System.out.println();
			} catch(EmplacementRedondant e) {
				System.out.println(e);
			} catch(EmplacementIncorrect e){
				System.out.println("Franchement tu n'est pas malin !");
			}
		}
	}



	/**
	 * Permet de charger le dernier fichier lancé
	 * @param event
	 */
	@FXML
	void chargerDernierFichier() {

		LinkedList<MenuItem> items = new LinkedList<MenuItem>();
		LinkedList<String> fichiers = prefs.getDerniersFichiers();
		int i = 0;
		for(String cour: fichiers) {
			// Create MenuItems
			MenuItem newItem = new MenuItem(cour);

			//QUand l'utilisateur appuye
			newItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					try {
						//TODO Afficher liste des fichiers ouvert -> 5 fichiers ?
						chargementFichier(new File(newItem.getText()));
					} catch( NullPointerException e ) {
						Main.journaux.warning("Aucun fichier en mémoire");
						Alert alerte = new Alert(AlertType.WARNING, "Erreur chemin du fichier", ButtonType.OK);
						alerte.showAndWait();
						e.printStackTrace();
					}
				}
			});
			items.add(i, newItem);
			i++;
		}

		menuDerniersFichiers.getItems().clear();
		menuDerniersFichiers.getItems().addAll(items);

	}



	/**
	 * Permet l'ouverture de la fen�tre préférence
	 * @param event non utilisé
	 */
	@FXML
	void ouvrirPref(ActionEvent event) {
		try {
			/* Chargement du fxml du menu préférence */
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("../preference/preference.fxml"));

			/* On prépare le théatre ( stage ) et la scene */
			Stage stage = new Stage();
			Scene scene = new Scene(fxmlLoader.load(), 800, 500);
			stage.setTitle("Préférence - Lecteur PDF");
			stage.setScene(scene);

			//stage.setResizable(false);

			/* Fenetre modale */
			stage.initOwner( parentVBox.getScene().getWindow() );
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.showAndWait();
		} catch (IOException e) {
			Main.journaux.severe("Problème de lancement de la fenetre préférence");
		}
	}

    @FXML
    void fermetureFenetre(ActionEvent event) {
    	//TODO
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {



	}

}
