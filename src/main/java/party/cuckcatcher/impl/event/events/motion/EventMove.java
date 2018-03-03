package party.cuckcatcher.impl.event.events.motion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 2018-03-01
 */
@RequiredArgsConstructor
@Getter
@Setter
public class EventMove {

    private final PlayerMoveEvent playerMoveEvent;

    private final Player player;

    private final PlayerProperty playerProperty;

    private final double offsetH;

    private final boolean isFromGround;

}
