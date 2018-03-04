package party.cuckcatcher.impl.type.types.motion;

import org.bukkit.Location;
import org.bukkit.Material;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.filters.motion.EventMoveFilter;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 04/03/2018
 */
@TypeManifest(label = "Step")
public class StepType extends Type {

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {

        double verticalDistance = event.getVerticalDistance();

        PlayerProperty playerProperty = event.getPlayerProperty();

        Location from = event.getPlayerMoveEvent().getFrom();

        boolean wasFromGround = from.clone().subtract(0.0, 0.001, 0.0).getBlock().getType() != Material.AIR;

        if (event.isFromGround() && wasFromGround && verticalDistance > 0.5) {

        }

    }, new EventMoveFilter(false));
}
