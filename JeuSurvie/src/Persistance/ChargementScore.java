package Persistance;

import Metier.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Chargement de fichier binaire
 */
public class ChargementScore implements IChargementScore {

    @Override
    public List<Score> chargement() {
        List<Score> listeScore = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("listScores.txt")))) {
            listeScore = (List<Score>) objectInputStream.readObject();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return listeScore;
    }
}
