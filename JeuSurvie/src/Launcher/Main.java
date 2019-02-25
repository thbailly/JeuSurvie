package Launcher;


import Gestionnaire.GameManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Première classe qui s'exécute au lancement de l'application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GameManager.VIEW_MENU_FXML));
        Pane pane = loader.load();
        Scene scene = new Scene(pane, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

        GameManager gm = new GameManager();
        gm.chargementScores();

    }

    @Override
    public void stop(){
        GameManager gm = new GameManager();
        gm.sauvegardeScores();
    }
}
