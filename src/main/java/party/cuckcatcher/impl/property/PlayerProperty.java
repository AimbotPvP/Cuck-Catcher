package party.cuckcatcher.impl.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class PlayerProperty {

    private final UUID uuid;

    @Getter
    @Setter
    public double previousHorizontalDistance = 0,
    jumpTicks = 0,
    walkingTicks = 0;

    public boolean assumeHitGround = false;
}
