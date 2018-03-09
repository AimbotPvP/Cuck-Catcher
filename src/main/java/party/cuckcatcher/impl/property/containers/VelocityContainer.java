package party.cuckcatcher.impl.property.containers;

import lombok.Getter;
import party.cuckcatcher.impl.util.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author SkidRevenant
 * at 06/03/2018
 */
public class VelocityContainer {

    private final Predicate<Velocity> velocityPredicate = velocity -> velocity.getVelocityTime() < System.currentTimeMillis() - 2000;

    @Getter
    private final List<Velocity> velocities = new ArrayList<>();

    public void addVelocity(double x, double y, double z) {
        this.velocities.add(new Velocity(x, y, z));
    }

    public double getLowest(int type) {
        this.velocities.removeIf(velocityPredicate);
        switch (type) {
            case 0:
                return this.getVelocities().stream().mapToDouble(Velocity::getX).min().orElse(0.0);

            case 1:
                return this.getVelocities().stream().mapToDouble(Velocity::getY).min().orElse(0.0);

            case 2:
                return this.getVelocities().stream().mapToDouble(Velocity::getZ).min().orElse(0.0);

            case 3:
                return this.getVelocities().stream().mapToDouble(Velocity::getXz).min().orElse(0.0);

            default:
                return 0.0;
        }
    }

    public double getHighest(int type) {
        this.velocities.removeIf(velocityPredicate);
        switch (type) {
            case 0:
                return this.getVelocities().stream().mapToDouble(Velocity::getX).max().orElse(0.0);

            case 1:
                return this.getVelocities().stream().mapToDouble(Velocity::getY).max().orElse(0.0);

            case 2:
                return this.getVelocities().stream().mapToDouble(Velocity::getZ).max().orElse(0.0);

            case 3:
                return this.getVelocities().stream().mapToDouble(Velocity::getXz).max().orElse(0.0);

            default:
                return 0.0;
        }
    }
}
