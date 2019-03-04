package info2.lecteurpdf;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import info2.util.OutilLecture.PageInexistante;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class ControleurChangementDisposition implements Initializable{

	private LinkedList<Vue> listeTemp;

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

		listeTemp = Vue.getListeVues();

		reloadDaD();

		AnchorPane[] tabAnchor = {
				posA,
				posB,
				posC,
				posD
		};

		/* Zones de drop */
		for(AnchorPane anchor:tabAnchor) {

			/**
			 * Actions lors du passage de la zone de drop
			 */
			anchor.setOnDragOver(dragEvent -> {
				final Dragboard dragBroard = dragEvent.getDragboard();

				if (dragEvent.getGestureSource() != anchor && dragBroard.hasString()) {
					// Indique les modes de transfert autorisés sur cette destination.
					dragEvent.acceptTransferModes(TransferMode.MOVE);
				}
				dragEvent.consume();

			});

			/**
			 * Action lors du drop dans la zone
			 */
			anchor.setOnDragDropped(dragEvent -> {
				final Dragboard dragBroard = dragEvent.getDragboard();
				boolean success = false;
				if (dragBroard.hasString() ) {

					Label nomfich = new Label(dragBroard.getString());
					nomfich.setWrapText(true);

					System.out.println(dragBroard.getContent(DataFormat.lookupMimeType(Vue.class.getName())));

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

				reloadDaD();

			});
		}



	}

	/**
	 * Determine la position dans les cadres de l'interface
	 * @param vue vue à determiner sa position
	 * @return AnchorPane du cadre concerné par la vue
	 */
	private AnchorPane determinePosition(Vue vue) {
		Emplacement emplacement = vue.getEmplacement();

		AnchorPane[][] tabPositions = {
				{posA,posB},
				{posC,posD},
		};

		return tabPositions[emplacement.getFenetre()-1][emplacement.getPosition()-1];

	}

	private void reloadDaD() {
		AnchorPane position;

		/* Pour chaques vues les mettre dans le bon cadre */
		for(Vue vue: listeTemp) {

			Label nomfich = new Label(vue.getPdf().getCheminFichier());
			nomfich.setWrapText(true);

			position = determinePosition(vue);

			// On positionne et ajoute la vue dans l'AnchorPane
			AnchorPane.setTopAnchor(nomfich, 0.0);
			AnchorPane.setLeftAnchor(nomfich, 0.0);
			AnchorPane.setRightAnchor(nomfich, 0.0);
			AnchorPane.setBottomAnchor(nomfich, 0.0);
			position.getChildren().add(nomfich);

			/**
			 * Detection du drag
			 */
			nomfich.setOnDragDetected(mouseEvent -> {
				System.out.println("DnD detecté.");
				final Dragboard dragBroard = nomfich.startDragAndDrop(TransferMode.MOVE);
				// Remlissage du contenu.
				ClipboardContent content = new ClipboardContent();

				try {
					Image image = vue.getPdf().getPagePdfToImg(0).getImage();

					content.putString(vue.getPdf().getCheminFichier());
					content.putImage(image);
					content.put(DataFormat.lookupMimeType(Vue.class.getName()), vue);
				} catch (PageInexistante e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dragBroard.setContent(content);

				mouseEvent.consume();
			});
	}
}



}
