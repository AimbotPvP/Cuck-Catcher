package party.cuckcatcher.impl.collector;

/**
 * @author SkidRevenant
 * at 06/03/2018
 */
public class StrikeCollector {

    private long lastStrikeTime;

    private final double timeToExpire;

    private int currentStrike = 0, requirement = 0;

    StrikeCollector(int requirement, double time) {
        this.requirement = requirement;
        this.timeToExpire = time * 10000;
        this.lastStrikeTime = System.currentTimeMillis();
    }

    public boolean strike() {
        if (System.currentTimeMillis() - this.lastStrikeTime > this.timeToExpire) {
            currentStrike = 0;
            this.lastStrikeTime = System.currentTimeMillis();
            return false;
        }

        this.lastStrikeTime = System.currentTimeMillis();

        ++currentStrike;

        return currentStrike > requirement;
    }

    public void reset() {
        this.currentStrike = 0;
    }
}
