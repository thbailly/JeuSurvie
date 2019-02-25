package Gestionnaire;

import Metier.Joueur;
import Metier.Projectile;

public class GestionnaireProjectile {
    /**
     * Crée un nouveau projectile
     *
     * @param joueur joueur qui tire
     * @return un projectile avec une position initialisée
     */
    public static Projectile tirer(Joueur joueur) {
        Projectile projectile = joueur.tirer();
        GestionnaireProjectile.initPosition(joueur, projectile);
        return projectile;
    }

    /**
     * Initialise la position du projectile par rapport à la position du joueur qui tire
     *
     * @param joueur     joueur qui tire
     * @param projectile projectile tiré
     */
    private static void initPosition(Joueur joueur, Projectile projectile) {
        switch (joueur.getLastDirection()) {
            case Haut:
                projectile.getPosition().setX(projectile.getPosition().getX() + joueur.getLargeur() / 2);
                break;
            case Bas:
                projectile.getPosition().setX(projectile.getPosition().getX() + joueur.getLargeur() / 2);
                projectile.getPosition().setY(projectile.getPosition().getY() + joueur.getHauteur());
                break;
            case Gauche:
                projectile.getPosition().setY(projectile.getPosition().getY() + joueur.getHauteur() / 2);
                break;
            case Droite:
                projectile.getPosition().setY(projectile.getPosition().getY() + joueur.getHauteur() / 2);
                projectile.getPosition().setX(projectile.getPosition().getX() + joueur.getLargeur());
                break;
        }
    }
}
