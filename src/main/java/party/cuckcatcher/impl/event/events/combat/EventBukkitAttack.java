package party.cuckcatcher.impl.event.events.combat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * @author SkidRevenant
 * at 08/03/2018
 */

@RequiredArgsConstructor
@Getter
@Setter
public class EventBukkitAttack {

    private final Player attacker;

    private final Entity defender;

    private final PlayerProperty playerProperty;
}
