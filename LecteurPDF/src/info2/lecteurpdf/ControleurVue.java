package info2.lecteurpdf;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import info2.util.OutilLecture.PageInexistante;
import info2.util.Preferences;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Permet de controler les différents objets de SceneBuilder -> fichier "vue.fxml"
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurVue implements Initializable {

	/** Borne maximum du slider */
	private static final double BORNE_MAX_ZOOM = 400;

	/** Permet d'accéder aux préférences de l'utilisateurs ( écrites dans le registre ) */
	private Preferences prefs = Preferences.getInstance();

	/** L'instance de vue sur laquelle on travaille actuellement */
	private Vue vue;

	/** La fenêtre fxml -> VBox affichée à l'écran */
	@FXML
	private VBox vboxVue;

	/** Bouton pour aller à la page précédente */
	@FXML
	private Button btnPrecPage;

	/** Bouton pour aller à la page suivante */
	@FXML
	private Button btnNextPage;

	/** Texte qui représente le numéro de page actuel */
	@FXML
	private TextField txbNbPage;

	/** Texte qui représente le nombre de page en tout */
	@FXML
	private TextField txbNbPagesTotal;

	/** Bouton permettant de passer la vue en mode Plein Ecran ( Barre navigation & image du fichier pdf ) */
	@FXML
	private Button btnPleinEcran;

	/** Borne maximum en pixels qui correspond à 100% de l'image */
	private double maxZoom;

	/** Slider pour choisir le pourcentage de zoom */
	@FXML
	private Slider sld_zoom;

	/** Permet de pouvoir scroll la page, sera inséré l'ImageView de la page du PDF */
	@FXML
	private ScrollPane scrollPaneImg;

	/** La page du fichier pdf que l'on affiche actuellement */
	private ImageView imageAfficher;

	/** Menu clic droit :
	 *       - changer fichier
	 *       -
	 */
	private ContextMenu contextMenu;

	/** Indique si l'on est en plein écran */
	private boolean pleinecran = false;


	/**
	 * Change la valeur de vue
	 * @param vue La nouvelle valeur de vue
	 */
	public void setVue(Vue vue) {
		this.vue = vue;
	}


	/**
	 * Permet de charger un nouveau fichier dans la vue actuelle
	 * @param fich Le fichier que l'on souhaite mettre dans la vue
	 */
	public void chargementFichier(File fich) {
		try {
			/* Si le fichier existe, on l'affiche */
			if(fich != null) {

				vue.setPdf(fich);

				imageAfficher = new ImageView(vue.getPdf().getPagePdfToImg(0).getImage());

				/* On met l'ImageView � la bonne �chelle */
				setImagePrefs();

				txbNbPage.setText(Integer.toString(vue.getPdf().getPagesCour()));

				txbNbPagesTotal.setText(Integer.toString(vue.getPdf().getNbPages()));

				maxZoom = imageAfficher.getImage().getHeight();
				System.out.println("Taille de l'image = " + maxZoom);

				sld_zoom.setMax(BORNE_MAX_ZOOM);
				sld_zoom.setMajorTickUnit(BORNE_MAX_ZOOM/4);
				sld_zoom.setValue(100);

				// Ecouteur lors du changement de valeur du slider
				sld_zoom.valueProperty().addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observable, //
							Number oldValue, Number newValue) {
						setZoom((newValue.doubleValue()/100) * maxZoom);
					}
				});

			}
		} catch (PageInexistante e) {
			Main.journaux.warning("Page inexistante");
		}
	}


	/**
	 * Mettre en place les preferences a mettre sur dans l'interface
	 * depuis l'ImageView imageAfficher
	 */
	private void setImagePrefs() {

		StackPane conteneurImage = new StackPane(imageAfficher);

		imageAfficher.setPreserveRatio(true);
		scrollPaneImg.setContent(null);

		scrollPaneImg.setContent(conteneurImage);

		conteneurImage.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
        scrollPaneImg.getViewportBounds().getWidth(), scrollPaneImg.viewportBoundsProperty()));

	}

	/**
	 * Permet d'afficher la précédente page
	 * @param event btnPrecPage
	 */
	@FXML
	void precedentePage(ActionEvent event) {
		try {
			imageAfficher.setImage(vue.getPdf().getPrecPage().getImage());
			/* On met l'ImageView � la bonne �chelle */
			setImagePrefs();
			txbNbPage.setText(Integer.toString(vue.getPdf().getPagesCour()));
		} catch (PageInexistante e) {
			Main.journaux.warning("Page inexistante");
		}

	}

	/**
	 * Permet d'afficher la prochaine page
	 * @param event btnNextPage
	 */
	@FXML
	void prochainePage(ActionEvent event) {
		try {
			imageAfficher.setImage(vue.getPdf().getNextPage().getImage());
			/* On met l'ImageView � la bonne �chelle */
			setImagePrefs();
			txbNbPage.setText(Integer.toString(vue.getPdf().getPagesCour()));
		} catch (PageInexistante e) {
			Main.journaux.warning("Page inexistante");
		}

	}

	/**
	 * Permet d'afficher la page souhaitée par l'utilisateur
	 * @param event txbNbPage
	 */
	@FXML
	void nbPage(ActionEvent event) {
		try {
			imageAfficher.setImage(vue.getPdf().getPagePdfToImg(Integer.parseInt(txbNbPage.getText()) - 1).getImage());
			/* On met l'ImageView � la bonne �chelle */
			setImagePrefs();
			txbNbPage.setText(Integer.toString(vue.getPdf().getPagesCour()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			Main.journaux.warning("Format du nombre errroné");
		} catch (PageInexistante e) {
			Main.journaux.warning("Page inexistante");
		}
	}

	/**
	 * Définit le zoom de l'image du pdf en court
	 * Le ratio hauteur et largeur est gardé
	 * @param hauteur souhaité
	 */
	void setZoom(Double hauteur){
		if(hauteur != 0) {
			imageAfficher.setPreserveRatio(true);
			imageAfficher.setFitHeight(hauteur);
		}

	}

	/**
	 * Permet de fermer proprement le fichier et la fenêtre
	 */
	void fermetureVue() {

		try {
			Parent parent = vboxVue.getParent();
			// Récupération du splitpane parent, celui qui contient les deux vue
			SplitPane splitPaneParent = (SplitPane) parent.getParent().getParent();
			//Retrait de l'anchorpane ou est la vue actuelle
			splitPaneParent.getItems().remove(parent);

			vue.fermetureVue();
		} catch( NullPointerException e) {

		}
	}

	/**
	 * Permet d'affichier le menu contextuel ( clic droit )
	 * @param event
	 */
	@FXML
	void afficherMenuContextuel(ContextMenuEvent event) {
		contextMenu.show(scrollPaneImg, event.getScreenX(), event.getScreenY());
	}

	/**
	 * Initialise les evenements
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Création du ContextMenu
		contextMenu = new ContextMenu();

		/* Les différentes options du menu */
		MenuItem changeDisp = new MenuItem("Changer disposition");
		MenuItem changeEnPres = new MenuItem("Mettre en présentation");
		MenuItem changeFich = new MenuItem("Changer fichier");
		MenuItem fermVue = new MenuItem("Fermer vue");

		/* Evénements des différentes options */
		// Evenement lié au changement du fichier
		changeFich.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// TODO charger un fichier
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
					Main.journaux.warning("Aucun fichier selectionné");
				}
			}
		});

		// Evenment de changement de disposition à mettre a la fin
		changeEnPres.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Emplacement emp;

				try {
					 emp = new Emplacement(2, 1);
					 vue.setEmplacement(emp);
				} catch (EmplacementIncorrect e) {
					Main.journaux.warning("Probléme, emplacement incoherent");
				} catch (EmplacementRedondant e) {
					try {
						emp = new Emplacement(2, 2);
						vue.setEmplacement(emp);
					} catch (EmplacementIncorrect e1) {
						Main.journaux.warning("Probléme, emplacement incoherent");
					} catch (EmplacementRedondant e1) {
						Main.journaux.warning("Aucun changement car fenetre 2 pleine");
						Alert alerte = new Alert(AlertType.WARNING, "Aucun changement car fenetre 2 pleine", ButtonType.OK);
						alerte.showAndWait();
					}
				} finally {
					Main.controller.reload();
				}
			}
		});

		// Evenment de fermeture de la fenetre courante
		fermVue.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// TODO : suppression
				fermetureVue();
			}
		});

		// Evenement d'affichage de la popup de changement de disposition
		changeDisp.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					/* Import FXML */
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangementDisposition.fxml"));
					BorderPane root = (BorderPane) loader.load();

					Scene scene = new Scene(root,559,274);

					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

					stage.setTitle("Visio Reader - Lecteur PDF Double Affichage");
					stage.getIcons().add(new Image("icone.png"));

					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});


		/* Ajout des options */
		contextMenu.getItems().addAll(changeDisp, changeEnPres,changeFich, fermVue);
		scrollPaneImg.setContextMenu(contextMenu);
	}

}
