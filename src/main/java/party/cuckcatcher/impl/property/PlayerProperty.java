package party.cuckcatcher.impl.property;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class PlayerProperty {

    private final UUID uuid;

    public double previousHorizontalDistance = 0,
    jumpTicks = 0,
    airTicks = 0;

    public boolean assumeHitGround = false,
    onGround = false,
    underBlock = false;
}
