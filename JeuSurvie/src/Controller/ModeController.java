package Controller;

import Gestionnaire.GameManager;
import Gestionnaire.GestionnaireClavier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ModeController {

    @FXML
    private Button closeButton;
    @FXML
    private Button playButton;
    @FXML
    private Label labelMode;
    @FXML
    private ChoiceBox<String> listMode;
    @FXML
    private Label labelJoueur;
    @FXML
    private ChoiceBox<String> listJoueurs;

    /**
     * Initialise les choices box et les labels
     */
    @FXML
    public void initialize() {
        //Sélection du mode de jeu
        ObservableList<String> modeJeu = FXCollections.observableArrayList("Facile", "Normal", "Difficile");
        listMode.setItems(modeJeu);
        listMode.setValue("Facile");
        labelMode.textProperty().bind(listMode.getSelectionModel().selectedItemProperty());

        //Sélection du nombre de joueurs
        ObservableList<String> options = FXCollections.observableArrayList("1 Joueur");
        listJoueurs.setValue("1 Joueur");
        listJoueurs.setItems(options);
        labelJoueur.textProperty().bind(listJoueurs.getSelectionModel().selectedItemProperty());

        //Set on action des différents boutons
        playEvent();
        closeEvent();
    }

    /**
     * Set l'action du bouton close
     */
    public void closeEvent() {
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
     * Set l'action du bouton play
     */
    public void playEvent() {
        playButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource(GameManager.VIEW_JEU_FXML)));
                JeuController jeu = new JeuController(labelMode.getText(), labelJoueur.getText());
                loader.setController(jeu);
                Pane root = loader.load();
                Stage stage = (Stage) playButton.getScene().getWindow();
                stage.setTitle("Jeu Survie - Jeu");
                GestionnaireClavier ges = jeu.lancerJeu(root);
                Scene scene = new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT);
                scene.setOnKeyPressed(ges::OnKeyPressed);
                scene.setOnKeyReleased(ges::OnKeyReleased);
                stage.setScene(scene);
                stage.show();
                jeu.initJeu();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
