package visioreader.vue;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
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
import visioreader.lecteurpdf.Main;
import visioreader.util.Emplacement;
import visioreader.util.EmplacementIncorrect;

/**
 * Fenêtre pop-up permettant de modifier la position des vues ouvertes, d'ajouter une nouvelle vue, d'en fermer une
 * Pour Supprimer une vue, l'utilisateur sélectionne une vue ( Drag And Drop ) présente dans l'un des AnchorPane ( posA,posB,posC,posD )
 *   et le déplace dans l'AnchorPane TODO
 * Pour modifier la position d'une vue, l'utilisateur sélectionne une vue ( Drag And Drop ) présente dans l'un
 *   des AnchorPane ( posA,posB,posC,posD ) et le déplace dans un de ces AnchorPane ( posA,posB,posC,posD ), si il y a déjà une vue
 *   les des vues échanges leurs positions
 * Pour ajouter une vue : à l'ouverture d'une vue, la vue se place automatiquement dans la première place disponible TODO
 *
 * Le vocabulaire associé à cette classe :
 * Expéditeur : Celui qui va envoyer la vue vers un nouvel AnchorPane
 * Destinataire : Celui qui recevra la nouvelle vue
 *
 * @author sannac, vivier, pouzelgues, renoleau
 */
public class ControleurChangementDisposition implements Initializable {

    /** L'anchorPane sur lequel on déposera une vue ( Emplacement(1,1) ) */
    @FXML
    private AnchorPane posA;

    /** Le label sur lequel est donné le nom de la vue se trouvant à cet emplacement ( Emplacement(1,1) ) */
    @FXML
    private Label labelA;

    /** L'anchorPane sur lequel on déposera une vue ( Emplacement(1,2) ) */
    @FXML
    private AnchorPane posB;

    /** Le label sur lequel est donné le nom de la vue se trouvant à cet emplacement ( Emplacement(1,2) ) */
    @FXML
    private Label labelB;

    /** L'anchorPane sur lequel on déposera une vue ( Emplacement(2,1) ) */
    @FXML
    private AnchorPane posC;

    /** Le label sur lequel est donné le nom de la vue se trouvant à cet emplacement ( Emplacement(2,1) ) */
    @FXML
    private Label labelC;

    /** L'anchorPane sur lequel on déposera une vue ( Emplacement(2,2) ) */
    @FXML
    private AnchorPane posD;

    /** Le label sur lequel est donné le nom de la vue se trouvant à cet emplacement ( Emplacement(2,2) ) */
    @FXML
    private Label labelD;

    /** L'anchorPane sur lequel on dépose une vue pour la supprimer */
    @FXML
    private AnchorPane posSuppr;

    /** Permet de valider les changements quant aux emplacement des vues ( disposition ) */
    @FXML
    private Button valider;

    /** Permet de retablir sur la pop-up l'emplacement des vues */
    @FXML
    private Button retablir;

    /**
     * Liste toutes les vues existantes de l'application
     * au moment de l'ouverture de la fenêtre modale de changement de disposition
     * String -> Le nom de l'objet vue
     * Emplacement -> L'emplacement de la vue
     */
    private static HashMap<String,Emplacement> listeVuesTmp = new HashMap<>();

    /** L'emplacement de la fenêtre expéditeur */
    private Emplacement emplacementTmp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* On place toutes les vues dans l'AnchorPane correspondant à leur emplacement */
        for(Vue vue: Vue.getListeVues()) {
            try {
                AnchorPane position; // L'anchorPane qui correspond à l'emplacement de la vue

                /* On ajoute à la liste des vues la vue */
                listeVuesTmp.put(vue.toString(), new Emplacement(vue.getEmplacement().getFenetre(),vue.getEmplacement().getPosition()));

                /* On détermine l'anchorPane correspondant à la vue */
                position = determinePosition(vue.getEmplacement());

                /* On change le label de la vue ( contient le nom de la vue )
                 * Permet de savoir quelle vue se trouve sur quel anchorpane
                 */
                ((Label)position.getChildren().get(0)).setText(vue.toString());

                dragDetected(position);
            } catch (EmplacementIncorrect e) {
                Main.journaux.severe("L'emplacement spécifié est incorrect");
            }

        }

        /* Tous les anchorPane qui peuvent recevoir un vue */
        AnchorPane[] tabAnchor = {
                posA,
                posB,
                posC,
                posD,
                posSuppr


        };

        /* On donne à chaque AnchorPane le pouvoir de recevoir une nouvelle vue */
        for(AnchorPane anchor : tabAnchor) {

            dragOver(anchor);

            dragDropped(anchor);

        }

    }

    /**
     * Permet à partir d'un emplacement de connaître son AnchorPane lié
     * @param emplacement L'emplacement de la vue pour lequel on souhaite connaître l'anchorPane
     * @return L'AnchorPane lié à l'emplacement
     * 			Emplacement(1,1) -> posA
     * 			Emplacement(1,2) -> posB
     * 			Emplacement(2,1) -> posC
     * 			Emplacement(2,2) -> posD
     */
    private AnchorPane determinePosition(Emplacement emplacement) {


        AnchorPane[][] tabPositions = {
                {posA,posB},
                {posC,posD},
        };

        return tabPositions[emplacement.getFenetre()-1][emplacement.getPosition()-1];
    }

    /**
     * Permet à partir d'un emplacement de connaître son Label lié
     * @param emplacement L'emplacement de la vue pour lequel on souhaite connaître le Label
     * @return L'AnchorPane lié à l'emplacement
     * 			Emplacement(1,1) -> labelA
     * 			Emplacement(1,2) -> labelB
     * 			Emplacement(2,1) -> labelC
     * 			Emplacement(2,2) -> labelD
     */
    private Label determinePositionLabel(Emplacement emplacement) {


        Label[][] tabPositions = {
                {labelA,labelB},
                {labelC,labelD},
        };

        return tabPositions[emplacement.getFenetre()-1][emplacement.getPosition()-1];
    }

    /**
     * Lorsque qu'un drag est detecté ( déplacement de la source ),
     * 'emplacementTmp' est mis à jour par l'emplacement de la vue contenue dans 'anchor'
     * @param anchor L'anchorPane sur lequel on detectera le drag ('posA','posB','posC' ou 'posD')
     */
    private void dragDetected(AnchorPane anchor) {
        anchor.setOnDragDetected(mouseEvent -> {
            final Dragboard dragBroard = anchor.startDragAndDrop(TransferMode.MOVE);

            // Remlissage du contenu.
            ClipboardContent content = new ClipboardContent();
            content.putString(((Label)anchor.getChildren().get(0)).getText());
            dragBroard.setContent(content);

            /* On récupéère l'emplacement de la vue que l'on place dans emplacementTmp */
            try {
                emplacementTmp = new Emplacement(listeVuesTmp.get(((Label)anchor.getChildren().get(0)).getText()).getFenetre(),listeVuesTmp.get(((Label)anchor.getChildren().get(0)).getText()).getPosition());
            } catch (EmplacementIncorrect e) {
                Main.journaux.severe("L'emplacement spécifié est incorrect");
            }

            mouseEvent.consume(); // Fin du dragDetected
        });
    }

    /**
     * Lorsque qu'un drag est detecté ( survol de la destination ),
     * Accepte le transfert
     * @param anchor L'anchorPane sur lequel on detectera le drag ('posA','posB','posC' ou 'posD')
     */
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


    /**
     * Lorsque qu'un drag est detecté ( reception de l'expditeur sur la sources ),
     * On récupère le contenu des labels concernés ( expditeurs et destinataires )
     * A partir de ces labels,
     * @param anchor L'anchorPane sur lequel on detectera le drag ('posA','posB','posC' ou 'posD')
     */
    private void dragDropped(AnchorPane anchor) {
        anchor.setOnDragDropped(dragEvent -> {
            final Dragboard dragBroard = dragEvent.getDragboard();
            boolean success = false;
            if (dragBroard.hasString() && !anchor.equals(posSuppr)) {

                //Label nomFich = new Label(dragBroard.getString()); // Label qui contiendra l'expediteur
                String nomFich = "";
                nomFich = dragBroard.getString();
                determinePositionLabel(emplacementTmp).setText("Label");

                /* On prépare à la réception de l'expéditeur */

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
                    } catch (EmplacementIncorrect e) {
                        Main.journaux.severe("L'emplacement spécifié est incorrect");
                    }
                }

                if (nomFich2.length() > 0) { // Si le destin ataire est une vue existante, on le place dans l'expéditeur
                    listeVuesTmp.get(nomFich2).setEmplacement(emplacementTmp);
                }

                success = true;

            } else if (dragBroard.hasString() && anchor.equals(posSuppr)) {
                String nomFich = "";
                nomFich = dragBroard.getString();
                determinePositionLabel(emplacementTmp).setText("Label");
                listeVuesTmp.remove(nomFich);
            }

            /* Termine le DragAndDrop*/
            dragEvent.setDropCompleted(success);

            dragEvent.consume();
        });
    }

    /**
     * Applique les nouvelles dispositions à la liste des vues 'Vue.getListeVues()'
     * @param event
     */
    @FXML
    void actionValider(ActionEvent event) {
        /* Mise à jour de la liste des vues */
        System.out.println("\n\n");
        System.out.println(Vue.getListeVues());
        LinkedList<Vue> listeVues = Vue.getListeVues();
        for (int i = 0 ; i < Vue.getListeVues().size() ; i++) {
            if (listeVuesTmp.containsKey(Vue.getListeVues().get(i).toString())) {
                System.out.println("ici 1 " + Vue.getListeVues().get(i).getPdf().getCheminFichier() + " " + Vue.getListeVues().get(i));
                Vue.getListeVues().get(i).setEmplacement(listeVuesTmp.get(Vue.getListeVues().get(i).toString()));
            } else {
                System.out.println("ici 2" + Vue.getListeVues().get(i).getPdf().getCheminFichier() + " " + Vue.getListeVues().get(i));
                Vue.retirerVue(Vue.getListeVues().get(i));
                i--;
            }
        }
        System.out.println(Vue.getListeVues());
        ((Stage)posA.getScene().getWindow()).close();
    }

    /**
     * Rétablis la configurtion des vues à l'ouverture de la pop-up
     * @param event
     */
    @FXML
    void actionRetablir(ActionEvent event) {

    }
}