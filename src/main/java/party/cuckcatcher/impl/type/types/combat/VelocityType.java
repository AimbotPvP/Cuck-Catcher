package party.cuckcatcher.impl.type.types.combat;

import org.bukkit.GameMode;
import org.bukkit.Material;
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
 * @author SkidRevenant
 * at 06/03/2018
 */
@TypeManifest(label = "Velocity")
public class VelocityType extends Type {

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        PlayerProperty playerProperty = event.getPlayerProperty();

        Player player = event.getPlayer();

        if (player.isFlying() || player.getGameMode() == GameMode.CREATIVE) return;

        Bridge bridge = this.getCuckCatcher().getBridge();

        double horizontalDistance = event.getHorizontalDistance(),
                verticalDistance = event.getVerticalDistance();

        boolean wasToGround = event.getPlayerMoveEvent().getTo().clone().subtract(0.0, 0.01, 0.0).getBlock().getType() != Material.AIR;

        if (event.isFromGround() && !wasToGround && verticalDistance < 0.41999998688697815 && !bridge.onGround(player) && !playerProperty.getPlayerPropertyFactory().underBlock) {
            VelocityContainer velocityContainer = playerProperty.getPlayerPropertyFactory().getVelocityContainer();

            double velocity = velocityContainer.getLowest(1);

            if (velocity > 0.0 && verticalDistance < velocity) {

                int velocityPercentage = (int) Math.round(200.0 * verticalDistance / velocity);

                if (velocityPercentage < 50 && playerProperty.getStrikeCollectorFactory().getVerticalKbCollector().strike()) {
                    playerProperty.addAlert(new Alert(this, playerProperty, "Vertical"));
                }
            } else {
                playerProperty.getStrikeCollectorFactory().getVerticalKbCollector().reset();
            }
        }

        if (horizontalDistance < 0.2) {
            VelocityContainer velocityContainer = playerProperty.getPlayerPropertyFactory().getVelocityContainer();

            double velocity = velocityContainer.getLowest(3);
            if (velocity > 0.0 && horizontalDistance < velocity * 0.99) {
                int horizontalPercentage = (int) Math.round(50.0 * horizontalDistance / velocity);

                if (horizontalPercentage < 20 && playerProperty.getStrikeCollectorFactory().getHorizontalKbCollector().strike()) {
                    playerProperty.addAlert(new Alert(this, playerProperty, "Horizontal"));
                } else {
                    playerProperty.getStrikeCollectorFactory().getHorizontalKbCollector().reset();
                }
            }

        }
    }, new EventMoveFilter(false));
}
