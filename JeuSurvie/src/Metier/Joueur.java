package Metier;


import Enum.EDirection;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Un joueur est une entité possèdant une arme
 */
public class Joueur extends Entite{
    /**
     * Définis l'arme du joueur
     */
    private Arme arme;
    /**
     * Définis la dernière direction dans laquelle le joueur s'est déplacé
     */
    private EDirection lastDirection = EDirection.Droite;
    /**
     * Définis le skin de base d'un joueur
     */
    private static final String SKIN_BONHOMME="resources/Images/man.png";
    /**
     * Définis le compteur de monstres tués par un joueur
     */
    private SimpleIntegerProperty cptMob = new SimpleIntegerProperty(0);


    public Joueur(String nom, int nbPdv, Arme arme, Position pos) {
        super(nom, nbPdv, pos, SKIN_BONHOMME);
        this.arme = arme;
    }

    public EDirection getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(EDirection lastDirection) {
        if (this.lastDirection != lastDirection)
            this.lastDirection = lastDirection;
    }

    /**
     * Le joueur tire avec son arme
     *
     * @return le projectile tiré
     */
    public Projectile tirer() {
        return arme.tirer(getPosition());
    }

    public int getCptMob() {
        return cptMob.get();
    }

    public void setCptMob(int cptMob) {
        this.cptMob.set(cptMob);
    }

    public SimpleIntegerProperty getCptMobProperty(){
        return cptMob;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() == this.getClass()) {
            Joueur joueur = (Joueur) obj;
            return (joueur.getPosition().equals(this.getPosition()) && joueur.getHauteur() == this.getHauteur() && joueur.getLargeur() == this.getLargeur() && joueur.getLastDirection() == this.getLastDirection() && joueur.getNom() == this.getNom() && joueur.getNbPdv() == this.getNbPdv());
        }
        return false;
    }
}
