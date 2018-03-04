package party.cuckcatcher.impl.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import party.cuckcatcher.api.manager.Manager;
import party.cuckcatcher.impl.CuckCatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class PlayerPropertyManager implements Manager {

    @Getter
    private final CuckCatcher cuckCatcher;

    private final Map<UUID, PlayerProperty> playerPropertyMap = new HashMap<>();

    public PlayerProperty getProperty(Player player) {
        return this.playerPropertyMap.computeIfAbsent(player.getUniqueId(), PlayerProperty::new);
    }

    @Override
    public void enable() {
        Bukkit.getOnlinePlayers().forEach(this::getProperty);
    }

    @Override
    public void disable() {
        this.playerPropertyMap.clear();
    }
}
