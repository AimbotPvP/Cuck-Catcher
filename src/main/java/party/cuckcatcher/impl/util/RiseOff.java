package party.cuckcatcher.impl.util;

import lombok.Getter;

/**
 * @author SkidRevenant
 * at 09/03/2018
 */
public enum RiseOff {

    NONE(0),
    LIGHT(1),
    NORMAL(2),
    MEDIUM(3),
    HARD(4),
    EXTREME(5),
    INTERMEDIATE(15),
    OBSCURE(10);

    @Getter
    private int maxRiseTicks;

    RiseOff(int maxRiseTicks) {
        this.maxRiseTicks = maxRiseTicks;
    }


}
