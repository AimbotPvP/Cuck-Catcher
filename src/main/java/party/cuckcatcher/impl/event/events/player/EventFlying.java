package party.cuckcatcher.impl.event.events.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * @author SkidRevenant
 * at 09/03/2018
 */
@RequiredArgsConstructor
@Getter
public class EventFlying {

    private final Player player;

    private final PlayerProperty playerProperty;
}
