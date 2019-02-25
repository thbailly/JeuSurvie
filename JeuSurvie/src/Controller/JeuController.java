package Controller;

import Enum.EDirection;
import Enum.ETypeMonstre;
import Fabrique.MonstreFactory;
import Gestionnaire.*;
import Metier.Joueur;
import Metier.Monstre;
import Metier.Projectile;
import Thread.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class JeuController {
    private static final int PROJECTILE_HEIGHT = 20;
    private static final int PROJECTILE_WIDTH = 20;
    private static final int JOUEUR_HEIGHT = 50;
    private static final int JOUEUR_WIDTH = 50;
    private static final int MONSTRE_HEIGHT = 70;
    private static final int MONSTRE_WIDTH = 70;
    private GameManager gm = new GameManager();

    private String nbPlayers;
    private String modeJeu;

    private GestionnaireCollision gestionnaireCollision;
    private List<Monstre> listMonsters = new ArrayList<>();
    private Thread chrono;
    public static SimpleIntegerProperty nbWaves = new SimpleIntegerProperty(1);
    public Pane pane;

    @FXML
    private ImageView armes;
    @FXML
    private ImageView vie;
    @FXML
    private Label timerLabel;
    @FXML
    private Label nbWavesLabel;
    @FXML
    private Label nbMonstersKilledLabel;
    @FXML
    private Label hpLabel;
    @FXML
    private Button leaveButton;

    public JeuController(String modeJeu, String nbPlayers) {
        this.modeJeu = modeJeu;
        this.nbPlayers = nbPlayers;
    }

    @FXML
    public void initialize() {
        //Initialisation du chronomètre et lancement de celui-ci
        chrono = new Chrono(timerLabel);
        chrono.start();

        //Bind le nombre de vagues
        nbWavesLabel.textProperty().bind(Bindings.convert(nbWaves));

        //Bind nombre de monstres tués par le joueur
        nbMonstersKilledLabel.textProperty().bind(Bindings.convert(gm.joueur.getCptMobProperty()));

        //Bind la santé du joueur
        hpLabel.textProperty().bind(Bindings.convert(gm.joueur.getNbPdvProperty()));


        //Set action du bouton pour quitter
        setLeaveButtonAction();
    }

    /**
     * Récupère le pane
     *
     * @param pane Accès au contenu de la fenêtre
     * @return un gestionnaire clavier pour gérer les évenements clavier
     * @throws Exception
     */
    public GestionnaireClavier lancerJeu(Pane pane) throws Exception {
        this.pane = pane;
        return new GestionnaireClavier(this);
    }

    /**
     * Initialise le joueur et le gestionnaireSpawn dans le pane
     *
     * @throws Exception
     */
    public void initJeu() throws Exception {
        addJoueurPane(gm.joueur);

        for (int i = 0; i < 10; i++) {
            listMonsters.add(MonstreFactory.getMontre(ETypeMonstre.normal));
        }

        Iterator<Monstre> it = listMonsters.iterator();
        gestionnaireCollision = new GestionnaireCollision(pane, gm.mapImagesJoueur, gm.mapImagesMonsters);

        while (it.hasNext()) {
            Monstre m = it.next();
            ImageView ivBuff = addMonstrePane(m);
            while (gestionnaireCollision.testCollisionSpawnMob(m, ivBuff)) {
                summonMonsterAll(m);
            }
            MouvementMonstreThread mouvementThread = new MouvementMonstreThread(m, gm.joueur, gestionnaireCollision);
            mouvementThread.start();
        }

        Thread spawnMonstresThread = new SpawnMonstresThread(modeJeu, nbPlayers, pane,gm.mapImagesMonsters,gm.mapImagesJoueur,gm.joueur);
        spawnMonstresThread.start();

        Thread joueurThread = new JoueurThread(gm.joueur, chrono, spawnMonstresThread, pane);
        joueurThread.start();
    }

    /**
     * set l'action du bouton quitter
     */
    public void setLeaveButtonAction() {
        leaveButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quitter");
            alert.setHeaderText("Etes vous sûr de vouloir quitter la partie en cours ?");
            alert.getDialogPane().getStylesheets().add(GameManager.STYLESHEET);

            ButtonType ok = new ButtonType("Oui");
            ButtonType non = new ButtonType("Non");

            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ok, non);

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ok) {
                try {
                    VBox root = FXMLLoader.load(getClass().getResource(GameManager.VIEW_MENU_FXML));
                    Stage stage = (Stage) leaveButton.getScene().getWindow();
                    stage.setTitle("Jeu Survie Coop - Menu");
                    stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chrono.interrupt();
            }
        });
    }

    /**
     * Permet le déplacement du joueur
     *
     * @param eDirection direction dans laquelle le joueur doit se déplacer
     */
    public void movePlayer(EDirection eDirection) {
        GestionnaireMouvement.bouger(gestionnaireCollision, gm.joueur, eDirection, GameManager.PAS_JOUEUR);
        gm.joueur.setLastDirection(eDirection);
    }

    /**
     * Permet de tirer un projectile
     */
    public void tire() {
        if (!gm.joueur.isAlive()) return;
        Projectile projectile = GestionnaireProjectile.tirer(gm.joueur);
        ImageView imageView = new ImageView(new Image(projectile.getSkin()));
        imageView.xProperty().bind(projectile.getPosition().xProperty());
        imageView.yProperty().bind(projectile.getPosition().yProperty());
        imageView.setFitHeight(PROJECTILE_HEIGHT);
        imageView.setFitWidth(PROJECTILE_WIDTH);
        pane.getChildren().add(imageView);
        projectile.setHauteur(imageView.getFitHeight());
        projectile.setLargeur(imageView.getFitWidth());
        ProjectileMouvementThread projectileMouvementThread = new ProjectileMouvementThread(projectile, gestionnaireCollision, gm.joueur.getLastDirection(), imageView, projectile.getVitesseBalle(), gm.joueur);
        projectileMouvementThread.start();
    }

    /**
     * Ajouter un joueur dans le pane
     *
     * @param j Joueur a ajouter
     * @throws Exception
     */
    private void addJoueurPane(Joueur j) throws Exception {
        FileInputStream input = new FileInputStream(j.getSkin());
        ImageView iv = new ImageView(new Image(input));

        iv.yProperty().bind(j.getPosition().yProperty());
        iv.xProperty().bind(j.getPosition().xProperty());
        iv.setFitHeight(JOUEUR_HEIGHT);
        iv.setFitWidth(JOUEUR_WIDTH);
        j.getPosition().setY(pane.getBoundsInParent().getMaxY() / 2);
        j.getPosition().setX(pane.getBoundsInParent().getMaxX() / 2);
        j.setHauteur(iv.getFitHeight());
        j.setLargeur(iv.getFitWidth());
        gm.mapImagesJoueur.put(j, iv);
        pane.getChildren().add(iv);
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
        gm.mapImagesMonsters.put(m, iv);
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
