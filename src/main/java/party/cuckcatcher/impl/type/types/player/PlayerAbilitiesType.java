package party.cuckcatcher.impl.type.types.player;

import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.filters.packet.PacketFilter;

import static com.comphenix.protocol.PacketType.Play.Client.ABILITIES;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@TypeManifest(label = "PlayerAbilities", maxVl = 1)
public class PlayerAbilitiesType extends Type {

    protected Link<PacketEvent> onPacketReceived = new Link<>(event -> {

        Player player = event.getPlayer();

        if (!player.isFlying() && !player.getAllowFlight()) {
            //TODO: Add an alert
        }

    }, new PacketFilter(ABILITIES));
}
