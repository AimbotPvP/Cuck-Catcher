package party.cuckcatcher.impl.type.types.combat.aura;

import com.comphenix.protocol.PacketType;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.alert.Alert;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.combat.EventBukkitAttack;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.event.events.player.EventFlying;
import party.cuckcatcher.impl.event.events.universal.EventPacket;
import party.cuckcatcher.impl.filters.packet.PacketFilter;
import party.cuckcatcher.impl.property.PlayerProperty;

import java.lang.ref.WeakReference;

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

        playerProperty.setLastTarget(new WeakReference<>(defender));
    });

    @EventListener
    private Link<EventPacket> onLookListener = new Link<>(event -> {
        PlayerProperty playerProperty = event.getPlayerProperty();

        Player player = event.getPlayer();

        if (playerProperty.getLastTarget().get() != null) {
            Entity defender = playerProperty.getLastTarget().get();

            if (defender.isDead()) {
                playerProperty.getLastTarget().clear();
            }

            double distanceFromCenter = player.getLocation().getDirection().normalize()
                    .crossProduct(defender.getLocation().toVector().subtract(player.getLocation().toVector()))
                    .lengthSquared();



        }
    }, new PacketFilter(PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION_LOOK));
}
