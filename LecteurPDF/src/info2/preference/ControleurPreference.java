package info2.preference;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;

public class ControleurPreference {

    @FXML
    private ScrollPane affichageInfos;

    @FXML
    void optionsTouches(ActionEvent event) {
    	try {
    		affichageInfos.setContent(FXMLLoader.load(getClass().getResource("touches.fxml")));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void optionDisposition(ActionEvent event) {
    	try {
    		affichageInfos.setContent(FXMLLoader.load(getClass().getResource("disposition.fxml")));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
