package Thread;

import Controller.FinPartieController;
import Controller.JeuController;
import Gestionnaire.GameManager;
import Metier.Joueur;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Joueur thread permet de détecter la mort du joueur afin de terminer la partie
 */
public class JoueurThread extends Thread {
    /**
     * Définis un joueur
     */
    private Joueur joueur;
    /**
     * Définis un Thread
     */
    private Thread chrono;
    /**
     * Définis un Thread
     */
    private Thread gs;
    /**
     * Définis un pane
     */
    private Pane pane;
    /**
     * Définis un GameManager
     */
    GameManager gm = new GameManager();

    public JoueurThread(Joueur joueur, Thread chrono, Thread gs, Pane pane) {
        this.joueur = joueur;
        this.chrono = chrono;
        this.gs = gs;
        this.pane = pane;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            if (!joueur.isAlive()) {
                Platform.runLater(() -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(GameManager.VIEW_FIN_PARTIE_FXML));
                    FinPartieController finPartieController = new FinPartieController((Chrono) chrono, JeuController.nbWaves, joueur.getCptMob());
                    loader.setController(finPartieController);
                    VBox root;
                    try {
                        root = loader.load();
                        Stage stage = (Stage) pane.getScene().getWindow();
                        stage.setTitle("Jeu Survie - Fin de partie");
                        stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                chrono.interrupt();
                gs.interrupt();
                this.interrupt();
            }
        }
    }
}
