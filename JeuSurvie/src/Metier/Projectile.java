package Metier;

/**
 * Un projectile est tiré par une arme et peut frapper un monstre
 */
public class Projectile {
    /**
     * Définis la vitesse d'un projectile
     */
    private static final int VITESSE_BALLE = 20;
    /**
     * Définis la force d'un projectile
     */
    private int force;
    /**
     * Définis le skin d'un projectile
     */
    private String skin;
    /**
     * Définis la position du projectile
     */
    private Position position;
    /**
     * Définis la largeur du projectile
     */
    private double largeur;
    /**
     * Définis la hauteur du projectile
     */
    private double hauteur;
    /**
     * Définis si le projectile est en mouvement ou non
     */
    private boolean isStop = false;
    /**
     * Définis le skin par défaut d'un projectile
     */
    private static final String SKIN_DEFAUT = "Images/bullet2.png";

    public Projectile(int force, String skin, Position position) {
        this.force = force;
        this.skin = skin != null && !skin.isEmpty() ? skin : SKIN_DEFAUT;
        this.position = position;
    }

    public int getVitesseBalle() {
        return VITESSE_BALLE;
    }

    public int getForce() {
        return this.force;
    }

    public String getSkin() {
        return this.skin;
    }

    public Position getPosition() {
        return position;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isStop() {
        return isStop;
    }
}
