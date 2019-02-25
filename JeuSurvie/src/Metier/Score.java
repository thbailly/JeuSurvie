package Metier;

import java.io.Serializable;

/**
 * Score de joueur enregistré à la fin d'une partie
 */
public class Score implements Serializable {
    /**
     * Temps total de la partie
     */
    private String temps;
    /**
     * Nombre de vagues
     */
    private Integer nbVagues;
    /**
     * Nombre de monstres tués
     */
    private Integer cptMob;
    /**
     * Pseudo du joueur
     */
    private String pseudo;

    public Score(String temps, int nbVagues, int cptMob, String pseudo){
        this.temps = temps;
        this.nbVagues = nbVagues;
        this.cptMob = cptMob;
        this.pseudo = pseudo;
    }

    public String getTemps() {
        return temps;
    }

    public Integer getNbVagues() {
        return nbVagues;
    }

    public Integer getCptMob() {
        return cptMob;
    }

    public String getPseudo() {
        return pseudo;
    }

    @Override
    public String toString(){
        return "[Score] " + this.getPseudo() + " - " + this.getTemps() + " - " + this.getNbVagues() + " - " + this.getCptMob();
    }
}
