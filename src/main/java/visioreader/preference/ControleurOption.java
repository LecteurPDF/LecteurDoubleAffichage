package visioreader.preference;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import visioreader.util.Preferences;

public class ControleurOption implements Initializable{

	/** Permet d'accéder aux préférences de l'utilisateurs ( écrites dans le registre ) */
	private Preferences prefs = Preferences.getInstance();

	private final float QUALITE_FAIBLE = 100;

	private final float QUALITE_MOYENNE = 150;

	private final float QUALITE_HAUTE = 300;

	@FXML
	private Button btn_sauver;

	@FXML
	private RadioButton rbtn_qualFaible;

	@FXML
	private ToggleGroup qualite;

	@FXML
	private RadioButton rbtn_qualHaute;

	@FXML
	private RadioButton rbtn_qualMoy;

	@FXML
	private Label lbl_combiSuiv;

	@FXML
	private Label lbl_combiPrec;

    @FXML
    private CheckBox vueA;

    @FXML
    private CheckBox vueB;

    @FXML
    private CheckBox vueC;

    @FXML
    private CheckBox vueD;

	@FXML
	void changements(ActionEvent event) {

		String valQual = qualite.getSelectedToggle().getUserData().toString();

		prefs.setVueLiee(0, vueA.isSelected());
		prefs.setVueLiee(1, vueB.isSelected());
		prefs.setVueLiee(2, vueC.isSelected());
		prefs.setVueLiee(3, vueD.isSelected());

		if(valQual.equals("faible")) {
			valQual = Float.toString(QUALITE_FAIBLE);
		} else if(valQual.equals("moyen")) {
			valQual = Float.toString(QUALITE_MOYENNE);
		} else if(valQual.equals("haute")) {
			valQual = Float.toString(QUALITE_HAUTE);
		}
		prefs.put("QUALITE", valQual);

		((Stage) btn_sauver.getScene().getWindow()).close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		rbtn_qualFaible.setUserData("faible");
		rbtn_qualMoy.setUserData("moyen");
		rbtn_qualHaute.setUserData("haute");

		if(prefs.get("QUALITE", "").equals(Float.toString(QUALITE_FAIBLE))) {
			rbtn_qualFaible.setSelected(true);
		} else if(prefs.get("QUALITE", "").equals(Float.toString(QUALITE_HAUTE))){
			rbtn_qualHaute.setSelected(true);
		} else {
			rbtn_qualMoy.setSelected(true);
		}

		vueA.setSelected(prefs.getVueLiee().get(0));
		vueB.setSelected(prefs.getVueLiee().get(1));
		vueC.setSelected(prefs.getVueLiee().get(2));
		vueD.setSelected(prefs.getVueLiee().get(3));


		lbl_combiSuiv.setText( prefs.get("TOUCHE_PAGE_SUIVANTE", ""));
		lbl_combiPrec.setText( prefs.get("TOUCHE_PAGE_PRECEDENTE", ""));
	}

}
