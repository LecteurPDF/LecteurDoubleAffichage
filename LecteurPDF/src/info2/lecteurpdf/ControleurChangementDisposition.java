package info2.lecteurpdf;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class ControleurChangementDisposition implements Initializable{

	@FXML
	private AnchorPane posA;

	@FXML
	private AnchorPane posB;

	@FXML
	private AnchorPane posC;

	@FXML
	private AnchorPane posD;

	@FXML
	private AnchorPane supprimer;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LinkedList<Vue> liste = Vue.getListeVues();
		AnchorPane position;

		for(Vue vue: liste) {
			Label nomfich = new Label(vue.getPdf().getCheminFichier());
			nomfich.setWrapText(true);

			position = determinePosition(vue);

			// On positionne et ajoute la vue dans l'AnchorPane
			AnchorPane.setTopAnchor(nomfich, 0.0);
			AnchorPane.setLeftAnchor(nomfich, 0.0);
			AnchorPane.setRightAnchor(nomfich, 0.0);
			AnchorPane.setBottomAnchor(nomfich, 0.0);
			position.getChildren().add(nomfich);

			nomfich.setOnDragDetected(mouseEvent -> {
	            System.out.println("DnD detecté.");
	            final Dragboard dragBroard = nomfich.startDragAndDrop(TransferMode.MOVE);
	            // Remlissage du contenu.
	            ClipboardContent content = new ClipboardContent();
	            content.putString(nomfich.getText());
	            dragBroard.setContent(content);
	            //
	            dragBroard.setContent(content);
	            mouseEvent.consume();
	        });

		}

		AnchorPane[] tabAnchor = {
				posA,
				posB,
				posC,
				posD
		};

		for(AnchorPane anchor:tabAnchor) {
		anchor.setOnDragOver(dragEvent -> {
		    final Dragboard dragBroard = dragEvent.getDragboard();

		    if (dragEvent.getGestureSource() != anchor && dragBroard.hasString()) {
		        // Indique les modes de transfert autorisés sur cette destination.
		        dragEvent.acceptTransferModes(TransferMode.MOVE);
		   }
		    dragEvent.consume();

		});


		anchor.setOnDragDropped(dragEvent -> {
			final Dragboard dragBroard = dragEvent.getDragboard();
	        boolean success = false;
	        if (dragBroard.hasString()) {

	        	Label nomfich = new Label(dragBroard.getString());
	        	nomfich.setWrapText(true);

				// On positionne et ajoute la vue dans l'AnchorPane
				AnchorPane.setTopAnchor(nomfich, 0.0);
				AnchorPane.setLeftAnchor(nomfich, 0.0);
				AnchorPane.setRightAnchor(nomfich, 0.0);
				AnchorPane.setBottomAnchor(nomfich, 0.0);
				anchor.getChildren().add(nomfich);

	           success = true;
	        }
	        /* let the source know whether the string was successfully
	         * transferred and used */
	        dragEvent.setDropCompleted(success);

	        dragEvent.consume();
		});
		}

	}

	private AnchorPane determinePosition(Vue vue) {
		Emplacement emplacement = vue.getEmplacement();

		AnchorPane[][] tabPositions = {
				{posA,posB},
				{posC,posD},
		};

		return tabPositions[emplacement.getFenetre()-1][emplacement.getPosition()-1];

	}


}
