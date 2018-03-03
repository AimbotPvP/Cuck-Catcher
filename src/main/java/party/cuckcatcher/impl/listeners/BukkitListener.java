package party.cuckcatcher.impl.listeners;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import party.cuckcatcher.api.listener.Listener;
import party.cuckcatcher.impl.CuckCatcher;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class BukkitListener implements Listener, org.bukkit.event.Listener {

    @Getter
    private final CuckCatcher cuckCatcher;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

    }

    @Override
    public void enable() {
        this.getCuckCatcher().getServer().getPluginManager().registerEvents(this, this.getCuckCatcher());
    }

    @Override
    public void disable() {

    }
}
