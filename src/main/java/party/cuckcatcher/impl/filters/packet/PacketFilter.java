package party.cuckcatcher.impl.filters.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 * Made by SkidRevenant at 03/03/2018
 */
public class PacketFilter implements Predicate<PacketEvent> {

    private final PacketType[] types;

    public PacketFilter(PacketType... types) {
        this.types = types;
    }

    @Override
    public boolean test(PacketEvent packetEvent) {
        PacketType packetType = packetEvent.getPacketType();

        AtomicBoolean isValidType = new AtomicBoolean(false);

        Arrays.stream(types).filter(type -> type == packetType)
                .forEach(type -> isValidType.set(true));

        return isValidType.get();
    }
}
