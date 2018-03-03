package party.cuckcatcher.impl.event.events.universal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 2018-03-01
 */
@RequiredArgsConstructor
@Getter
@Setter
public class EventBukkit {

    private final Event event;

    private final Player player;

    private final PlayerProperty playerProperty;
}
