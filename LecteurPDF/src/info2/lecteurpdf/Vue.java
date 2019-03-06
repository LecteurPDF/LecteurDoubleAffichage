package info2.lecteurpdf;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import info2.util.OutilLecture;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * Une vue ets l'élément qui permet d'afficher une page d'un fichier pdf
 *   ainsi que les différents éléments qui  concerne cette même page
 *
 * Dans cette vue on retrouve différents éléments :
 *   - Le fichier PDF lui-même
 *   - La barre de navigation : page précédente / suivante + bouton plein écran + page actuelle
 * @author kevin.sannac
 *
 */
public class Vue {

	/** Liste toutes les vues existantes dans la fenêtre actuelle */
	private static LinkedList<Vue> listeVues = new LinkedList<Vue>();

	/** Emplacement courant définit par une fenêtre et une position */
	private Emplacement emplacement;

    /** Elements du fichier pdf ouvert en cours ( fichier et page affich�e en ce moment ) */
    private OutilLecture pdf = new OutilLecture();

    /** La représentation ( fxml ( graphique ) ) de la vue */
    private VBox vue;

    /** Le controleur lié à la vue ( graphique ) */
    private ControleurVue controleur;


    /**
     * Permet de créer à partir du contrôleur et du fichier fxml ( vue.fxml )
     * une nouvelle vue ( graphique )
     */
    public Vue (Emplacement emplacement) {

    	this.emplacement = emplacement;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vue.fxml"));
            vue = (VBox) loader.load();
            controleur = loader.getController();
            controleur.setVue(this);

            listeVues.add(this);
        } catch (IOException e) {
            Main.journaux.severe("Problème lors de la création de la vue");
            e.printStackTrace();
        }

    }

    public static LinkedList<Vue> getListeVues() {
		return listeVues;
	}

	/**
     * @return valeur de controleur
     */
    public ControleurVue getControleur() {
        return controleur;
    }
    /**
     * @return valeur de vue
     */
    public VBox getVue() {
        return vue;
    }

    /**
     * Donne une nouvelle valeur à vue
     * @param vue La nouvelle valeur de vue
     */
    public void setVue(VBox vue) {
        this.vue = vue;
    }

    /**
     * @return valeur de pdf
     */
    public OutilLecture getPdf() {
        return pdf;
    }

    /**
     * Donne une nouvelle valeur à pdf
     * @param fich La nouvelle valeur de pdf
     */
    public void setPdf(File fich) {
        pdf = new OutilLecture(fich.getAbsolutePath());
    }

    public Emplacement getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(Emplacement emplacement) {
		this.emplacement = emplacement;
	}

	/**
     * Ferme la vue
     */
    public void fermetureVue() {
        pdf.close();

		//Retrait dans la liste des vues
		listeVues.remove(this);

    }

}
