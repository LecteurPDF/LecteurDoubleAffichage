package visioreader.preference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class ControleurDisposition {

    @FXML
    private Label lab1DG;

    @FXML
    private Label lab2DG;

    @FXML
    private Button btn_inverserDG;

    @FXML
    private Button btn_inverserHB;

    @FXML
    private Label lab1HB;

    @FXML
    private Label lab2HB;

    @FXML
    private RadioButton radDG;

    @FXML
    private RadioButton radHB;


    @FXML
    private Button btn_valider;

    @FXML
    private Button btn_annuler;


    @FXML
    private RadioButton horizontal;

    @FXML
    private ToggleGroup choix;

    @FXML
    private RadioButton vertical;



    @FXML
    void inversionDG(ActionEvent event) {
    	String temp = lab1DG.getText();
    	lab1DG.setText(lab2DG.getText());
    	lab2DG.setText(temp);
    }

    @FXML
    void inversionHB(ActionEvent event) {
    	String temp = lab1HB.getText();
    	lab1HB.setText(lab2HB.getText());
    	lab2HB.setText(temp);
    }

    @FXML
    void inversion(MouseEvent event) {

    }

    @FXML
    void valider(ActionEvent event) {

    }


}
