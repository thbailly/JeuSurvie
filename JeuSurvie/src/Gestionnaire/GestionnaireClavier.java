package Gestionnaire;

import Controller.JeuController;
import Enum.EDirection;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class GestionnaireClavier {
    private boolean bz, bd, bq, bs;
    private JeuController jeuController;

    public GestionnaireClavier(JeuController jeuController) {
        this.jeuController = jeuController;
    }

    /**
     * Détecte les touches pressées
     *
     * @param ke touche pressée
     */
    @FXML
    public void OnKeyPressed(KeyEvent ke) {
        switch (ke.getCode()) {
            case Z:
                bz = true;
                break;
            case S:
                bs = true;
                break;
            case Q:
                bq = true;
                break;
            case D:
                bd = true;
                break;
            case C:
                jeuController.tire();
                break;
        }
        lanceMouvement();
    }

    /**
     * Appelle les méthodes pour déplacer le joueur
     */
    public void lanceMouvement() {
        if (bz)
            jeuController.movePlayer(EDirection.Haut);
        if (bs)
            jeuController.movePlayer(EDirection.Bas);
        if (bq)
            jeuController.movePlayer(EDirection.Gauche);
        if (bd)
            jeuController.movePlayer(EDirection.Droite);
    }

    /**
     * Détecte les touches relachées
     *
     * @param ke touche relachée
     */
    @FXML
    public void OnKeyReleased(KeyEvent ke) {
        switch (ke.getCode()) {
            case Z:
                bz = false;
                break;
            case S:
                bs = false;
                break;
            case Q:
                bq = false;
                break;
            case D:
                bd = false;
                break;
        }
    }
}
