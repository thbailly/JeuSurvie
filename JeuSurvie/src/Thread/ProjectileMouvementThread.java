package Thread;

import Gestionnaire.GestionnaireCollision;
import Gestionnaire.GestionnaireMouvement;
import Enum.EDirection;
import Metier.Joueur;
import Metier.Projectile;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

/**
 * ProjectileMouvementThread est un Thread permettant de gérer le déplacement d'un projectile
 */
public class ProjectileMouvementThread extends Thread {
    /**
     * Temps entre chaque déplacement de projectile
     */
    public static final int TEMPS_DEPLACEMENT_PROJECTILE = 50;
    /**
     * Définis un projectile
     */
    private Projectile projectile;
    /**
     * Définis un gestionnaire de collision
     */
    private GestionnaireCollision gestionnaireCollision;
    /**
     * Définis la direction dans laquelle le projectile est tiré
     */
    private EDirection eDirection;
    /**
     * Définis l'ImageView du projectile
     */
    private ImageView imageView;
    /**
     * Définis la distance parcourue par le projectile en un déplacement
     */
    private int pas;
    /**
     * Définis le joueur qui à tiré le projectile
     */
    private Joueur joueur;

    public ProjectileMouvementThread(Projectile projectile, GestionnaireCollision gestionnaireCollision, EDirection eDirection, ImageView imageView, int pas, Joueur joueur) {
        this.projectile = projectile;
        this.gestionnaireCollision = gestionnaireCollision;
        this.eDirection = eDirection;
        this.imageView = imageView;
        this.pas = pas;
        this.joueur = joueur;
    }

    @Override
    public void run() {
        while (!projectile.isStop()) {
            Platform.runLater(() -> {
                GestionnaireMouvement.mouvementBalle(projectile, eDirection, pas);
                gestionnaireCollision.testCollisionBalle(projectile, pas, eDirection, imageView, joueur);
            });
            try {
                Thread.sleep(TEMPS_DEPLACEMENT_PROJECTILE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
