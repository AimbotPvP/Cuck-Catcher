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
import party.cuckcatcher.impl.util.RiseOff;
import party.cuckcatcher.impl.util.RiseUtil;

/**
 * @author SkidRevenant
 * at 09/03/2018
 */
@TypeManifest(label = "Fly")
public class FlyType extends Type {

    //TODO:

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        Player player = event.getPlayer();

        PlayerProperty playerProperty = event.getPlayerProperty();

        boolean isOnGround = this.getCuckCatcher().getBridge().onGround(player) || playerProperty.getPlayerPropertyFactory().onGround;

        double verticalDistance = event.getVerticalDistance();

        playerProperty.getPlayerPropertyFactory().riseOff = RiseUtil.getRiseOffFromVerticalDistance(verticalDistance);

        RiseOff currentRiseOff = playerProperty.getPlayerPropertyFactory().riseOff;

        playerProperty.getPlayerPropertyFactory().flyTicks = isOnGround ? 0 : (playerProperty.getPlayerPropertyFactory().flyTicks += currentRiseOff.getMaxRiseTicks());

        if (currentRiseOff == RiseOff.OBSCURE || playerProperty.getPlayerPropertyFactory().flyTicks > 14) {
            playerProperty.addAlert(new Alert(this, playerProperty));
        }

    }, new EventMoveFilter(true));
}
