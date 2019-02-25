package Persistance;

import Metier.Score;

import java.util.List;

/**
 * Interface pour effectuer le chargement des scores
 */
public interface IChargementScore {

    List<Score> chargement();
}
