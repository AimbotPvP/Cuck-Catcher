package party.cuckcatcher.impl.type.types.motion;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import party.cuckcatcher.api.bridge.Bridge;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.alert.Alert;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.filters.motion.EventMoveFilter;
import party.cuckcatcher.impl.property.PlayerProperty;
import party.cuckcatcher.impl.property.containers.VelocityContainer;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@TypeManifest(label = "MoveSpeed")
public class MoveSpeedType extends Type {

    //this is mostly from the minecraft source code, it's still a work in progress

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        PlayerProperty playerProperty = event.getPlayerProperty();

        Player player = event.getPlayer();

        if (player.isFlying() || player.getGameMode() == GameMode.CREATIVE) return;

        Bridge bridge = this.getCuckCatcher().getBridge();

        double verticalDistance = event.getVerticalDistance();

        double horizontalSpeed = playerProperty.getPlayerPropertyFactory().moveSpeed;

        double blockFriction = playerProperty.getPlayerPropertyFactory().blockfriction;

        boolean isPlayerOnGround = bridge.onGround(player);

        double speedLimit = playerProperty.getPlayerPropertyFactory().underBlock ? 1.38235 : 1.0;

        if (isPlayerOnGround) {

            blockFriction *= 0.91;

            horizontalSpeed *= blockFriction > 0.708 ? 1.3 : 0.23315;

            horizontalSpeed *= 0.16277136 / Math.pow(blockFriction, 3);

            if (verticalDistance > 0.4199) {
                horizontalSpeed += 0.2;
            }

        } else {
            horizontalSpeed = 0.026;
            blockFriction = 0.91;
        }

        double previousHorizontal = playerProperty.getPlayerPropertyFactory().previousHorizontalDistance;

        Location to = event.getPlayerMoveEvent().getTo(),
        from = event.getPlayerMoveEvent().getFrom();

        double deltaX = to.getX() - from.getX(),
                deltaZ = to.getZ() - from.getZ();

        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        if (horizontalDistance - previousHorizontal > horizontalSpeed * 0.1) {
            playerProperty.getPlayerPropertyFactory().moveSpeed = Math.max(playerProperty.getPlayerPropertyFactory().moveSpeed, this.getCuckCatcher().getBridge().updateMovementSpeed(player));

            VelocityContainer velocityContainer = playerProperty.getPlayerPropertyFactory().getVelocityContainer();

            double currentVelocity = velocityContainer.getHighest(3);

            if (currentVelocity > 0.0) {
                horizontalSpeed += Math.sqrt(currentVelocity);
            }

            double moveSpeed = (horizontalDistance - previousHorizontal) / horizontalSpeed;

            moveSpeed *= 0.98;

            if (moveSpeed > speedLimit && playerProperty.getStrikeCollectorFactory().getSpeedCollector().strike()) {
                playerProperty.addAlert(new Alert(this, playerProperty, moveSpeed));
                player.teleport(playerProperty.getPlayerPropertyFactory().lastGroundLocation);
            }

        }

        double friction = bridge.getBlockfriction(player);

        playerProperty.getPlayerPropertyFactory().previousHorizontalDistance = horizontalDistance * blockFriction;
        playerProperty.getPlayerPropertyFactory().blockfriction = friction;

    }, new EventMoveFilter(false));
}
