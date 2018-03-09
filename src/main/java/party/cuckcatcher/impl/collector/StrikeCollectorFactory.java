package party.cuckcatcher.impl.collector;

import lombok.Getter;

/**
 * @author SkidRevenant
 * at 06/03/2018
 */
public class StrikeCollectorFactory {

    @Getter
    private final StrikeCollector verticalKbCollector = new StrikeCollector(11, 30),
    horizontalKbCollector = new StrikeCollector(11, 30),
    averageSpeedCollector = new StrikeCollector(2, 3),
    speedCollector = new StrikeCollector(3, 0.45),
    attackMoveCollector = new StrikeCollector(10, 3);
}
