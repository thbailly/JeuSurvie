package Metier;

/**
 * Une arme est associée à un joueur et permet de tirer des projectiles
 */
public class Arme {
    /**
     * Force de l'arme
     */
    private int force;
    /**
     * Nombre de projectiles disponibles
     */
    private int nbCoups;

    public Arme(int force, int nbCoups) {
        this.force = force;
        this.nbCoups = nbCoups;
    }


    public int getForce() {
        return force;
    }

    public int getNbCoups() {
        return nbCoups;
    }

    public void setNbCoups(int nbCoups) {
        if (nbCoups <= 0) {
            this.nbCoups = 0;
        } else this.nbCoups = nbCoups;
    }

    /**
     * Tire un projectile
     *
     * @param position Position du joueur
     * @return le projectile
     */
    public Projectile tirer(Position position) {
        return new Projectile(force, null, new Position(position.getX(), position.getY()));
    }
}
