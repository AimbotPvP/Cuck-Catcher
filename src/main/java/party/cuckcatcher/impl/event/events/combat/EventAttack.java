package party.cuckcatcher.impl.event.events.combat;

import com.comphenix.protocol.events.PacketEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.bukkit.entity.Player;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 2018-03-01
 */
@RequiredArgsConstructor
@Getter
@Setter
public class EventAttack {

    private final Player attacker, attacked;

    private final PacketEvent packetEvent;

    private final PlayerProperty playerProperty, attackedProperty;
}
