package info2.lecteurpdf;

import java.io.File;
import java.io.IOException;

import info2.util.OutilLecture;
import info2.util.Preferences;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class Vue {


	private Preferences prefs = Preferences.getInstance();

	/** Elements du fichier pdf ouvert en cours ( fichier et page affich�e en ce moment ) */
	private OutilLecture pdf = new OutilLecture();

	private VBox vue;


	public Vue (File fich) {
		pdf = new OutilLecture(fich.getAbsolutePath());

		try {
			System.out.println("avant");
			vue = (VBox) FXMLLoader.load(getClass().getResource("vue.fxml"));
			System.out.println("apres");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public VBox getVue() {
		return vue;
	}

	public OutilLecture getPdf() {
		return pdf;
	}

}
