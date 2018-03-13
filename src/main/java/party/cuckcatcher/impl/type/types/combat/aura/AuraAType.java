package party.cuckcatcher.impl.type.types.combat.aura;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.alert.Alert;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.combat.EventBukkitAttack;
import party.cuckcatcher.impl.event.events.universal.EventPacket;
import party.cuckcatcher.impl.filters.packet.PacketFilter;
import party.cuckcatcher.impl.property.PlayerProperty;
import party.cuckcatcher.impl.util.LookProperty;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SkidRevenant
 * at 07/03/2018
 */
@TypeManifest(label = "Aura (Type A)")
public class AuraAType extends Type {

    @EventListener
    private Link<EventBukkitAttack> onAttackListener = new Link<>(event -> {
        Player player = event.getAttacker();

        if (player.isFlying() || player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        Entity defender = event.getDefender();

        PlayerProperty playerProperty = event.getPlayerProperty();
    });

    @EventListener
    private Link<EventPacket> onLookListener = new Link<>(event -> {
        PlayerProperty playerProperty = event.getPlayerProperty();

        PacketContainer packetContainer = event.getPacketEvent().getPacket();

        float yaw = packetContainer.getFloat().read(0),
                pitch = packetContainer.getFloat().read(1);

        playerProperty.getPlayerPropertyFactory().getLookList().add(new LookProperty(yaw - playerProperty.getPlayerPropertyFactory().lastYaw, pitch - playerProperty.getPlayerPropertyFactory().lastPitch));

        if (playerProperty.getPlayerPropertyFactory().getLookList().size() == 128) {

            AtomicInteger level = new AtomicInteger(0);

            playerProperty.getPlayerPropertyFactory().getLookList().stream()
                    .filter(lookProperty -> lookProperty.getYaw() != 0.f && lookProperty.getPitch() != 0.f)
                    .forEach(lookProperty -> {

                        float yawDelta = lookProperty.getYaw(),
                                pitchDelta = lookProperty.getPitch();

                        if (yawDelta <= 0.001 || pitchDelta <= 0.001) {
                            level.getAndIncrement();
                        }
                    });

            double averageYawDelta = playerProperty.getPlayerPropertyFactory().getLookList().stream()
                    .mapToDouble(LookProperty::getYaw)
                    .average()
                    .getAsDouble();

            double averagePitchDelta = playerProperty.getPlayerPropertyFactory().getLookList().stream()
                    .mapToDouble(LookProperty::getPitch)
                    .average()
                    .getAsDouble();

            if ((averageYawDelta < 1.d && averageYawDelta > 0.d || averagePitchDelta <=- 0.01) && level.get() >= 90) {
                playerProperty.addAlert(new Alert(this, playerProperty));
            }

            playerProperty.getPlayerPropertyFactory().getLookList().clear();
        }

        playerProperty.getPlayerPropertyFactory().lastYaw = yaw;
        playerProperty.getPlayerPropertyFactory().lastPitch = pitch;
    }, new PacketFilter(PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION_LOOK));
}
