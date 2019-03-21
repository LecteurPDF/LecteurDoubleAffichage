package visioreader.vue;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import visioreader.lecteurpdf.Main;
import visioreader.util.Emplacement;
import visioreader.util.OutilLecture;

/**
 * Une vue ets l'élément qui permet d'afficher une page d'un fichier pdf
 *   ainsi que les différents éléments qui  concerne cette même page
 *
 * Dans cette vue on retrouve différents éléments :
 *   - Le fichier PDF lui-même
 *   - La barre de navigation : page précédente / suivante + bouton plein écran + page actuelle
 *   - L'emplacement de la vue
 *   - Sa représentation graphique (VBox)
 * @author kevin.sannac
 *
 */
public class Vue {

    /** Liste toutes les vues existantes de l'application */
    private static LinkedList<Vue> listeVues = new LinkedList<Vue>();

    /** Emplacement courant définit par une fenêtre et une position */
    private Emplacement emplacement;

    /** Elements du fichier pdf ouvert en cours ( fichier et page affich�e en ce moment ) */
    private OutilLecture pdf = new OutilLecture();

    /** La représentation ( fxml ( graphique ) ) de la vue */
    private VBox vue;

    /** Vrai si le menu est sorti de la fenêtre, sinon faux */
    private boolean menuSorti;

    /** Le controleur lié à la vue ( graphique ) */
    private ControleurVue controleur;


    /**
     * Permet de créer à partir du contrôleur et du fichier fxml ( vue.fxml )
     * une nouvelle vue ( graphique )
     * @param emplacement L'emplacement qu'aura la vue
     */
    public Vue (Emplacement emplacement) {

        this.emplacement = emplacement;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/Vue.fxml"));
            vue = (VBox) loader.load();
            controleur = loader.getController();
            controleur.setVue(this);

            menuSorti = false;

            listeVues.add(this);
        } catch (IOException e) {
            Main.journaux.severe("Problème lors de la création de la vue");
            e.printStackTrace();
        }

    }

    /**
     * valeur de listeVues
     * @return listeVues
     */
    public static LinkedList<Vue> getListeVues() {
        return listeVues;
    }

    /**
     * Change la valeur de listeVues
     * @param nouvelleListeVues Nouvelle valeur de listeVues
     */
    public static void setListeVues(LinkedList<Vue> nouvelleListeVues) {
        listeVues = nouvelleListeVues;
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
     * @param fich La nouvelle valeur de this.pdf
     */
	public void setPdf(File fich) {
		try {
			pdf = new OutilLecture(fich.getAbsolutePath());
		} catch (IOException e) {
			// Fichier non trouvé
			Main.journaux.warning("FIchier non trouv�");
			controleur.fermerVue(null);
			retirerVue(this);
		}
	}

    /**
     * valeur de emplacement
     * @return emplacement
     */
    public Emplacement getEmplacement() {
        return emplacement;
    }

    /**
     * @param emplacement Nouvelle valeur de this.emplacement
     */
    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }


    /**
     * Retire une vue de la liste des vues
     * @param vue La vue que l'on souhaite retirer
     * @return Vrai si on a pu retirer la vue, sinon faux
     */
    public static boolean retirerVue(Vue vue) {
        if (listeVues.contains(vue)) {
        listeVues.remove(vue);
        return true;
        }
        return false;
    }



    /**
     * @return Vrai si le menu est sorti, sinon faux
     */
    public boolean isMenuSorti() {
		return menuSorti;
	}

    /**
     * @param menuSorti nouvelle valeur de menuSorti, vrai si le menu est sorti, sinon faux
     */
	public void setMenuSorti(boolean menuSorti) {
		this.menuSorti = menuSorti;
	}

	/**
     * Ferme la vue
     */
    public void fermetureVue() {
        pdf.close();

        //Retrait dans la liste des vues
        listeVues.remove(this);
        controleur.stageMenuSepare.close(); // Fermeture du menu sorti si il est sorti
        Main.controller.reload();

    }

}
