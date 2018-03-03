package party.cuckcatcher.impl.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import party.cuckcatcher.impl.CuckCatcher;

/**
 * Made by SkidRevenant at 03/03/2018
 */
public class PacketListener extends PacketAdapter {

    @Getter
    private final CuckCatcher cuckCatcher;

    public PacketListener(CuckCatcher cuckCatcher) {
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
                PacketType.Play.Client.ABILITIES);
        this.cuckCatcher = cuckCatcher;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();


    }
}
