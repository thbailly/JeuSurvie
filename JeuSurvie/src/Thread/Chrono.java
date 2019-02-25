package Thread;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Gère le timer de la partie
 */
public class Chrono extends Thread {
    /**
     * Définis une minute
     */
    private static final int ONE_MINUTE = 59;
    /**
     * Définis une heure
     */
    private static final int ONE_HOUR = 59;
    /**
     * Définis une seconde
     */
    private static final int SLEEP_ONE_SECOND = 1000;
    /**
     * Définis le nombre de secondes
     */
    private SimpleIntegerProperty sec = new SimpleIntegerProperty(0);
    /**
     * Définis le nombre de minutes
     */
    private SimpleIntegerProperty min = new SimpleIntegerProperty(0);
    /**
     * Définis le nombre d'heures
     */
    private SimpleIntegerProperty hour = new SimpleIntegerProperty(0);
    private StringProperty sp = new SimpleStringProperty();
    /**
     * Définis l'heure formatée hh:mm:ss
     */
    private StringBuilder sb = new StringBuilder();
    /**
     * Définis le label dans lequel est affiché le timer
     */
    @FXML
    private Label label;

    public Chrono(Label label) {
        this.label = label;
        label.textProperty().bind(sp);
    }

    /**
     * Lance le chrono
     */
    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                Platform.runLater(() -> {
                    if (sec.get() < ONE_MINUTE) {
                        sec.set(sec.get() + 1);
                    } else if (min.get() < ONE_HOUR) {
                        min.set(min.get() + 1);
                        sec.set(0);
                    } else {
                        hour.set(hour.get() + 1);
                        min.set(0);
                    }
                    sb.setLength(0);
                    if (hour.get() < 10)
                        sb.append("0" + hour.get());
                    else
                        sb.append(hour.get());
                    sb.append(":");
                    if (min.get() < 10)
                        sb.append("0" + min.get());
                    else
                        sb.append(min.get());
                    sb.append(":");
                    if (sec.get() < 10)
                        sb.append("0" + sec.get());
                    else
                        sb.append(sec.get());

                    sp.setValue(sb.toString());

                });
                Thread.sleep(SLEEP_ONE_SECOND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuilder getSb() {
        return sb;
    }
}
