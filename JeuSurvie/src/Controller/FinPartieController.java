package Controller;

import Gestionnaire.GameManager;
import Metier.Score;
import Thread.Chrono;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FinPartieController {
    @FXML
    private Button closeButton;
    @FXML
    private Button pseudoButton;
    @FXML
    private Label tempsLabel;
    @FXML
    private Label wavesLabel;
    @FXML
    private Label nbMonstersKilledLabel;
    @FXML
    private TextField pseudoTextField;

    private Chrono chrono;
    private SimpleIntegerProperty nbWaves;
    private int cptMob;
    private SimpleStringProperty pseudo = new SimpleStringProperty();

    public FinPartieController(Chrono chrono, SimpleIntegerProperty nbWaves, int cptMob) {
        this.chrono = chrono;
        this.nbWaves = nbWaves;
        this.cptMob = cptMob;
    }

    @FXML
    public void initialize() {
        tempsLabel.setText(chrono.getSb().toString());
        wavesLabel.setText(Integer.toString(nbWaves.get()));
        nbMonstersKilledLabel.setText(Integer.toString(cptMob));
        pseudo.bind(pseudoTextField.textProperty());

        closeEvent();
        enregistrementEvent();
    }

    /**
     * Set l'action du bouton close
     */
    private void closeEvent() {
        closeButton.setOnAction(event -> {
            try {
                VBox root = FXMLLoader.load(getClass().getResource(GameManager.VIEW_MENU_FXML));
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.setTitle("Jeu Survie - Menu");
                stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Set l'action du bouton d'enregistrement du pseudo
     */
    private void enregistrementEvent(){

        pseudoButton.setOnAction(event -> {
            Score score = new Score(chrono.getSb().toString(), nbWaves.get(), cptMob, pseudo.get());
            GameManager.listeScores.add(score);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(GameManager.VIEW_SCORES_FXML));
            ScoresController scoresController = new ScoresController();
            loader.setController(scoresController);
            try {
                VBox root = loader.load();
                Stage stage = (Stage) pseudoButton.getScene().getWindow();
                stage.setTitle("Jeu Survie - Scores");
                stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
