package Gestionnaire;

import Metier.*;
import Persistance.ChargementScore;
import Persistance.SauvegardeScore;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {
    /**
     * Constante de la vue Menu.fxml
     */
    public static final String VIEW_MENU_FXML = "/View/Menu.fxml";
    /**
     * Constante de la vue Mode.fxml
     */
    public static final String VIEW_MODE_FXML = "/View/Mode.fxml";
    /**
     * Constante de la vue Aide.fxml
     */
    public static final String VIEW_AIDE_FXML = "/View/Aide.fxml";
    /**
     * Constante de la vue Credits.fxml
     */
    public static final String VIEW_CREDITS_FXML = "/View/Credits.fxml";
    /**
     * Constante de la vue Options.fxml
     */
    public static final String VIEW_OPTIONS_FXML = "/View/Options.fxml";
    /**
     * Constante de la vue Jeu.fxml
     */
    public static final String VIEW_JEU_FXML = "/View/Jeu.fxml";
    /**
     * Constante de la vue FinPartie.fxml
     */
    public static final String VIEW_FIN_PARTIE_FXML = "/View/FinPartie.fxml";
    /**
     * Constante de la vue Scores.fxml
     */
    public static final String VIEW_SCORES_FXML = "/View/Scores.fxml";
    /**
     * Constante pour le css
     */
    public static final String STYLESHEET = "/View/style.css";
    /**
     * Pas du joueur
     */
    public static final int PAS_JOUEUR = 10;
    /**
     * Pas du monstre
     */
    public static final int PAS_MONSTRE = 10;
    /**
     * Largeur de la l'application
     */
    public static final int DEFAULT_WIDTH = 1200;
    /**
     * Hauteur de l'application
     */
    public static final int DEFAULT_HEIGHT = 800;

    /**
     * Arme du joueur
     */
    private Arme a = new Arme(15, 20);
    /**
     * Création de notre joueur
     */
    public Joueur joueur = new Joueur("Player", 15, a, new Position(10, 10));
    /**
     * Map contenant la liste des monstres et des ImageView
     */
    public Map<Monstre, ImageView> mapImagesMonsters = new ConcurrentHashMap<>();
    /**
     * Map contenant la liste des monstres et des ImageView
     */
    public Map<Joueur, ImageView> mapImagesJoueur = new ConcurrentHashMap<>();
    /**
     * Liste de tous les scores
     */
    public static List<Score> listeScores = new ArrayList<>();

    /**
     * Sauvegarde des scores
     */
    public void sauvegardeScores(){
        SauvegardeScore sauvegardeScore = new SauvegardeScore();
        sauvegardeScore.sauvegarde(listeScores);
    }

    /**
     * Chargement des scores
     */
    public void chargementScores(){
        ChargementScore chargementScore = new ChargementScore();
        listeScores.clear();
        listeScores = chargementScore.chargement();
    }


}
