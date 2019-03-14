package visioreader.util;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import visioreader.vue.Vue;

/**
 * Expéditeur : Celui qui va envoie la vue vers un nouvel AnchorPane
 * Destinataire : Celui qui recevra la nouvelle vue
 * @author sannac, vivier, pouzelgues, renoleau
 */
public class ControleurChangementDisposition implements Initializable{

	@FXML
    private AnchorPane posA;

    @FXML
    private Label labelA;

    @FXML
    private AnchorPane posB;

    @FXML
    private Label labelB;

    @FXML
    private AnchorPane posC;

    @FXML
    private Label labelC;

    @FXML
    private AnchorPane posD;

    @FXML
    private Label labelD;

	@FXML
	private AnchorPane supprimer;

	@FXML
	private Button valider;

	@FXML
	private Button retablir;

	/** Liste toutes les vues existantes de l'application
	 * au moment de l'ouverture de la fenêtre modale de changement de disposition
	 */
	private static HashMap<String,Emplacement> listeVuesTmp = new HashMap<>();

	/** La fenêtre que l'on bouge actuelelment */
	private Emplacement emplacementTmp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		for(Vue vue: Vue.getListeVues()) {
			try {
				AnchorPane position;

				listeVuesTmp.put(vue.toString(), new Emplacement(vue.getEmplacement().getFenetre(),vue.getEmplacement().getPosition()));

				Label nomFich = new Label(vue.toString());
				nomFich.setWrapText(true);

				position = determinePosition(vue.getEmplacement());

				((Label)position.getChildren().get(0)).setText(vue.toString());
				
				dragDetected(position);
			} catch (EmplacementIncorrect e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EmplacementRedondant e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		AnchorPane[] tabAnchor = {
				posA,
				posB,
				posC,
				posD
		};

		for(AnchorPane anchor : tabAnchor) {

			dragOver(anchor);

			dragDropped(anchor);

		}

	}

	private AnchorPane determinePosition(Emplacement emplacement) {


		AnchorPane[][] tabPositions = {
				{posA,posB},
				{posC,posD},
		};

		return tabPositions[emplacement.getFenetre()-1][emplacement.getPosition()-1];
	}

	private Label determinePositionLabel(Emplacement emplacement) {


		Label[][] tabPositions = {
				{labelA,labelB},
				{labelC,labelD},
		};

		return tabPositions[emplacement.getFenetre()-1][emplacement.getPosition()-1];
	}

	private void dragDetected(AnchorPane anchor) {
		anchor.setOnDragDetected(mouseEvent -> {
			final Dragboard dragBroard = anchor.startDragAndDrop(TransferMode.MOVE);
			// Remlissage du contenu.

			ClipboardContent content = new ClipboardContent();
			content.putString(((Label)anchor.getChildren().get(0)).getText());
			dragBroard.setContent(content);

			/* On récupéère l'emplacement de la vue que l'on déplace */
			try {
				emplacementTmp = new Emplacement(listeVuesTmp.get(((Label)anchor.getChildren().get(0)).getText()).getFenetre(),listeVuesTmp.get(((Label)anchor.getChildren().get(0)).getText()).getPosition());
			} catch (EmplacementIncorrect e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EmplacementRedondant e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mouseEvent.consume();
		});
	}


	private void dragOver(AnchorPane anchor) {
		anchor.setOnDragOver(dragEvent -> {
			final Dragboard dragBroard = dragEvent.getDragboard();

			if (dragEvent.getGestureSource() != anchor && dragBroard.hasString()) {
				// Indique les modes de transfert autorisés sur cette destination.
				dragEvent.acceptTransferModes(TransferMode.MOVE);
			}
			dragEvent.consume();

		});
	}


	private void dragDropped(AnchorPane anchor) {
		anchor.setOnDragDropped(dragEvent -> {
			final Dragboard dragBroard = dragEvent.getDragboard();
			boolean success = false;
			if (dragBroard.hasString()) {

				//Label nomFich = new Label(dragBroard.getString()); // Label qui contiendra l'expediteur
				String nomFich = "";
				nomFich = dragBroard.getString();
				determinePositionLabel(emplacementTmp).setText(nomFich);

				/* On prépare à la réception de l'expéditeur */
				// TODO PB ICI

				AnchorPane expediteur = determinePosition(emplacementTmp); // On récupère l'emplacement de l'expediteur

				/* On place le label destinataire dans l'expéditeur */
				String nomFich2 = "";
				if (!((Label)anchor.getChildren().get(0)).getText().equals("Label")) {

					nomFich2 = ((Label)anchor.getChildren().get(0)).getText();
					determinePositionLabel(emplacementTmp).setText(nomFich2);
				}

				/* On échange les emplacements */
				Emplacement emplacementDestinataire;
				if (nomFich2.length() > 0) {
					emplacementDestinataire = listeVuesTmp.get(nomFich2);
					listeVuesTmp.get(nomFich).setEmplacement(emplacementDestinataire); // On place l'expediteur dans le destinataire

				} else {
					try {

						if (anchor.equals(posA)) {
							emplacementDestinataire = new Emplacement(1,1);
						} else if (anchor.equals(posB)) {
							emplacementDestinataire = new Emplacement(1,2);
						} else if (anchor.equals(posC)) {
							emplacementDestinataire = new Emplacement(2,1);
						} else {
							emplacementDestinataire = new Emplacement(2,2);
						}

						listeVuesTmp.get(nomFich).setEmplacement(emplacementDestinataire);
						((Label)determinePosition(emplacementDestinataire).getChildren().get(0)).setText(nomFich);
						
						if (nomFich2.length() > 0) {
							dragDetected(determinePosition(emplacementTmp));
						} else {
							determinePosition(emplacementTmp).setOnDragDetected(null);
						}
						dragDetected(determinePosition(emplacementDestinataire));
					} catch (Exception e) {
						System.out.println("Erreur avec emplacement");
						e.printStackTrace();
					}
				}

				if (nomFich2.length() > 0) { // Si le destin ataire est une vue existante, on le place dans l'expéditeur
					listeVuesTmp.get(nomFich2).setEmplacement(emplacementTmp);
				}

				success = true;

			}

			/* let the source know whether the string was successfully
			 * transferred and used  TODO Français */
			dragEvent.setDropCompleted(success);

			dragEvent.consume();
		});
	}

	@FXML
	void actionValider(ActionEvent event) {
		for (Vue vue : Vue.getListeVues()) {
			vue.setEmplacement(listeVuesTmp.get(vue.toString()));
		}
		visioreader.lecteurpdf.Main.controller.reload();
		((Stage)posA.getScene().getWindow()).close();
	}

	@FXML
	void actionRetablir(ActionEvent event) {

	}
}