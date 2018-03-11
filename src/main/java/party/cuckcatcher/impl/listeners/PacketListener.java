package party.cuckcatcher.impl.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import party.cuckcatcher.impl.CuckCatcher;
import party.cuckcatcher.impl.event.events.combat.EventPacketAttack;
import party.cuckcatcher.impl.event.events.player.EventFlying;
import party.cuckcatcher.impl.event.events.universal.EventPacket;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 03/03/2018
 */
public class PacketListener extends PacketAdapter {

    @Getter
    private final CuckCatcher cuckCatcher;

    PacketListener(CuckCatcher cuckCatcher) {
        super(cuckCatcher,
                /* COMBAT */
                PacketType.Play.Client.ARM_ANIMATION,
                PacketType.Play.Client.USE_ENTITY,

                /* MOTION */
                PacketType.Play.Client.FLYING,
                PacketType.Play.Client.POSITION,
                PacketType.Play.Client.POSITION_LOOK,
                PacketType.Play.Client.LOOK,

                /* PLAYER */
                PacketType.Play.Client.ABILITIES,
                PacketType.Play.Client.BLOCK_DIG,
                PacketType.Play.Client.BLOCK_PLACE,

                /* SERVER */
                PacketType.Play.Server.ENTITY_VELOCITY);
        this.cuckCatcher = cuckCatcher;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        PlayerProperty playerProperty = this.getCuckCatcher().getPlayerPropertyManager().getProperty(player);
        PacketType type = event.getPacketType();

        if (type == PacketType.Play.Client.USE_ENTITY) {
            PacketContainer packetContainer = event.getPacket();

            EnumWrappers.EntityUseAction action = packetContainer.getEntityUseActions().read(0);

            if (action == EnumWrappers.EntityUseAction.ATTACK) {

                PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity) packetContainer.getHandle();

                CraftPlayer craftPlayer = (CraftPlayer) player;

                Entity attackedEntity = packetPlayInUseEntity.a(craftPlayer.getHandle().getWorld());

                playerProperty.getPlayerPropertyFactory().attacking = true;

                this.getCuckCatcher().getBus().post(new EventPacketAttack(player, attackedEntity.getBukkitEntity(), playerProperty));
            }
        }

        switch (type.getCurrentId()) {
            case 13:    /* Flying */
            case 14:    /* Position */
            case 15:    /* Position_Look */
            case 16:    /* Look */
                playerProperty.getPlayerPropertyFactory().onMovePacket();
                this.getCuckCatcher().getBus().post(new EventFlying(player, playerProperty));
                break;

            case 20:    /* Block_Dig */
                break;

            case 32:    /* Block_Place */
                playerProperty.getPlayerPropertyFactory().placing = true;
                break;

        }

        this.getCuckCatcher().getBus().post(new EventPacket(event, player, playerProperty));

    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        PlayerProperty playerProperty = this.getCuckCatcher().getPlayerPropertyManager().getProperty(player);
        PacketType type = event.getPacketType();

        if (type == PacketType.Play.Server.ENTITY_VELOCITY) {
            PacketContainer packet = event.getPacket();

            if (player.getEntityId() != packet.getIntegers().read(0)) {
                return;
            }

            double x = packet.getIntegers().read(1) / 8000.0,
                    y = packet.getIntegers().read(2) / 8000.0,
                    z = packet.getIntegers().read(3) / 8000.0;

            playerProperty.getPlayerPropertyFactory().getVelocityContainer().addVelocity(x, y, z);
        }
    }
}
