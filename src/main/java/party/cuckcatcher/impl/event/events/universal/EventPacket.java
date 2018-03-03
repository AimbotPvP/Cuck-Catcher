package party.cuckcatcher.impl.event.events.universal;

import com.comphenix.protocol.events.PacketEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.bukkit.entity.Player;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 2018-02-28
 */
@RequiredArgsConstructor
@Getter
@Setter
public class EventPacket {

    private final PacketEvent packetEvent;

    private final Player player;

    private final PlayerProperty playerProperty;
}
