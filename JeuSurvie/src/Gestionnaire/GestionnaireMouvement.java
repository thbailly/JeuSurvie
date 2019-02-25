package Gestionnaire;


import Enum.EDirection;
import Metier.*;

public class GestionnaireMouvement {
    /**
     * Permet le déplacement d'une entité
     *
     * @param gestionnaireCollision gestionnaire de collision contenant la liste des monstres et des joueurs
     * @param entite                entite que l'on souhaite déplacer
     * @param eDirection            direction dans laquelle l'entite doit se déplacer
     * @param pas                   distance à parcourir lors du déplacement
     */
    public static void bouger(GestionnaireCollision gestionnaireCollision, Entite entite, EDirection eDirection, int pas) {
        switch (eDirection) {
            case Haut:
                if (gestionnaireCollision.testCollisionMur(entite.getPosition(), entite.getHauteur(), entite.getLargeur(), pas, eDirection) && entite.isAlive())
                    entite.getPosition().setY(entite.getPosition().getY() - pas);
                break;
            case Bas:
                if (gestionnaireCollision.testCollisionMur(entite.getPosition(), entite.getHauteur(), entite.getLargeur(), pas, eDirection) && entite.isAlive())
                    entite.getPosition().setY(entite.getPosition().getY() + pas);
                break;
            case Droite:
                if (gestionnaireCollision.testCollisionMur(entite.getPosition(), entite.getHauteur(), entite.getLargeur(), pas, eDirection) && entite.isAlive())
                    entite.getPosition().setX(entite.getPosition().getX() + pas);
                break;
            case Gauche:
                if (gestionnaireCollision.testCollisionMur(entite.getPosition(), entite.getHauteur(), entite.getLargeur(), pas, eDirection) && entite.isAlive())
                    entite.getPosition().setX(entite.getPosition().getX() - pas);
                break;
        }
    }

    /**
     * Permet le déplacement des projectiles
     *
     * @param projectile projectile que l'on souhaite déplacer
     * @param eDirection direction dans laquelle le projectile doit aller
     * @param pas        distance que le projectile parcourt en un déplacement
     */

    public static void mouvementBalle(Projectile projectile, EDirection eDirection, int pas) {

        switch (eDirection) {
            case Haut:
                projectile.getPosition().setY(projectile.getPosition().getY() - pas);
                break;
            case Bas:
                projectile.getPosition().setY(projectile.getPosition().getY() + pas);
                break;
            case Gauche:
                projectile.getPosition().setX(projectile.getPosition().getX() - pas);
                break;
            case Droite:
                projectile.getPosition().setX(projectile.getPosition().getX() + pas);
                break;
        }

    }


    /**
     * Permet de déplacer les monstres en testant la collision avec un joueur ou un autre monstre
     *
     * @param gestionnaireCollision gestionnaire de collision contenant la liste des monstres et des joueurs
     * @param monstre               monstre que l'on souhaite déplacer
     * @param joueur                joueur avec lequel on veut tester la collision
     * @param pas                   distance que le monstre parcourt en un déplacement
     * @return true si collision avec un joueur sinon false
     */
    public static boolean mouvementMonstre(GestionnaireCollision gestionnaireCollision, Monstre monstre, Joueur joueur, int pas) {
        if (monstre.getPosition().getX() > joueur.getPosition().getX() && !gestionnaireCollision.testCollisionEntreMonstre(monstre))
            monstre.getPosition().setX(monstre.getPosition().getX() - pas);
        if (gestionnaireCollision.testCollisionEntreMonstre(monstre)) // Annule le déplacement si collision avec un autre monstre
            monstre.getPosition().setX(monstre.getPosition().getX() + pas);
        if (monstre.getPosition().getX() < joueur.getPosition().getX() && !gestionnaireCollision.testCollisionEntreMonstre(monstre))
            monstre.getPosition().setX(monstre.getPosition().getX() + pas);
        if (gestionnaireCollision.testCollisionEntreMonstre(monstre)) // Annule le déplacement si collision avec un autre monstre
            monstre.getPosition().setX(monstre.getPosition().getX() - pas);
        if (monstre.getPosition().getY() > joueur.getPosition().getY() && !gestionnaireCollision.testCollisionEntreMonstre(monstre))
            monstre.getPosition().setY(monstre.getPosition().getY() - pas);
        if (gestionnaireCollision.testCollisionEntreMonstre(monstre)) // Annule le déplacement si collision avec un autre monstre
            monstre.getPosition().setY(monstre.getPosition().getY() + pas);
        if (monstre.getPosition().getY() < joueur.getPosition().getY() && !gestionnaireCollision.testCollisionEntreMonstre(monstre))
            monstre.getPosition().setY(monstre.getPosition().getY() + pas);
        if (gestionnaireCollision.testCollisionEntreMonstre(monstre)) // Annule le déplacement si collision avec un autre monstre
            monstre.getPosition().setY(monstre.getPosition().getY() - pas);
        return gestionnaireCollision.testCollisionMonstre(monstre, joueur);
    }
}
