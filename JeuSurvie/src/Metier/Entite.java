package Metier;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Définis une entité
 */
public abstract class Entite {
    /**
     * Définis la position de l'entité
     */
    private Position position;
    /**
     * Définis le nom de l'entité
     */
    private String nom;
    /**
     * Définis le nombre de points de vie de l'entité
     */
    private SimpleIntegerProperty nbPdv = new SimpleIntegerProperty();
    /**
     * Définis le skin de l'entité
     */
    private String skin;
    /**
     * Définis la hauteur de l'entité
     */
    private double hauteur = 0;
    /**
     * Définis la largeur de l'entité
     */
    private double largeur = 0;
    /**
     * Définis si l'entité est vivante ou non
     */
    private boolean isAlive = true;

    public Entite(String nom, int nbPdv, Position pos, String skin) {
        this.nom = nom;
        setNbPdvProperty(nbPdv);
        this.position = pos;
        this.skin = skin;
    }

    public int getNbPdv() {
        return nbPdv.get();
    }

    public SimpleIntegerProperty getNbPdvProperty() {
        return nbPdv;
    }

    public void setNbPdvProperty(int val) {
        nbPdv.set(val);
    }

    public String getNom() {
        return this.nom;
    }

    public String getSkin() {
        return this.skin;
    }

    private void setNbPdv(int nbPdv) {
        if (nbPdv <= 0) {
            isAlive = false;
            setNbPdvProperty(0);
        } else this.nbPdv.set(nbPdv);
    }

    /**
     * Gère la vie de l'entité si celle-ci se fait frapper
     *
     * @param force force du coup
     */
    public void seFaireFrapper(int force) {
        if (getNbPdv() < 0)
            setNbPdv(0);
        else
            setNbPdv(getNbPdv() - force);
    }

    /**
     * Permet de frapper une entité
     *
     * @param e     entité a frapper
     * @param force force de frappe
     */
    public void frapper(Entite e, int force) {
        e.seFaireFrapper(force);
    }

    public Position getPosition() {
        return position;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
