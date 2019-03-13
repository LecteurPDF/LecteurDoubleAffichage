package visioreader.vue;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import visioreader.lecteurpdf.Main;
import visioreader.util.Emplacement;
import visioreader.util.EmplacementIncorrect;
import visioreader.util.EmplacementRedondant;

/**
 * Expéditeur : Celui qui va envoie la vue vers un nouvel AnchorPane
 * Destinataire : Celui qui recevra la nouvelle vue
 * @author sannac, vivier, pouzelgues, renoleau
 */
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

	@FXML
	private Button valider;

	@FXML
	private Button retablir;

	/** Liste toutes les vues existantes de l'application
	 * au moment de l'ouverture de la fenêtre modale de changement de disposition
	 */
	private static HashMap<String,Emplacement> listeVuesTmp = new HashMap<>();

	/* La fenêtre que l'on bouge actuelelment */
	private Emplacement emplacementTmp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AnchorPane position;

		for(Vue vue: Vue.getListeVues()) {
			try {
				System.out.println(vue);
				listeVuesTmp.put(vue.toString(), new Emplacement(vue.getEmplacement().getFenetre(),vue.getEmplacement().getPosition()));

				Label nomFich = new Label(vue.toString());
				nomFich.setWrapText(true);

				position = determinePosition(vue.getEmplacement());

				// On positionne et ajoute la vue dans l'AnchorPane
				AnchorPane.setTopAnchor(nomFich, 0.0);
				AnchorPane.setLeftAnchor(nomFich, 0.0);
				AnchorPane.setRightAnchor(nomFich, 0.0);
				AnchorPane.setBottomAnchor(nomFich, 0.0);
				position.getChildren().add(nomFich);

				dragDetected(nomFich);
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

	private void dragDetected(Label nomFich) {
		nomFich.setOnDragDetected(mouseEvent -> {
			final Dragboard dragBroard = nomFich.startDragAndDrop(TransferMode.MOVE);
			// Remlissage du contenu.
			ClipboardContent content = new ClipboardContent();
			content.putString(nomFich.getText());
			//DataFormat data = new DataFormat("Vue");
			//content.put(data, vue)
			dragBroard.setContent(content);

			/* On récupéère l'emplacement de la vue que l'on déplace */
			try {
				emplacementTmp = new Emplacement(listeVuesTmp.get(nomFich.getText()).getFenetre(),listeVuesTmp.get(nomFich.getText()).getPosition());
			} catch (EmplacementIncorrect e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EmplacementRedondant e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(emplacementTmp);

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

				Label nomFich = new Label(dragBroard.getString()); // Label qui contiendra l'expediteur
				nomFich.setWrapText(true);

				/* On prépare à la réception de l'expéditeur */


				// TODO PB ICI

				AnchorPane expediteur = determinePosition(emplacementTmp); // On récupère l'emplacement de l'expediteur
				// afin de l'échanger ( swap ) avec le destinataire.
				System.out.println(" 2 " + emplacementTmp);
				/* On retire le label destintaire du destinataire si il existe */
				if (expediteur.getChildren().size() > 0) {
					expediteur.getChildren().remove(0);
				}

				/* On place le label destinataire dans l'expéditeur */
				Label nomFich2 = new Label();
				if (anchor.getChildren().size() > 0) {
					nomFich2 = (Label)anchor.getChildren().get(0);
					AnchorPane.setTopAnchor(nomFich2, 0.0);
					AnchorPane.setLeftAnchor(nomFich2, 0.0);
					AnchorPane.setRightAnchor(nomFich2, 0.0);
					AnchorPane.setBottomAnchor(nomFich2, 0.0);
					expediteur.getChildren().add(nomFich2);
				}

				//dragDetected(nomFich2); // Mise à jour de l'événement de déplacement

				/* On place le label de l'expéditeur dans le destinaire */
				AnchorPane.setTopAnchor(nomFich, 0.0);
				AnchorPane.setLeftAnchor(nomFich, 0.0);
				AnchorPane.setRightAnchor(nomFich, 0.0);
				AnchorPane.setBottomAnchor(nomFich, 0.0);
				anchor.getChildren().add(nomFich);

				/* On échange les emplacements */
				System.out.println(" 3 " + emplacementTmp);
				if (nomFich2.getText().length() > 0) {
					listeVuesTmp.get(nomFich.getText()).setEmplacement(listeVuesTmp.get(nomFich2.getText())); // On place l'expediteur dans le destinataire

				} else {
					try {
						Emplacement emplacementDestinataire;
						if (anchor.equals(posA)) {
							emplacementDestinataire = new Emplacement(1,1);
						} else if (anchor.equals(posB)) {
							emplacementDestinataire = new Emplacement(1,2);
						} else if (anchor.equals(posC)) {
							emplacementDestinataire = new Emplacement(2,1);
						} else {
							emplacementDestinataire = new Emplacement(2,2);
						}

						listeVuesTmp.get(nomFich.getText()).setEmplacement(emplacementDestinataire);
					} catch (Exception e) {
						System.out.println("Erreur avec emplacement");
						e.printStackTrace();
					}
				}
				System.out.println(" 4 " + emplacementTmp);
				if (nomFich2.getText().length() > 0) { // Si le destinataire est une vue existante, on le place dans l'expéditeur
					listeVuesTmp.get(nomFich2.getText()).setEmplacement(emplacementTmp);
					System.out.println(emplacementTmp);
				}

				/* On recharge les différents événements sur la page */
				dragDetected(nomFich); // Mise à jour de l'événement de déplacement
				dragDetected(nomFich2);
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
			System.out.println(vue.getPdf().getCheminFichier() + " " + listeVuesTmp.get(vue.toString()));
		}
		Main.controller.reload();
		((Stage)posA.getScene().getWindow()).close();
	}

	@FXML
	void actionRetablir(ActionEvent event) {

	}
}