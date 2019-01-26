package info2.lecteurpdf;

import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;

public class ControleurPreference {

	Preferences prefs = Main.prefs;

    @FXML
    void avancerCtrl(ActionEvent event) {
    	prefs.put("TOUCHE_PAGE_SUIVANTE", KeyCode.Z.toString());
    }

    @FXML
    void avancerFlecheHaut(ActionEvent event) {
    	prefs.put("TOUCHE_PAGE_SUIVANTE", KeyCode.D.toString());
    }

}
