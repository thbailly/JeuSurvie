package Gestionnaire;

import Enum.EDirection;
import Metier.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Map;

public class GestionnaireCollision {
    private Map<Joueur, ImageView> joueurMap;
    private Map<Monstre, ImageView> monstreMap;
    private Pane pane;

    public GestionnaireCollision(Pane pane, Map<Joueur, ImageView> joueurMap, Map<Monstre, ImageView> monstreMap) {
        this.pane = pane;
        this.joueurMap = joueurMap;
        this.monstreMap = monstreMap;
    }


    /**
     * Permet de tester entre un projectile et un monstre ou un mur
     *
     * @param projectile projectile sur lequel on souhaite effectuer les tests
     * @param pas        distance parcourue par le projectile en un déplacement
     * @param eDirection direction dans laquelle va le projectile
     * @param imageView  imageView du projectile
     * @param joueur     joueur qui a tiré le projectile
     * @return true si collision sinon false
     */
    public boolean testCollisionBalle(Projectile projectile, int pas, EDirection eDirection, ImageView imageView, Joueur joueur) {

        if (!testCollisionMur(projectile.getPosition(), projectile.getHauteur(), projectile.getLargeur(), pas, eDirection)) {
            projectile.setStop(true);
            pane.getChildren().remove(imageView);
            return true;
        }
        for (Map.Entry<Monstre, ImageView> entry : monstreMap.entrySet()) {
            if (entry.getValue().intersects(imageView.getBoundsInParent())) {
                entry.getKey().seFaireFrapper(projectile.getForce());
                if (!entry.getKey().isAlive()) {
                    pane.getChildren().remove(entry.getValue());
                    monstreMap.remove(entry.getKey());
                    joueur.setCptMob(joueur.getCptMob() + 1);
                }
                pane.getChildren().remove(imageView);
                projectile.setStop(true);
                return true;
            }
        }
        return false;
    }


    /**
     * Permet de tester la collision entre un monstre et un joueur
     *
     * @param monstre monstre sur le quel on veut effectuer le test
     * @param joueur  joueur sur le quel on veut effectuer le test
     * @return true si il y'a collision sinon false
     */
    public boolean testCollisionMonstre(Monstre monstre, Joueur joueur) {
        ImageView imageViewMonstre = monstreMap.get(monstre);
        ImageView imageViewJoueur = joueurMap.get(joueur);

        if (imageViewJoueur == null || imageViewMonstre == null)
            return false;
        if (imageViewJoueur.getBoundsInParent().intersects(imageViewMonstre.getBoundsInParent())) {
            monstre.frapper(joueur, monstre.getForce());
            if (!joueur.isAlive()) {
                pane.getChildren().remove(imageViewJoueur);
                joueurMap.remove(joueur);
            }
            return true;
        }
        return false;
    }

    /**
     * Permet de tester la collision entre une entité et un mur
     *
     * @param position   position de l'entité
     * @param hauteur    hauteur de l'entité
     * @param largeur    largeur de l'entité
     * @param pas        taille du déplacement à effectuer
     * @param eDirection direction dans laquelle s'effectue le pas
     * @return true si le déplacement est possible sinon false
     */
    public boolean testCollisionMur(Position position, double hauteur, double largeur, int pas, EDirection eDirection) {
        switch (eDirection) {
            case Haut:
                return (position.getY() - pas >= 0);
            case Bas:
                return (position.getY() + hauteur + pas <= pane.getBoundsInParent().getMaxY());
            case Droite:
                return (position.getX() + largeur + pas <= pane.getBoundsInParent().getMaxX());
            case Gauche:
                return (position.getX() - pas >= 0);
        }
        return false;
    }

    /**
     * Test de collision pour l'apparition des monstres afin que deux monstres ne soient pas superposés
     *
     * @param monstre   monstre que l'ont souhaite faire apparaître
     * @param imageView image de ce monstre
     * @return true si il y'a une collision sinon false
     */
    public boolean testCollisionSpawnMob(Monstre monstre, ImageView imageView) {
        for (Map.Entry<Monstre, ImageView> entry : monstreMap.entrySet()) {
            if (!monstre.equals(entry.getKey()))
                if (entry.getValue().getBoundsInParent().intersects(imageView.getBoundsInParent()))
                    return true;
        }
        return false;
    }

    /**
     * Permet de tester la collision entre deux monstres
     *
     * @param monstre monstre qui se déplace
     * @return true si collision sinon false
     */
    public boolean testCollisionEntreMonstre(Monstre monstre) {
        ImageView imageView = monstreMap.get(monstre);
        if (imageView == null)
            return false;
        for (Map.Entry<Monstre, ImageView> entry : monstreMap.entrySet()) {
            if (!entry.getKey().equals(monstre)) {
                if (entry.getValue().getBoundsInParent().intersects(imageView.getBoundsInParent()))
                    return true;
            }
        }
        return false;
    }
}
