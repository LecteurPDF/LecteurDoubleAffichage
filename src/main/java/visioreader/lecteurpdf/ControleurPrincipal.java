/*
 * ControleurPrincipal.java                                                                             22/11/2018
 */

package visioreader.lecteurpdf;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import visioreader.util.Emplacement;
import visioreader.util.EmplacementIncorrect;
import visioreader.util.Preferences;
import visioreader.vue.Vue;

/**
 * Représente la fenêtre de l'application, elle permet  à la fois :
 *   - D'accéder aux différents menus : préférence, ouverture d'un fichier, aide, mode plein écran, présentation
 *   - Afficher les différentes vues
 *   - Ouvrir des vues
 *   - Gérer ses vues
 *
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

	/** Représente le menu qui sauvegarde les derniers fichiers ouverts */
	@FXML
	private Menu menuDerniersFichiers;

	/** La deuxième fenêtre de l'application */
	private SplitPane fenDeux;

	/** Vue actuellement en création */
	private Vue vueEnCreation;

	/**
	 * @return valeur de splitPanePdf
	 */
	public SplitPane getSplitPanePdf() {
		return splitPanePdf;
	}

	/**
	 * @return valeur de fenDeux
	 */
	public SplitPane getFenDeux() {
		return fenDeux;
	}

	/**
	 * Definit si l'orientation courante est horizontal ou non
	 * @return	true si horizontal
	 * 			false si vertical
	 */
	public boolean isHorizontal() {
		return splitPanePdf.getOrientation() == Orientation.HORIZONTAL;
	}

	/**
	 * Definit en horizontal ou vertical sur les deux fenetres
	 * @param horizontal	true si horizontal voulu
	 * 						false si vertical voulu
	 */
	public void setHorizontal(boolean horizontal) {
		if(horizontal) {
			splitPanePdf.setOrientation(Orientation.HORIZONTAL);
			if(fenDeux != null) {
				fenDeux.setOrientation(Orientation.HORIZONTAL);
			}
		} else {
			splitPanePdf.setOrientation(Orientation.VERTICAL);
			if(fenDeux != null) {
				fenDeux.setOrientation(Orientation.VERTICAL);
			}
		}
	}

	/**
	 * Capture les touches appuyées
	 * @param event
	 */
	@FXML
	public void entreeClavier(KeyEvent event) {

		/* Temporaire */
		KeyCode entreeTouche;
		KeyCombination entreeCombi;

		/* Permet de determiner l'action à réalisé */
		String[] touche = {
				prefs.get("TOUCHE_PAGE_SUIVANTE", "").toUpperCase(),            // touchePageSuivante
				prefs.get("TOUCHE_PAGE_PRECEDENTE", "").toUpperCase(),          // touchePagePrecedente

				prefs.get("TOUCHE_PLEIN_ECRAN_1", "").toUpperCase(),            // touchePleinEcran1
				prefs.get("TOUCHE_PLEIN_ECRAN_2", "").toUpperCase(),            // touchePleinEcran2

				prefs.get("TOUCHE_PAGE_OUVRIR_FICHIER", "").toUpperCase(),      // toucheOuvrirFichier

				prefs.get("TOUCHE_PAGE_SUIVANTE_A", "").toUpperCase(),          // touchePgSuivanteVueA
				prefs.get("TOUCHE_PAGE_PRECEDENTE_A", "").toUpperCase(),        // touchePgPrecedenteVueA

				prefs.get("TOUCHE_PAGE_SUIVANTE_B", "").toUpperCase(),          // touchePgSuivanteVueB
				prefs.get("TOUCHE_PAGE_PRECEDENTE_B", "").toUpperCase(),        // touchePgPrecedenteVueB

				prefs.get("TOUCHE_PAGE_SUIVANTE_C", "").toUpperCase(),          // touchePgSuivanteVueC
				prefs.get("TOUCHE_PAGE_PRECEDENTE_C", "").toUpperCase(),        // touchePgPrecedenteVueC

				prefs.get("TOUCHE_PAGE_SUIVANTE_D", "").toUpperCase(),          // touchePgSuivanteVueD
				prefs.get("TOUCHE_PAGE_PRECEDENTE_D", "").toUpperCase(),        // touchePgPrecedenteVueD
		};

		/* Vrai si c'est l'action à faire */
		boolean[] action = {
				false,          // pgSuivante
				false,          // pgPrecedente

				false,          // tchPleinEcran1
				false,          // tchPleinEcran2

				false,          // ouvFichier

				false,          // pgSuivanteVueA
				false,          // pgPrecendenteVueA

				false,          // pgSuivanteVueB
				false,          // pgPrecendenteVueB

				false,          // pgSuivanteVueC
				false,          // pgPrecendenteVueC

				false,          // pgSuivanteVueD
				false,          // pgPrecendenteVueD

		};

		/* Definit si la touche ou la combinaison entree correspond
		 * a un element definit dans les preferences
		 */

		for (int i = 0 ; i < touche.length ; i++) {
			if(!touche[i].equals("")) {
				//touche[i] = touche[i].toUpperCase();
				if(touche[i].contains("+")) {
					entreeCombi = KeyCombination.valueOf(touche[i]);
					action[i] = entreeCombi.match(event);
				} else{
					String uneTouche ;
					if(touche[i].contains("NUMPAD")) {
						uneTouche = touche[i].replace(" ", "");
					} else {
						uneTouche = touche[i].replace(' ', '_');
					}
					entreeTouche = KeyCode.valueOf(uneTouche);
					action[i] = event.getCode() == entreeTouche;
				}
			}
		}

		/* Definit les actions à réalisé lié a une combinaison de touches */

		/* touchePageSuivante */
		if(action[0]) {
			if (action[5] || action[7] || action[9] || action[11]) {
				// Ne rien faire pour éviter conflit avec les changements de vue
			} else {
				/* On regarde les vues liées */
				for (Vue vue : Vue.getListeVues()) {
					int i;
					if (vue.getEmplacement().getFenetre() == 1) {
						if (vue.getEmplacement().getPosition() == 1) {
							i = 0;
						} else  {
							i = 1;
						}
					} else  {
						if (vue.getEmplacement().getPosition() == 1) {
							i = 2;
						} else  {
							i = 3;
						}
					}

					if (Preferences.getInstance().getVueLiee().get(i)) {
						vue.getControleur().prochainePage(null);
					}
				}
			}

		}

		/* touchePagePrecedente */
		if(action[1]) {
			if (action[6] || action[8] || action[10] || action[12]) {
				// Ne rien faire pour éviter conflit avec les changements de vue
			} else {
				/* On regarde les vues liées */
				for (Vue vue : Vue.getListeVues()) {
					int i;
					if (vue.getEmplacement().getFenetre() == 1) {
						if (vue.getEmplacement().getPosition() == 1) {
							i = 0;
						} else  {
							i = 1;
						}
					} else  {
						if (vue.getEmplacement().getPosition() == 1) {
							i = 2;
						} else  {
							i = 3;
						}
					}

					if (Preferences.getInstance().getVueLiee().get(i)) {
						vue.getControleur().precedentePage(null);
					}
				}
			}
		}

		/* touchePleinEcran1 */
		if(action[2]) {
			if (action[3]) {

			} else {
				((Stage)parentVBox.getScene().getWindow()).setFullScreen(true);
				/* On parcourt toutes les vues */
				for (Vue vue : Vue.getListeVues()) {
					if(vue.getEmplacement().getFenetre() == 1)
						zoomVue(vue);
				}

			}
		}

		/* touchePleinEcran2 */
		if(action[3]) {
			if(fenDeux != null) {
				Stage stage = (Stage) fenDeux.getScene().getWindow();
				stage.toFront();
				stage.setFullScreen(true);
				stage.setAlwaysOnTop(true);

				for (Vue vue : Vue.getListeVues()) {
					if(vue.getEmplacement().getFenetre() == 2)
						zoomVue(vue);
				}
			}
		}
		/* toucheOuvrirFichier */
		if(action[4]) {
			changerFichier();
		}

		/* touchePageSuivanteA */
		if (action[5]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 0) {
					vue.getControleur().prochainePage(null);
				}
			}
		}

		/* touchePagePrecedenteA */
		if (action[6]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 0) {
					vue.getControleur().precedentePage(null);
				}
			}
		}

		/* touchePageSuivanteB */
		if (action[7]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 1) {
					vue.getControleur().prochainePage(null);
				}
			}
		}

		/* touchePagePrecedenteB */
		if (action[8]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 1) {
					vue.getControleur().precedentePage(null);
				}
			}
		}

		/* touchePageSuivanteC */
		if (action[9]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 2) {
					vue.getControleur().prochainePage(null);
				}
			}
		}

		/* touchePagePrecedenteC */
		if (action[10]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 2) {
					vue.getControleur().precedentePage(null);
				}
			}
		}

		/* touchePageSuivanteD */
		if (action[11]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 3) {
					vue.getControleur().prochainePage(null);
				}
			}
		}

		/* touchePagePrecedenteD */
		if (action[12]) {
			for (Vue vue : Vue.getListeVues()) {
				int i;
				if (vue.getEmplacement().getFenetre() == 1) {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 0;
					} else  {
						i = 1;
					}
				} else  {
					if (vue.getEmplacement().getPosition() == 1) {
						i = 2;
					} else  {
						i = 3;
					}
				}

				if ( i == 3) {
					vue.getControleur().precedentePage(null);
				}
			}
		}


	}

	/**
	 * Permet de définir le fichier que l'on va afficher
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

		if( i >= 4 ) { /* Trop de fenêtre demandé */
			Main.journaux.info("Max de vue atteint : " + i);
			Alert alerte = new Alert(AlertType.WARNING, "Vous ne pouvez pas ouvrir plus de 4 vues.", ButtonType.OK);
			alerte.showAndWait();

		} else { /* Cas d'un ajout sur la fenetre principal */
			Emplacement emplacement;
			try {

				if(!(emplacement = new Emplacement(1, 1)).existe(vues)) {
				} else if(!(emplacement = new Emplacement(1, 2)).existe(vues)) {
				} else if(!(emplacement = new Emplacement(2, 1)).existe(vues)) {
				} else {
					emplacement = new Emplacement(2, 2);
				}

				// Ajout de la vue à la fenêtre actuelle ( maximum 2 vues par fenétre)
				vueEnCreation = new Vue(emplacement);
				vues = Vue.getListeVues();

				vues.get(i).getControleur().chargementFichier(fich);

			} catch(EmplacementIncorrect e){
				Main.journaux.severe("Erreur emplacement impossible");
			}
		}
		lancementDisposition();
		vueEnCreation = null;
	}


	/**
	 * Recharge les dispositions des vues à partir de Vue.getListeVues();
	 */
	public void reload() {
		LinkedList<Vue> vues = Vue.getListeVues();
		TreeMap<Emplacement, VBox> emps = new TreeMap<>();
		boolean presenceFenDeux = false;

		splitPanePdf.getItems().clear();
		if(fenDeux != null)
			fenDeux.getItems().clear();


		for(Vue vue : vues) {
			emps.put(vue.getEmplacement(), vue.getVue());
		}

		Entry<Emplacement, VBox> entree = null; // Couple courant
		/* Traitement de chaques Vues */
		while((entree = emps.pollFirstEntry()) != null) {

			Emplacement emplacement = entree.getKey();
			// Ajout de l'AnchorPane au SplitPane ( Parent ) -> contiendra la vue
			AnchorPane newAnchor = new AnchorPane();

			if ( emplacement.getFenetre() >= 2 ){

				presenceFenDeux = true;
				// Creation de la fenétre si inexistante
				if(fenDeux == null) {
					/* Detecte l'ecran secondaire pour la presentation */
					int nbEcran = 0;
					for (@SuppressWarnings("unused") Screen screen : Screen.getScreens()) {
						nbEcran++;
					}
					Rectangle2D primaryScreenBounds = Screen.getScreens().get(nbEcran-1).getVisualBounds();

					/* Ouverture de la nouvelle fenêtre */
					fenDeux = new SplitPane();

					Stage stage = new Stage();
					Scene scene = new Scene(fenDeux, 900, 600);
					stage.setTitle("Visio Reader - Lecteur PDF Double Affichage");
					stage.getIcons().add(new Image("/image/icone.png"));
					stage.setScene(scene);

					//Set le stage sur l'ecran de presentation
					stage.setX(primaryScreenBounds.getMinX());
					stage.setY(primaryScreenBounds.getMinY());

					stage.show();

					stage.toFront();

					fenDeux.setOnKeyPressed(e -> {
						entreeClavier(e);
					});

					/* Fermeture de la fenetre */
					fenDeux.getScene().getWindow().setOnCloseRequest(e -> {
						fenDeux = null;
					});

				}
				fenDeux.getItems().add(newAnchor);
			} else {
				//On ajoute l'AnchorPane contenant la vue au SplitPane
				splitPanePdf.getItems().add(newAnchor);
			}

			// On positionne et ajoute la vue dans l'AnchorPane
			AnchorPane.setTopAnchor(entree.getValue(), 0.0);
			AnchorPane.setLeftAnchor(entree.getValue(), 0.0);
			AnchorPane.setRightAnchor(entree.getValue(), 0.0);
			AnchorPane.setBottomAnchor(entree.getValue(), 0.0);
			newAnchor.getChildren().add(entree.getValue());

			/* Supression de la fenetre si fermé */
			if(!presenceFenDeux && fenDeux != null) {
				((Stage) fenDeux.getScene().getWindow()).close();
				fenDeux = null;
			}


			setHorizontal(isHorizontal());

		}

		zoomAuto();

	}

	/**
	 * Applique la méthode 'zoomeVue' sur toutes les vues de V
	 *
	 */
	public void zoomAuto() {
		/* On parcourt toutes les vues */
		for (Vue vue : Vue.getListeVues()) {
			zoomVue(vue);
		}
	}

	/**
	 * Permet de faire un zoom automatique sur la vue en fonction de la taille du
	 * SplitPane dans lequel elle se trouve.
	 * @param vue La vue sur la laquette on souhaite faire le zoom
	 */
	public void zoomVue(Vue vue) {
		int tailleMenu; // La taille en pixel du menu
		int fenetre; // La fenetre sur laquelle la vue est présente
		int nbVues; // Le nombre de vues ouvertes dans la fenêtre

		tailleMenu = vue.isMenuSorti() ? 0 : 52;  // 0 Si le menu est sorti sinon 52
		fenetre = vue.getEmplacement().getFenetre();

		// On définit nbVues
		SplitPane[] tab = {splitPanePdf, fenDeux};

		// SI il y a un diviseur, alors il y a deux vues dans la fenêtre
		nbVues = tab[fenetre-1].getDividerPositions().length == 1 ? 2 : 1;
		vue.getControleur().getSldZoom().setValue((tab[fenetre-1].getHeight()-tailleMenu)/vue.getControleur().getImageAfficher().getImage().getHeight()*100);

		// On règle la hauteur voulue
		vue.getControleur().getImageAfficher().setFitHeight(tab[fenetre-1].getHeight()-tailleMenu);
		ScrollPane sp = (ScrollPane) vue.getVue().getChildren().get(vue.getVue().getChildren().size()-1);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);

		// On règle la largeur voulue
		if (nbVues == 1) {
			vue.getControleur().getImageAfficher().setPreserveRatio(false);
			vue.getControleur().getImageAfficher().setFitWidth(tab[fenetre-1].getWidth());
			vue.getControleur().getImageAfficher().setPreserveRatio(true);

		} else {
			vue.getControleur().getImageAfficher().setPreserveRatio(false);
			vue.getControleur().getImageAfficher().setFitWidth(tab[fenetre-1].getWidth()/2);
			vue.getControleur().getImageAfficher().setPreserveRatio(true);
		}
	}

	/**
	 * Permet d'initialiser la disposition
	 */
	@FXML
	void lancementDisposition() {
		try {
			/* Import FXML */
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/ChangementDisposition.fxml"));
			BorderPane root = (BorderPane) loader.load();

			Scene scene = new Scene(root,600,360);

			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

			stage.setTitle("Visio Reader - Lecteur PDF Double Affichage");
			stage.getIcons().add(new Image("/image/icone.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);

			stage.setOnCloseRequest(e -> {
				if(vueEnCreation != null) {
					vueEnCreation.fermetureVue();
				}
			});

			stage.showAndWait();
			Main.controller.reload();
		} catch (IOException e) {
			Main.journaux.severe("Impossible d'ouvrir le fichier");
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
						chargementFichier(new File(newItem.getText()));
					} catch( NullPointerException e ) {
						Main.journaux.warning("Aucun fichier en mémoire");
						Alert alerte = new Alert(AlertType.WARNING, "Erreur chemin du fichier", ButtonType.OK);
						alerte.showAndWait();
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
			fxmlLoader.setLocation(getClass().getResource("/layout/Preference.fxml"));

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

	/**
	 * Gère la fermeture de la fenêtre
	 * @param event
	 */
	@FXML
	void fermetureFenetre(ActionEvent event) {
	}

	/**
	 * ouvre le manuel
	 */
	@FXML
	void ouvrirManuel() {
		URL url = getClass().getResource("/manuel/manuel.pdf");

		chargementFichier(new File(url.getPath()));
	}

	/**
	 * Ouvre le menu à propose de
	 * @param event
	 */
	@FXML
	void ouvrirAPropos(ActionEvent event) {
		try {
			/* Chargement du fxml du menu préférence */
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/layout/APropos.fxml"));

			/* On prépare le théatre ( stage ) et la scene */
			Stage stage = new Stage();
			Scene scene = new Scene(fxmlLoader.load(), 800, 500);
			stage.setTitle("A Propos - Lecteur PDF");
			stage.setScene(scene);

			//stage.setResizable(false);

			/* Fenetre modale */
			stage.initOwner( parentVBox.getScene().getWindow() );
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.showAndWait();
		} catch (IOException e) {
			Main.journaux.severe("Problème de lancement de la fenetre a propos");
		}
	}

	/**
	 * Permet de passer en mode présentation ( deuxième fenêtre en plein écran )
	 * @param event
	 */
	@FXML
	void modePresentation(ActionEvent event) {

		if(fenDeux != null) {
			((Stage)fenDeux.getScene().getWindow()).setFullScreen(true);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setHorizontal(true);
	}

}
