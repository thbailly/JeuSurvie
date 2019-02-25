package Thread;

import Gestionnaire.GameManager;
import Gestionnaire.GestionnaireCollision;
import Gestionnaire.GestionnaireMouvement;
import Metier.Joueur;
import Metier.Monstre;
import javafx.application.Platform;

/**
 * MouvementMonstreThread est un Thread permettant le déplacement d'un monstre
 */
public class MouvementMonstreThread extends Thread {
    /**
     * Temps d'attente entre chaque déplacement du monstre
     */
    public static final int DEMI_SECOND = 500;
    /**
     * Définis un monstre
     */
    private Monstre monstre;
    /**
     * Définis un joueur
     */
    private Joueur joueur;
    /**
     * Définis un gestionnaire de collision
     */
    private GestionnaireCollision gestionnaireCollision;

    public MouvementMonstreThread(Monstre monstre, Joueur joueur, GestionnaireCollision gestionnaireCollision) {
        this.monstre = monstre;
        this.joueur = joueur;
        this.gestionnaireCollision = gestionnaireCollision;
    }

    @Override
    public void run() {
        try {
            while (monstre.isAlive()) {
                Thread.sleep(DEMI_SECOND);
                Platform.runLater(() -> {
                    GestionnaireMouvement.mouvementMonstre(gestionnaireCollision, monstre, joueur, GameManager.PAS_MONSTRE);
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
