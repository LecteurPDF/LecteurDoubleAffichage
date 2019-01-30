package info2.lecteurpdf;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import info2.util.OutilLecture.PageInexistante;
import info2.util.Preferences;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Permet de controler les différents objets de SceneBuilder -> fichier "vue.fxml"
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurVue implements Initializable {

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

		imageAfficher.setPreserveRatio(true);

		scrollPaneImg.setContent(null);
		scrollPaneImg.setContent(imageAfficher);
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
	 * Permet de passer la vue en mode plein écran, en mode plein écran, le menu n'est plus affiché,
	 * seule la vue ( Donc la page du fichier et sa barre denavigation le sont)
	 * @param event inutilisé
	 */
	@FXML
	void switchPleinEcran(ActionEvent event) {

		Stage primaryStage = (Stage) vboxVue.getScene().getWindow();

		Stage stage = new Stage();

		stage.setScene(new Scene(new VBox(vboxVue), 400, 400));

		pleinecran  = !pleinecran ;

		primaryStage.hide();
		stage.show();

		stage.setFullScreen(pleinecran);

	}


	/**
	 * Permet de fermer proprement le fichier et la fenêtre
	 */
	void fermetureVue() {

		try {
			vue.getPdf().close();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Create ContextMenu
		contextMenu = new ContextMenu();

		/* Les différentes options du menu */
		MenuItem item1 = new MenuItem("Changer fichier");
		MenuItem item2 = new MenuItem("Fermer vue");

		/* Evénements des différentes options */
		item1.setOnAction(new EventHandler<ActionEvent>() {

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

		item2.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// TODO : suppression
				//fermetureVue(); -> Ne marche pas
			}
		});

		/* Ajout des options */
		contextMenu.getItems().addAll(item1,item2);
	}

}
