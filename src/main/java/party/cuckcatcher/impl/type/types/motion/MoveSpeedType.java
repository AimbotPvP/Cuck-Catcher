package party.cuckcatcher.impl.type.types.motion;

import org.bukkit.Bukkit;

import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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

/**
 * Made by SkidRevenant at 03/03/2018
 */
@TypeManifest(label = "MoveSpeed")
public class MoveSpeedType extends Type {

    //this is mostly from the minecraft source code, it's still a work in progress
    //TODO: Make a velocity container and account for velocity

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        PlayerProperty playerProperty = event.getPlayerProperty();

        Player player = event.getPlayer();

        if (player.isFlying() || player.getGameMode() == GameMode.CREATIVE) return;

        Bridge bridge = this.getCuckCatcher().getBridge();

        double horizontalDistance = event.getHorizontalDistance(),
                verticalDistance = event.getVerticalDistance();

        double horizontalSpeed = playerProperty.getPlayerPropertyFactory().moveSpeed;

        double speedLimit = playerProperty.getPlayerPropertyFactory().underBlock ? 1.17453292 : 1.0;

        double blockFriction = playerProperty.getPlayerPropertyFactory().blockfriction;

        if (bridge.onGround(player)) {

            horizontalSpeed *= 1.3;
            blockFriction *= 0.91;
            horizontalSpeed *= 0.16277136 / (blockFriction * blockFriction * blockFriction);

            if (verticalDistance > 0.4199) {
                horizontalSpeed += 0.2;
            }

        } else {
            horizontalSpeed = 0.026;
            blockFriction = 0.91;
        }

        double previousHorizontal = Math.max(0.1, playerProperty.getPlayerPropertyFactory().previousHorizontalDistance);

        if (horizontalDistance - previousHorizontal > horizontalSpeed * 1.1) {
            playerProperty.getPlayerPropertyFactory().moveSpeed = Math.max(playerProperty.getPlayerPropertyFactory().moveSpeed, this.getCuckCatcher().getBridge().updateMovementSpeed(player));
        }

        double moveSpeed = (horizontalDistance - previousHorizontal) / horizontalSpeed;

        moveSpeed *= 0.98;

        if (horizontalDistance > 0.1) {
            playerProperty.getPlayerPropertyFactory().getMoveSpeedSamples().add(moveSpeed);
        }

        if (playerProperty.getPlayerPropertyFactory().getMoveSpeedSamples().size() == 8) {
            double averageSpeed = playerProperty.getPlayerPropertyFactory().getMoveSpeedSamples().stream().mapToDouble(d -> d).average().getAsDouble();

            if (Math.abs(averageSpeed) > 0.9604) {
                Bukkit.broadcastMessage("" + averageSpeed);
                playerProperty.addAlert(new Alert(this, playerProperty));
            }

            playerProperty.getPlayerPropertyFactory().getMoveSpeedSamples().clear();
        }

        if (moveSpeed > speedLimit) {
            playerProperty.addAlert(new Alert(this, playerProperty));
        }

        double friction = bridge.getBlockfriction(player);

        playerProperty.getPlayerPropertyFactory().previousHorizontalDistance = horizontalDistance * blockFriction;
        playerProperty.getPlayerPropertyFactory().blockfriction = friction < 0.6001 ? 0.91 : friction;

    }, new EventMoveFilter(false));
}
