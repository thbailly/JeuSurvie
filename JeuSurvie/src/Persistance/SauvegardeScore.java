package Persistance;

import Metier.Score;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Sauvegarde en fichier binaire
 */
public class SauvegardeScore implements ISauvegardeScore {

    @Override
    public void sauvegarde(List<Score> listScores) {

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("listScores.txt")))) {
                objectOutputStream.writeObject(listScores);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
