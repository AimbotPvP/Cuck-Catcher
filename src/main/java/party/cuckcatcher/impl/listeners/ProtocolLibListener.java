package party.cuckcatcher.impl.listeners;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import party.cuckcatcher.api.listener.Listener;
import party.cuckcatcher.impl.CuckCatcher;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class ProtocolLibListener implements Listener {

    @Getter
    private final CuckCatcher cuckCatcher;

    private PacketListener packetListener;

    @Override
    public void enable() {
        this.packetListener = new PacketListener(this.getCuckCatcher());
        ProtocolLibrary.getProtocolManager().addPacketListener(this.packetListener);
    }

    @Override
    public void disable() {
        ProtocolLibrary.getProtocolManager().removePacketListener(this.packetListener);
    }
}
