package party.cuckcatcher.impl.util;

import lombok.Getter;

/**
 * @author SkidRevenant
 * at 06/03/2018
 */
@Getter
public class Velocity {

    private final long velocityTime;

    private final double x, y, z, xz;

    public Velocity(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xz = x * x + z * z;
        this.velocityTime = System.currentTimeMillis();
    }
}