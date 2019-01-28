package info2.lecteurpdf;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import info2.util.OutilLecture.PageInexistante;
import info2.util.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControleurVue implements Initializable {

	private Preferences prefs = Preferences.getInstance();

	private Vue vue;

	@FXML
	private VBox vboxVue;

	@FXML
	private Button btnPrecPage;

	@FXML
	private TextField txbNbPage;

	@FXML
	private Button btnNextPage;

	@FXML
	private Button btnPleinEcran;

	@FXML
	private ScrollPane scrollPaneImg;

	private ImageView imageAfficher;

	private boolean pleinecran = false;

	public void setVue(Vue vue) {
		this.vue = vue;
	}


	public void chargementFichier(File fich) {
		try {
			/* Si le fichier existe, on l'affiche */
			if(fich != null) {

				vue.setPdf(fich);

				imageAfficher = new ImageView(vue.getPdf().getPagePdfToImg(0).getImage());

				/* On met l'ImageView � la bonne �chelle */
				setImagePrefs();

				txbNbPage.setText(Integer.toString(vue.getPdf().getPagesCour()));

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
	 * Permet d'afficher la pr�c�dente page
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
	 * Permet d'afficher la page souhait�e par l'utilisateur
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
			Main.journaux.warning("Format du nombre errron�");
		} catch (PageInexistante e) {
			Main.journaux.warning("Page inexistante");
		}
	}

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
	 * Permet de fermer proprement le fichier et la fen�tre
	 * @param event
	 */
	void fermetureVue() {

		try {
			vue.getPdf().close();
		} catch( NullPointerException e) {

		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
