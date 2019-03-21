package visioreader.vue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import visioreader.lecteurpdf.Main;
import visioreader.util.Emplacement;
import visioreader.util.EmplacementIncorrect;
import visioreader.util.Preferences;
import visioreader.util.OutilLecture.PageInexistante;

/**
 * Permet d'afficher une vue
 * La fenêtre possède les éléments suivants :
 *  - Changements de page ( suivant, précédent )
 *  - Page Actuelle ( et nombre de page total )
 *  - Barre de zoom
 *  - Menu contextuel ( clic droit ) : TODO chanegemt ?
 *      - Fermer vue              - Ferme la vue
 *      - Changer disposition     - Ouvre la fenpetre de changement de disposition 'ChangementDisposition.fxml'
 *      - Changer Fichier         - Change le fichier de la vue
 *      - Mettre en présentation  - Met la vue en présentation ( plein ecran )
 *
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

	/** Le menu complet */
	@FXML
	private ToolBar menu;

	/** Représente le menu (ToolBar menu) une fois qu'il est sorti de la vue */
	private BorderPane menuSepare;

	/** Représente le théatre du (ToolBar menu) une fois qu'il est sorti de la vue */
	Stage stageMenuSepare = new Stage();

	/** Affiche le nom du fichier */
	@FXML
	private Label nomFichier;

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

				sld_zoom.setMax(BORNE_MAX_ZOOM);
				sld_zoom.setMajorTickUnit(BORNE_MAX_ZOOM/4);
				sld_zoom.setValue(100);

				/* Ajout nom du fichier */
				nomFichier.setText(vue.getPdf().nomFichier());

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
	 * @return valeur de sld_zoom
	 */
	public Slider getSldZoom()  {
		return sld_zoom;

	}


	/**
	 * Mettre en place les preferences a mettre sur dans l'interface
	 * depuis l'ImageView imageAfficher
	 */
	private void setImagePrefs() {

		StackPane conteneurImage = new StackPane();

		conteneurImage.getChildren().add(imageAfficher);

		imageAfficher.setPreserveRatio(true);
		imageAfficher.setSmooth(true);
		imageAfficher.setCache(true);

		scrollPaneImg.setContent(null);

		scrollPaneImg.setContent(conteneurImage);
		System.out.println(conteneurImage.getWidth());
		imageAfficher.setFitWidth(conteneurImage.getWidth());

		conteneurImage.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
		scrollPaneImg.getViewportBounds().getWidth(), scrollPaneImg.viewportBoundsProperty()));

	}

	/**
	 * Permet d'afficher la précédente page
	 * @param event btnPrecPage
	 */
	@FXML
	public void precedentePage(ActionEvent event) {
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
	public void prochainePage(ActionEvent event) {
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
	public void nbPage(ActionEvent event) {
		try {
			imageAfficher.setImage(vue.getPdf().getPagePdfToImg(Integer.parseInt(txbNbPage.getText()) - 1).getImage());
			setImagePrefs();
			txbNbPage.setText(Integer.toString(vue.getPdf().getPagesCour()));
		} catch (NumberFormatException e) {
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
	public void setZoom(Double hauteur){
		if(hauteur != 0) {
			imageAfficher.setPreserveRatio(true);
			imageAfficher.setFitHeight(hauteur);
		}

	}

	/**
	 * Permet de fermer proprement le fichier et la fenêtre
	 */
	public void fermetureVue() {

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
	 * Permet en cliquant sur le boutton de fermer la vue
	 * @param event
	 * TODO Mettre à droite
	 */
	@FXML
	void fermerVue(ActionEvent event) {
		fermetureVue();
	}

	/**
	 * Sépare le menu de la vue
	 * @param event
	 */
	@FXML
	void separerMenu(ActionEvent event) {
		/* Si le menuSepare ne contient rien c'est que le menu est dans la vue */
		if (menuSepare == null) {

			menuSepare = new BorderPane();

			menuSepare.setCenter(menu); // Ajoute le menu

			Scene scene = new Scene(menuSepare,530,menu.getHeight());

			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

			/* Titre de la vue -> nomFichier - Vue n°1*/
			int numeroVue;
			if (vue.getEmplacement().getFenetre() == 1) {
				numeroVue = vue.getEmplacement().getPosition()-1 + vue.getEmplacement().getFenetre();
			} else {
				numeroVue = vue.getEmplacement().getPosition() + vue.getEmplacement().getFenetre();
			}
			stageMenuSepare.setTitle(vue.getPdf().nomFichier() + " - Vue n° " + numeroVue);


			stageMenuSepare.getIcons().add(new Image("/image/icone.png"));
			stageMenuSepare.setResizable(false);
			stageMenuSepare.setScene(scene);
			stageMenuSepare.show();

			/* A la fermeture on remet l'élément dans la VBox dans le bon ordre */
			stageMenuSepare.setOnCloseRequest((WindowEvent eventClose) -> {
				menuSepare = null;
				vboxVue.getChildren().remove(scrollPaneImg);
				vboxVue.getChildren().addAll(menu,scrollPaneImg);

			});

			vue.setMenuSorti(true); // le menu est sorti

			vboxVue.getScene().setOnMouseDragEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					Stage stage = (Stage) vboxVue.getScene().getWindow();
					stage.toFront();
				}
			});

		} else { // Sinon le menu est dans une fenetre
			/* On ferme la fenêtre, on vide le BorderPane et on remet l'élément dans la VBox dans le bon ordre*/
			stageMenuSepare.close();
			menuSepare = null;
			vboxVue.getChildren().remove(scrollPaneImg);
			vboxVue.getChildren().addAll(menu,scrollPaneImg);
			vue.setMenuSorti(false); // le menu n'est plus sorti
		}
	}




	/**
	 *
	 * @return valeur de imageAfficher
	 */
	public ImageView getImageAfficher() {
		return imageAfficher;
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
				} finally {
					Main.controller.reload();
				}
			}
		});

		// Evenment de fermeture de la fenetre courante
		fermVue.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				fermetureVue();
			}
		});

		// Evenement d'affichage de la popup de changement de disposition
		changeDisp.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
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
					stage.showAndWait();
					Main.controller.reload();
				} catch (IOException e) {
					Main.journaux.severe("Impossible d'ouvrir le fichier");
				}

			}
		});




		/* Ajout des options */
		contextMenu.getItems().addAll(changeDisp, changeEnPres,changeFich, fermVue);
		scrollPaneImg.setContextMenu(contextMenu);
	}

}
