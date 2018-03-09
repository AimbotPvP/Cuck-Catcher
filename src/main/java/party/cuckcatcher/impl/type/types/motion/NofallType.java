package party.cuckcatcher.impl.type.types.motion;

import org.bukkit.entity.Player;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.alert.Alert;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.filters.motion.EventMoveFilter;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * @author SkidRevenant
 * at 08/03/2018
 */
@TypeManifest(label = "Nofall")
public class NofallType extends Type {

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        Player player = event.getPlayer();

        double verticalDistance = event.getVerticalDistance();

        if (player.getFallDistance() == 0.d && verticalDistance <- 0.07840000152589255) {

            PlayerProperty playerProperty = event.getPlayerProperty();

            playerProperty.addAlert(new Alert(this, playerProperty));
        }

    });
}
