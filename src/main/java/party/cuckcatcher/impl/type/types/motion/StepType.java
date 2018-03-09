package party.cuckcatcher.impl.type.types.motion;

import org.bukkit.Location;
import org.bukkit.Material;
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
 * Made by SkidRevenant at 04/03/2018
 */
@TypeManifest(label = "Step")
public class StepType extends Type {

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {

        double verticalDistance = event.getVerticalDistance();

        PlayerProperty playerProperty = event.getPlayerProperty();

        if (event.isFromGround() && verticalDistance > 0.5) {

            VelocityContainer velocityContainer = playerProperty.getPlayerPropertyFactory().getVelocityContainer();

            double velocity = velocityContainer.getLowest(1);

            if (velocity == 0) {
                playerProperty.addAlert(new Alert(this, playerProperty));
            }
        }

    }, new EventMoveFilter(false));
}
