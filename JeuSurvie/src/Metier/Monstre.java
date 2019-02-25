package Metier;

/**
 * Un monstre est une entité pouvant frapper un joueur
 */
public class Monstre extends Entite {
    /**
     * Définis la force du monstre
     */
    private int force;

    public Monstre(String nom, int nbPdv, int force, Position pos, String skin) {
        super(nom, nbPdv, pos, skin);
        this.force = force;
    }

    public int getForce() {
        return force;
    }


}
