package party.cuckcatcher.impl.type.types.player;

import com.comphenix.protocol.PacketType;
import org.bukkit.entity.Player;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.alert.Alert;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.universal.EventPacket;
import party.cuckcatcher.impl.filters.packet.PacketFilter;
import party.cuckcatcher.impl.property.PlayerProperty;

import static com.comphenix.protocol.PacketType.Play.Client.ABILITIES;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@TypeManifest(label = "PlayerAbilities", maxVl = 1)
public class PlayerAbilitiesType extends Type {

    @EventListener
    private Link<EventPacket> onPacketReceived = new Link<>(event -> {
        Player player = event.getPlayer();

        PlayerProperty playerProperty = this.getCuckCatcher().getPlayerPropertyManager().getProperty(player);

        if (!player.isFlying() && !player.getAllowFlight()) {
            playerProperty.addAlert(new Alert(this, playerProperty));
        }

    }, new PacketFilter(PacketType.Play.Client.ABILITIES));
}
