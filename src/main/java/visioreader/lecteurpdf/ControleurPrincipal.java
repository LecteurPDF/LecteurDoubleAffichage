/*
 * ControleurPrincipal.java                                                                             22/11/2018
 */

package visioreader.lecteurpdf;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import visioreader.util.Emplacement;
import visioreader.util.EmplacementIncorrect;
import visioreader.util.Preferences;
import visioreader.vue.Vue;

/**
 * Représente la fenêtre de l'application, elle permet  à la fois :
 *   - D'accéder aux différents menus : préférence, ouverture d'un fichier, aide, mode plein écran, présentation
 *   - Afficher les différentes vues
 *   - Ouvrir des vues
 *   - Gérer ses vues
 *
 * @author sannac, vivier, pouzelgues, renoleau
 * @version 1.0
 */
public class ControleurPrincipal implements Initializable {

    /** Permet d'accéder aux préférences de l'utilisateurs ( écrites dans le registre ) */
    private Preferences prefs = Preferences.getInstance();

    /** Contient tous les éléments de la fenêtre */
    @FXML
    private VBox parentVBox;

    /** Contient 1 ou 2 AnchorPane qui eux même contiendront les diffférentes vues */
    @FXML
    private SplitPane splitPanePdf;

    /** Représente le menu qui sauvegarde les derniers fichiers ouverts */
    @FXML
    private Menu menuDerniersFichiers;

    /** La deuxième fenêtre de l'application */
    private SplitPane fenDeux;

    /**
     * Capture les touches appuyées
     * @param event
     */
    @FXML
    public void entreeClavier(KeyEvent event) {

        /* Temporaire */
        KeyCode entreeTouche;
        KeyCombination entreeCombi;

        /* Permet de determiner l'action à réalisé */
        String[] touche = {
                prefs.get("TOUCHE_PAGE_SUIVANTE", "").toUpperCase(),            // touchePageSuivante
                prefs.get("TOUCHE_PAGE_PRECEDENTE", "").toUpperCase(),          // touchePagePrecedente

                prefs.get("TOUCHE_PLEIN_ECRAN_1", "").toUpperCase(),            // touchePleinEcran1
                prefs.get("TOUCHE_PLEIN_ECRAN_2", "").toUpperCase(),            // touchePleinEcran2

                prefs.get("TOUCHE_PAGE_OUVRIR_FICHIER", "").toUpperCase(),      // toucheOuvrirFichier

                prefs.get("TOUCHE_PAGE_SUIVANTE_A", "").toUpperCase(),          // touchePgSuivanteVueA
                prefs.get("TOUCHE_PAGE_PRECEDENTE_A", "").toUpperCase(),        // touchePgPrecedenteVueA

                prefs.get("TOUCHE_PAGE_SUIVANTE_B", "").toUpperCase(),          // touchePgSuivanteVueB
                prefs.get("TOUCHE_PAGE_PRECEDENTE_B", "").toUpperCase(),        // touchePgPrecedenteVueB

                prefs.get("TOUCHE_PAGE_SUIVANTE_C", "").toUpperCase(),          // touchePgSuivanteVueC
                prefs.get("TOUCHE_PAGE_PRECEDENTE_C", "").toUpperCase(),        // touchePgPrecedenteVueC

                prefs.get("TOUCHE_PAGE_SUIVANTE_D", "").toUpperCase(),          // touchePgSuivanteVueD
                prefs.get("TOUCHE_PAGE_PRECEDENTE_D", "").toUpperCase(),        // touchePgPrecedenteVueD
        };

        /* Vrai si c'est l'action à faire */
        boolean[] action = {
                false,          // pgSuivante
                false,          // pgPrecedente

                false,          // tchPleinEcran1
                false,          // tchPleinEcran2

                false,          // ouvFichier

                false,          // pgSuivanteVueA
                false,          // pgPrecendenteVueA

                false,          // pgSuivanteVueB
                false,          // pgPrecendenteVueB

                false,          // pgSuivanteVueC
                false,          // pgPrecendenteVueC

                false,          // pgSuivanteVueD
                false,          // pgPrecendenteVueD

        };

        /* Definit si la touche ou la combinaison entree correspond
         * a un element definit dans les preferences
         */

        for (int i = 0 ; i < touche.length ; i++) {
            if(!touche[i].equals("")) {
                if(touche[i].contains("+")) {
                    entreeCombi = KeyCombination.valueOf(touche[i]);
                    action[i] = entreeCombi.match(event);
                } else {
                    entreeTouche = KeyCode.valueOf(touche[i]);
                    action[i] = event.getCode() == entreeTouche;
                }
            }
        }

        /* Definit les actions à réalisé lié a une combinaison de touches */

        /* touchePageSuivante */
        if(action[0]) {
            for (int i=0; i < Vue.getListeVues().size() ; i++) {
                if (Preferences.getInstance().getVueLiee().get(i)) {
                    Vue.getListeVues().get(i).getControleur().prochainePage(null);
                }
            }
        }

        /* touchePagePrecedente */
        if(action[1]) {
            for (int i=0; i < Vue.getListeVues().size() ; i++) {
                if (Preferences.getInstance().getVueLiee().get(i)) {
                    Vue.getListeVues().get(i).getControleur().precedentePage(null);
                }
            }
        }

        /* touchePleinEcran1 */
        if(action[2]) {
            ((Stage)parentVBox.getScene().getWindow()).setFullScreen(true);
        }

        /* touchePleinEcran2 */
        if(action[3]) {
            if(fenDeux != null) {
                ((Stage)fenDeux.getScene().getWindow()).setFullScreen(true);
            }
        }
        /* toucheOuvrirFichier */
        if(action[4]) {
            changerFichier();
        }


    }

    /**
     * Permet de définir le fichier que l'on va afficher
     * @param event
     */
    public void changerFichier() {

        final FileChooser choixFichier = new FileChooser(); // Choisisseur de fichier

        /* Extension obligatoire : .PDF */
        FileChooser.ExtensionFilter filtreFichierPdf = new FileChooser.ExtensionFilter("Fichier PDF (*.pdf)", "*.pdf");
        choixFichier.getExtensionFilters().add(filtreFichierPdf);
        try {
            /* Ouverture de la fen�tre pour choix du fichier */
            File file = choixFichier.showOpenDialog(new Stage());
            prefs.putDernierFichier(file.getAbsolutePath());

            chargementFichier(file);

        } catch (NullPointerException e) {
            Main.journaux.warning("Aucun fichier selectionn�");
        }

    }

    /**
     * Permet pour une fenêtre d'ajouter une nouvelle vue avec un nouveau fichier
     * @param fich Le fichier que l'on souhaite afficher dans la vue
     */
    private void chargementFichier(File fich) {

        //TODO : integré la popup

        LinkedList<Vue> vues = Vue.getListeVues();
        int i = vues.size(); // Index pour l'ajout

        /* Supression de la fenetre si fermé */
        if( i <= 2) {
            fenDeux = null;
        }

        if( i >= 4 ) { /* Trop de fenêtre demandé */
            //TODO: demander à l'utilisateur quesqu'il veut changer
            Main.journaux.info("Max de vue atteint : " + i);
            Alert alerte = new Alert(AlertType.WARNING, "Vous ne pouvez pas ouvrir plus de 4 vues.", ButtonType.OK);
            alerte.showAndWait();

        } else { /* Cas d'un ajout sur la fenetre principal */
            Emplacement emplacement;
            try {
                if (i >= 2){
                    emplacement = new Emplacement(2,i%2+1);
                    // Creation de la fenétre si inexistante
                    if(fenDeux == null) {

                        int nbScreen = 0;
                        for (@SuppressWarnings("unused") Screen screen : Screen.getScreens()) {
                            nbScreen++;
                        }

                        System.out.println(nbScreen);
                        Rectangle2D primaryScreenBounds = Screen.getScreens().get(nbScreen-1).getVisualBounds();

                        fenDeux = new SplitPane();

                        Stage stage = new Stage();
                        Scene scene = new Scene(fenDeux, 900, 600);
                        stage.setScene(scene);
                        stage.show();
                        //set Stage boundaries to visible bounds of the main screen
                        stage.setX(primaryScreenBounds.getMinX());
                        stage.setY(primaryScreenBounds.getMinY());

                        fenDeux.setOnKeyPressed(e -> {
                            entreeClavier(e);
                        });
                    }
                } else {
                    emplacement = new Emplacement(1,i%2+1);
                }

                // Ajout de la vue à la fenêtre actuelle ( maximum 2 vues par fenétre)
                new Vue(emplacement);
                vues = Vue.getListeVues();

                vues.get(i).getControleur().chargementFichier(fich);

                System.out.println();
            } catch(EmplacementIncorrect e){
                System.out.println("Franchement tu n'est pas malin !");
            }
            reload();
        }
    }


    /**
     * Recharge les dispositions des vues à partir de Vue.getListeVues();
     */
    public void reload() {
        LinkedList<Vue> vues = Vue.getListeVues();
        TreeMap<Emplacement, VBox> emps = new TreeMap<>();
        boolean presenceFenDeux = false;

        splitPanePdf.getItems().clear();
        if(fenDeux != null)
            fenDeux.getItems().clear();


        for(Vue vue : vues) {
            emps.put(vue.getEmplacement(), vue.getVue());
        }


        Entry<Emplacement, VBox> entree = null; // Couple courant

        while((entree = emps.pollFirstEntry()) != null) {

            Emplacement emplacement = entree.getKey();
            // Ajout de l'AnchorPane au SplitPane ( Parent ) -> contiendra la vue
            AnchorPane newAnchor = new AnchorPane();

            if ( emplacement.getFenetre() >= 2 ){

                presenceFenDeux = true;
                // Creation de la fenétre si inexistante
                if(fenDeux == null) {
                    /* Detecte l'ecran secondaire pour la presentation */
                    int i = 0;
                    for (@SuppressWarnings("unused") Screen screen : Screen.getScreens()) {
                        i++;
                    }
                    Rectangle2D primaryScreenBounds = Screen.getScreens().get(i-1).getVisualBounds();

                    /* Ouverture de la nouvelle fenêtre */
                    fenDeux = new SplitPane();

                    Stage stage = new Stage();
                    Scene scene = new Scene(fenDeux, 900, 600);
                    stage.setScene(scene);

                    stage.show();
                    //Set le stage sur l'ecran de presentation
                    stage.setX(primaryScreenBounds.getMinX());
                    stage.setY(primaryScreenBounds.getMinY());



                }
                fenDeux.getItems().add(newAnchor);
            } else {
                //On ajoute l'AnchorPane contenant la vue au SplitPane
                splitPanePdf.getItems().add(newAnchor);
            }

            // On positionne et ajoute la vue dans l'AnchorPane
            AnchorPane.setTopAnchor(entree.getValue(), 0.0);
            AnchorPane.setLeftAnchor(entree.getValue(), 0.0);
            AnchorPane.setRightAnchor(entree.getValue(), 0.0);
            AnchorPane.setBottomAnchor(entree.getValue(), 0.0);
            newAnchor.getChildren().add(entree.getValue());

        }
        if(!presenceFenDeux && fenDeux != null) {
            ((Stage) fenDeux.getScene().getWindow()).close();
            fenDeux = null;
        }
    }


    /**
     * Permet de charger le dernier fichier lancé
     * @param event
     */
    @FXML
    void chargerDernierFichier() {

        LinkedList<MenuItem> items = new LinkedList<MenuItem>();
        LinkedList<String> fichiers = prefs.getDerniersFichiers();
        int i = 0;
        for(String cour: fichiers) {
            // Create MenuItems
            MenuItem newItem = new MenuItem(cour);

            //QUand l'utilisateur appuye
            newItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //TODO Afficher liste des fichiers ouvert -> 5 fichiers ?
                        chargementFichier(new File(newItem.getText()));
                    } catch( NullPointerException e ) {
                        Main.journaux.warning("Aucun fichier en mémoire");
                        Alert alerte = new Alert(AlertType.WARNING, "Erreur chemin du fichier", ButtonType.OK);
                        alerte.showAndWait();
                        e.printStackTrace();
                    }
                }
            });
            items.add(i, newItem);
            i++;
        }

        menuDerniersFichiers.getItems().clear();
        menuDerniersFichiers.getItems().addAll(items);

    }



    /**
     * Permet l'ouverture de la fen�tre préférence
     * @param event non utilisé
     */
    @FXML
    void ouvrirPref(ActionEvent event) {

        try {
            /* Chargement du fxml du menu préférence */
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/layout/Preference.fxml"));

            /* On prépare le théatre ( stage ) et la scene */
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);
            stage.setTitle("Préférence - Lecteur PDF");
            stage.setScene(scene);

            //stage.setResizable(false);

            /* Fenetre modale */
            stage.initOwner( parentVBox.getScene().getWindow() );
            stage.initModality( Modality.APPLICATION_MODAL );
            stage.showAndWait();
        } catch (IOException e) {
            Main.journaux.severe("Problème de lancement de la fenetre préférence");
        }
    }

    /**
     * Gère la fermeture de la fenêtre
     * @param event
     */
    @FXML
    void fermetureFenetre(ActionEvent event) {
        //TODO
    }


    /**
     * Permet de passer en mode présentation ( deuxième fenêtre en plein écran )
     * @param event
     */
    @FXML
    void modePresentation(ActionEvent event) {
        if(fenDeux != null) {
            ((Stage)fenDeux.getScene().getWindow()).setFullScreen(true);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
