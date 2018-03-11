package party.cuckcatcher.impl.util;

/**
 * @author SkidRevenant
 * at 09/03/2018
 */
public class RiseUtil {

    public static RiseOff getRiseOffFromVerticalDistance(double verticalDistance) {
        return verticalDistance == 0.41999998688697815 ? RiseOff.LIGHT :
                verticalDistance == .33319999363422426 ? RiseOff.MEDIUM :
                        verticalDistance == .24813599859094637 ? RiseOff.HARD :
                                verticalDistance == .164773281826065 ? RiseOff.EXTREME :
                                        (verticalDistance == .0830778178064655 || verticalDistance == .08307781780646906 || verticalDistance == .015625 || verticalDistance == .0625 || verticalDistance == .033890786745502055 || verticalDistance == .046875) ? RiseOff.NORMAL :
                                                (verticalDistance >= .125 && verticalDistance <= .126 || verticalDistance == .25 || verticalDistance == .5) ? RiseOff.INTERMEDIATE :
                                                        (verticalDistance == 0.01250004768371582 || verticalDistance == .20000004768371582 || verticalDistance == .21497229621076386) ? RiseOff.NORMAL :
                                                                (verticalDistance == .4844449272978011 || (verticalDistance >= .4743096234328408 && verticalDistance <= .474396234328408) || verticalDistance == .4486043022532442 || verticalDistance == .43876546952123263 || verticalDistance == .09768912519418649 || verticalDistance == .375 || verticalDistance == .4657852174660846) ? RiseOff.INTERMEDIATE :
                                                                        verticalDistance == .02099999965727406 ? RiseOff.HARD :
                                                                                verticalDistance == .4641593749554431 ? RiseOff.INTERMEDIATE :
                                                RiseOff.OBSCURE;
    }
}
