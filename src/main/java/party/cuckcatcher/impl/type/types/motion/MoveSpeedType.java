package party.cuckcatcher.impl.type.types.motion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.filters.motion.EventMoveFilter;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@TypeManifest(label = "MoveSpeed")
public class MoveSpeedType extends Type {

    //this is all from the minecraft source code, it's still a work in progress
    //TODO: check the first sprinting ticks

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        Player player = event.getPlayer();

        PlayerProperty playerProperty = event.getPlayerProperty();

        double horizontalDistance = event.getHorizontalDistance(),
                verticalDistance = event.getVerticalDistance();

        Location to = event.getPlayerMoveEvent().getTo();

        double deltaHorizontal = Math.max(0, horizontalDistance - event.getPlayerProperty().getPreviousHorizontalDistance());

        boolean isSprinting = player.isSprinting() && player.getFoodLevel() > 5;

        if (deltaHorizontal > 0) {

            boolean isJumpTick = playerProperty.jumpTicks > 0;

            double horizontalDistanceLimit = 0.98;

            boolean onGround = to.clone().subtract(0.0, 0.001, 0.0).getBlock().getType() != Material.AIR;

            if (event.isFromGround() && !onGround) {

                if (isSprinting) horizontalDistanceLimit *= 1.3;

                double blockFriction = 0.91;

                horizontalDistanceLimit *= .16277136F / (blockFriction * blockFriction * blockFriction);

                horizontalDistanceLimit *= isJumpTick ? 1.24 : 0.1;

                if (verticalDistance < 0.08) {
                    horizontalDistanceLimit *= 0.5;
                }

            } else {
                horizontalDistanceLimit = isSprinting ? 0.029872 : 0.01992536;
            }

            horizontalDistanceLimit *= 0.98;

            playerProperty.assumeHitGround = deltaHorizontal > 0.1f && deltaHorizontal > horizontalDistanceLimit;

            if (isJumpTick) {
                playerProperty.jumpTicks = 0;
            }

            if (playerProperty.assumeHitGround && horizontalDistanceLimit < 0.03) {
                horizontalDistanceLimit *= 4.75;
            }

            if (deltaHorizontal > horizontalDistanceLimit) {

                Bukkit.broadcastMessage(String.format("%s : %s", deltaHorizontal, horizontalDistanceLimit));
            }
        }

        playerProperty.setPreviousHorizontalDistance(Math.max(0.1, horizontalDistance * 0.91));

    }, new EventMoveFilter(false));
}
