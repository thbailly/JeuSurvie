package Controller;

import Gestionnaire.GameManager;
import Metier.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ScoresController {

    private GameManager gm = new GameManager();

    @FXML
    private Button closeButton;
    @FXML
    private TableView<Score> listScores;

    @FXML
    public void initialize() {
        TableColumn<Score, String> pseudoCol = new TableColumn<>("Pseudo");
        pseudoCol.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        TableColumn<Score, String> tempsCol = new TableColumn<>("Temps");
        tempsCol.setCellValueFactory(new PropertyValueFactory<>("temps"));
        TableColumn<Score, Integer> nbWavesCol = new TableColumn<>("Nombres de vagues");
        nbWavesCol.setCellValueFactory(new PropertyValueFactory<>("nbVagues"));
        TableColumn<Score, Integer> cptMobCol = new TableColumn<>("Monstres tués");
        cptMobCol.setCellValueFactory(new PropertyValueFactory<>("cptMob"));

        listScores.getColumns().addAll(pseudoCol, tempsCol, nbWavesCol, cptMobCol);
        listScores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Score> liste = FXCollections.observableArrayList(GameManager.listeScores);
        listScores.setItems(liste);

        closeEvent();
    }

    /**
     * Set de l'action du bouton qui ferme
     */
    public void closeEvent(){
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

}
