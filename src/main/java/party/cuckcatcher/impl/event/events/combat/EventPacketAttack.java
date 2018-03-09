package party.cuckcatcher.impl.event.events.combat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 2018-03-01
 */
@RequiredArgsConstructor
@Getter
@Setter
public class EventPacketAttack {

    private final Player attacker;

    private final Entity attacked;

    private final PlayerProperty playerProperty;
}
