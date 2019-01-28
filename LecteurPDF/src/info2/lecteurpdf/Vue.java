package info2.lecteurpdf;

import java.io.File;
import java.io.IOException;

import info2.util.OutilLecture;
import info2.util.Preferences;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Vue {


	private Preferences prefs = Preferences.getInstance();

	/** Elements du fichier pdf ouvert en cours ( fichier et page affich�e en ce moment ) */
	private OutilLecture pdf = new OutilLecture();

	private VBox vue;

	private ControleurVue controlleur;


	public Vue () {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("vue.fxml"));
			vue = (VBox) loader.load();
			controlleur = loader.getController();
			controlleur.setVue(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ControleurVue getControlleur() {
		return controlleur;
	}

	public VBox getVue() {
		return vue;
	}

	public void setVue(VBox vue) {
		this.vue = vue;
	}

	public OutilLecture getPdf() {
		return pdf;
	}

	public void setPdf(File fich) {
		pdf = new OutilLecture(fich.getAbsolutePath());
	}

}
