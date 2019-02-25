package Metier;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Position contient un X et un Y permettant de placer une image sur le pane
 */
public class Position {
    /**
     * Définis une DoubleProperty x permettant le binding de la position X
     */
    private DoubleProperty x = new SimpleDoubleProperty();
    /**
     * Définis une DoubleProperty y permettant le binding de la position Y
     */
    private DoubleProperty y = new SimpleDoubleProperty();

    public Position(double x, double y) {
        this.x.setValue(x);
        this.y.setValue(y);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() == this.getClass()) {
            Position position = (Position) obj;
            return (position.getX() == this.getX() && position.getY() == this.getY());
        }
        return false;
    }
}
