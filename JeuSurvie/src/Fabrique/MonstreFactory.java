package Fabrique;

import Enum.ETypeMonstre;
import Metier.Monstre;
import Metier.Position;

/**
 * MonstreFactory est une fabrique de monstres
 */
public class MonstreFactory {
    /**
     * Définis le skin d'un monstre normal
     */
    private static final String MONSTER_NORMAL = "resources/Images/monster-normal.png";
    /**
     * Définis le skin d'un monstre tank
     */
    private static final String MONSTER_TANK = "resources/Images/monster-tank.png";
    /**
     * Définis le nombre de points de vie d'un monstre normal
     */
    private static final int NB_PDV_NORMAL = 50;
    /**
     * Définis la force d'un monstre normal
     */
    private static final int FORCE_NORMAL = 10;
    /**
     * Définis le nombre de points de vie d'un monstre tank
     */
    private static final int NB_PDV_TANK = 150;
    /**
     * Définis la force d'un monstre de type tank
     */
    private static final int FORCE_TANK = 8;
    /**
     * Définis la position X de base d'un monstre
     */
    public static final int X_DEFAULT = 0;
    /**
     * Définis la position Y de base d 'un monstre
     */
    public static final int Y_DEFAULT = 0;

    /**
     * Retourne un monstre en fonction du type demandé en paramètre
     *
     * @param typeMonstre type demandé
     * @return null si le type demandé n'existe pas sinon un monstre de ce type
     */
    public static Monstre getMontre(ETypeMonstre typeMonstre) {
        switch (typeMonstre) {
            case normal:
                return new Monstre("normal", NB_PDV_NORMAL, FORCE_NORMAL, new Position(X_DEFAULT, Y_DEFAULT), MONSTER_NORMAL);
            case tank:
                return new Monstre("tank", NB_PDV_TANK, FORCE_TANK, new Position(X_DEFAULT, Y_DEFAULT), MONSTER_TANK);

        }
        return null;
    }

}
