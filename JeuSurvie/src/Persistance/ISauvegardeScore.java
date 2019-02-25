package Persistance;

import Metier.Score;

import java.util.List;

/**
 * Interface pour effectuer la sauvegarde de scores
 */
public interface ISauvegardeScore {

    void sauvegarde(List<Score> listScores);

}
