package Controller;

import Gestionnaire.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class MenuController {

    @FXML
    private Button closeButton;
    @FXML
    private Button playButton;
    @FXML
    private Button creditsButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button scoreButton;

    /**
     * Evenement pour fermer le jeu
     *
     * @param actionEvent
     */
    @FXML
    public void closeEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Etes vous sûr de vouloir quitter le jeu ?");
        alert.getDialogPane().getStylesheets().add(GameManager.STYLESHEET);

        ButtonType ok = new ButtonType("Oui");
        ButtonType non = new ButtonType("Non");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ok, non);


        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ok) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Evenement pour lancer la fenêtre Mode.fxml
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void playEvent(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(GameManager.VIEW_MODE_FXML)));
        ModeController mode = new ModeController();
        loader.setController(mode);
        Pane root = loader.load();
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.setTitle("Jeu Survie - Mode");
        stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
        stage.show();

    }

    /**
     * Evement pour lancer la fenêtre d'aide
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void helpEvent(ActionEvent actionEvent) throws Exception {
        VBox root = FXMLLoader.load(getClass().getResource(GameManager.VIEW_AIDE_FXML));
        Stage stage = (Stage) helpButton.getScene().getWindow();
        stage.setTitle("Jeu Survie - Aide");
        stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
        stage.show();
    }

    /**
     * Evenement pour lancer la fenêtre crédits
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void creditsEvent(ActionEvent actionEvent) throws Exception {
        VBox root = FXMLLoader.load(getClass().getResource(GameManager.VIEW_CREDITS_FXML));
        Stage stage = (Stage) creditsButton.getScene().getWindow();
        stage.setTitle("Jeu Survie - Crédits");
        stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
        stage.show();
    }

    /**
     * Evenement pour lancer la fenêtre option
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void optionsEvent(ActionEvent actionEvent) throws Exception {
        VBox root = FXMLLoader.load(getClass().getResource(GameManager.VIEW_OPTIONS_FXML));
        Stage stage = (Stage) optionsButton.getScene().getWindow();
        stage.setTitle("Jeu Survie - Options");
        stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
        stage.show();
    }

    /**
     * Evenement pour lancer la fenêtre de score
     *
     * @param actionEvent
     * @throws Exception
     */
    public void scoreEvent(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GameManager.VIEW_SCORES_FXML));
        ScoresController scoresController = new ScoresController();
        loader.setController(scoresController);
        try {
            VBox root = loader.load();
            Stage stage = (Stage) scoreButton.getScene().getWindow();
            stage.setTitle("Jeu Survie - Scores");
            stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
