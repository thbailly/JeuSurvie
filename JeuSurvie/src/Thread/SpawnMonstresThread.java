package Thread;

import Controller.JeuController;
import Enum.ETypeMonstre;
import Fabrique.MonstreFactory;
import Gestionnaire.GameManager;
import Gestionnaire.GestionnaireCollision;
import Metier.Joueur;
import Metier.Monstre;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.util.*;

/**
 * Permet de faire spawn les monstres tous les x temps
 */
public class SpawnMonstresThread extends Thread {

    /**
     * Unité de temps représentant 10 seoondes
     */
    private static final int DIX_SECONDES = 10;
    /**
     * Nombre de monstre par vagues
     */
    private static final int NB_MONSTRES_PAR_VAGUE = 10;
    /**
     * Hauteur d'un monstre
     */
    private static final int MONSTRE_HEIGHT = 70;
    /**
     * Largeur d'un monstre
     */
    private static final int MONSTRE_WIDTH = 70;
    /**
     * Multiplicateur pour passer de secondes à millisecondes
     */
    private static final int MULTIPLIER_SECONDS_TO_MILLISECONDS = 1000;
    /**
     * Mode de jeu sélectionné par le joueur
     */
    private String modeJeu;
    /**
     * Nombre de joueurs sélectionné par le joueur
     */
    private String nbJoueurs;
    @FXML
    private Pane pane;
    private Map<Monstre, ImageView> mapImagesMonstres;
    private Map<Joueur, ImageView> mapImagesJoueurs;
    private Joueur joueur;
    /**
     * Temps d'attente entre chaque vague
     */
    private int tempsSpawn;
    /**
     * Nombre de joueurs
     */
    private int nbPlayers = 0;
    /**
     * Liste de monstre à faire spawn sur le plateau
     */
    private List<Monstre> listMonsters = new ArrayList<>();
    /**
     * Manager de jeu
     */

    public SpawnMonstresThread(String modeJeu, String nbJoueurs, Pane pane,Map<Monstre, ImageView> mapImagesMonstres, Map<Joueur, ImageView> mapImagesJoueurs,Joueur joueur) {
        this.modeJeu = modeJeu;
        this.nbJoueurs = nbJoueurs;
        this.pane = pane;
        this.mapImagesMonstres = mapImagesMonstres;
        this.mapImagesJoueurs = mapImagesJoueurs;
        this.joueur = joueur;
    }

    /**
     * Redéfinition de la méthode run pour faire spawn les monstres avec un intervalle
     */
    @Override
    public void run() {
        nbPlayers = setupJoueurs();
        tempsSpawn = setupTime();

        try {
            while (!this.isInterrupted()) {
                Thread.sleep(tempsSpawn * MULTIPLIER_SECONDS_TO_MILLISECONDS);
                Platform.runLater(() -> {
                    try {
                        JeuController.nbWaves.set(JeuController.nbWaves.get() + 1);
                        spawnMonstres();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Configurer le nombre de joueurs
     *
     * @return le nombre de joueurs
     */
    private int setupJoueurs() {
        switch (nbJoueurs) {
            case "1 Joueur":
                nbPlayers = 1;
                break;

            case "2 Joueurs":
                nbPlayers = 2;
                break;

            default:
                nbPlayers = 1;
                break;
        }
        return nbPlayers;
    }

    /**
     * Configurer le temps de spawn
     *
     * @return le temps de spawn
     */
    private int setupTime() {
        switch (modeJeu) {
            case "Facile":
                tempsSpawn = (DIX_SECONDES * 3) / nbPlayers;
                break;

            case "Normal":
                tempsSpawn = (DIX_SECONDES * 2) / nbPlayers;
                break;

            case "Difficile":
                tempsSpawn = DIX_SECONDES / nbPlayers;
                break;

            default:
                tempsSpawn = (DIX_SECONDES * 3) / nbPlayers;
                break;
        }

        return tempsSpawn;
    }

    /**
     * Faire spawn les monstres
     *
     * @throws Exception
     */
    private void spawnMonstres() throws Exception {
        listMonsters.clear();

        for (int i = 0; i < NB_MONSTRES_PAR_VAGUE; i++) {
            listMonsters.add(MonstreFactory.getMontre(ETypeMonstre.normal));
        }

        Iterator<Monstre> it = listMonsters.iterator();

        GestionnaireCollision gc = new GestionnaireCollision(pane, mapImagesJoueurs, mapImagesMonstres);

        while (it.hasNext()) {
            Monstre m = it.next();
            ImageView ivBuff = addMonstrePane(m);
            while (gc.testCollisionSpawnMob(m, ivBuff))
                summonMonsterAll(m);

            MouvementMonstreThread mouvementThread = new MouvementMonstreThread(m, joueur, gc);
            mouvementThread.start();
        }
    }

    /**
     * Ajouter un monstre dans le pane
     *
     * @param m monstre à ajouter
     * @return ImageView utilisée pour vérifier la collision avec le monstre suivant
     * @throws Exception
     */
    private ImageView addMonstrePane(Monstre m) throws Exception {
        FileInputStream input = new FileInputStream(m.getSkin());
        ImageView iv = new ImageView(new Image(input));

        iv.yProperty().bind(m.getPosition().yProperty());
        iv.xProperty().bind(m.getPosition().xProperty());
        iv.setFitHeight(MONSTRE_HEIGHT);
        iv.setFitWidth(MONSTRE_WIDTH);
        m.setHauteur(iv.getFitHeight());
        m.setLargeur(iv.getFitWidth());
        mapImagesMonstres.put(m, iv);
        pane.getChildren().add(iv);
        return iv;
    }

    /**
     * Change la position d'un monstre pour le faire spawn à droite ou à gauche du plateau
     *
     * @param monstre Monstre à faire spawn
     */
    private void summonMonsterAll(Monstre monstre) {
        double height = pane.getBoundsInParent().getMaxY();
        int max = (int) height - (int) monstre.getHauteur();
        int randomY = randInt(0, max);
        monstre.getPosition().setY(randomY);
        if ((listMonsters.indexOf(monstre) % 2) == 0)
            monstre.getPosition().setX(pane.getBoundsInParent().getMinX());
        else
            monstre.getPosition().setX(pane.getBoundsInParent().getMaxX() - monstre.getHauteur());
    }

    /**
     * Génération aléatoire
     *
     * @param min valeur la plus basse
     * @param max valeur la plus haute
     * @return
     */
    private int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
