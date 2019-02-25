package Controller;

import Gestionnaire.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class LeaveButtonController {

    @FXML
    public Button closeButton;

    /**
     * Evenement appelé lorsque l'on clique sur le bouton
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void closeEvent(ActionEvent actionEvent) throws  Exception{
        VBox root = FXMLLoader.load(getClass().getResource(GameManager.VIEW_MENU_FXML));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.setTitle("Jeu Survie - Menu");
        stage.setScene(new Scene(root, GameManager.DEFAULT_WIDTH, GameManager.DEFAULT_HEIGHT));
        stage.show();
    }
}
