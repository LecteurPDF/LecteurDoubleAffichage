package info2.lecteurpdf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControleurChangementDisposition {

    @FXML
    void ecranUnPositionUn(ActionEvent event) {
    	try {
			new Vue(new Emplacement(1, 1));

		} catch (EmplacementIncorrect e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void ecranUnPositionDeux(ActionEvent event) {


    }

    @FXML
    void ecranDeuxPositionUn(ActionEvent event) {
    	try {
			new Vue(new Emplacement(2, 1));

		} catch (EmplacementIncorrect e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void ecranDeuxPositionDeux(ActionEvent event) {

    }

}
