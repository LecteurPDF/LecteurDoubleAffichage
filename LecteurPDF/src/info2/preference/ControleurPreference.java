package info2.preference;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class ControleurPreference {

    @FXML
    private ScrollPane affichageInfos;

    @FXML
    void optionsTouches(ActionEvent event) {
    	try {
    		affichageInfos.setContent(FXMLLoader.load(getClass().getResource("touches.fxml")));

		} catch (IOException e) {
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

    @FXML
    void survolEntreeBouton(MouseEvent event) {
    	((Button)event.getSource()).setStyle("-fx-background-color:#f17d49;-fx-text-fill:white;");
    }

    @FXML
    void survolSortieBouton(MouseEvent event) {
    	((Button)event.getSource()).setStyle("-fx-background-color:#e16731;-fx-text-fill:white;");
    }
}
