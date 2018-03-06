package party.cuckcatcher.impl.listeners;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import party.cuckcatcher.api.listener.Listener;
import party.cuckcatcher.impl.CuckCatcher;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class BukkitListener implements Listener, org.bukkit.event.Listener {

    @Getter
    private final CuckCatcher cuckCatcher;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location from = event.getFrom(),
                to = event.getTo();

        double deltaX = to.getX() - from.getX(),
                deltaZ = to.getZ() - from.getZ();

        double horizontalDistance = Math.max(0.0, Math.sqrt(deltaX * deltaX + deltaZ * deltaZ)),
                verticalDistance = to.getY() - from.getY();

        PlayerProperty playerProperty = this.getCuckCatcher().getPlayerPropertyManager().getProperty(player);

        Location groundLocation = from.clone().subtract(0, 0.001, 0),
        aboveLocation = from.clone().add(0, 2.001, 0);

        //Bukkit.broadcastMessage(to.clone().subtract(0, 0.001, 0).getBlock().getType().name());

        playerProperty.getPlayerPropertyFactory().onGround = isOnGround(groundLocation);
        playerProperty.getPlayerPropertyFactory().underBlock = isOnGround(aboveLocation);

        playerProperty.getPlayerPropertyFactory().airTicks = playerProperty.getPlayerPropertyFactory().onGround ? 0 : playerProperty.getPlayerPropertyFactory().airTicks + 1;

        this.getCuckCatcher().getBus().post(new EventMove(event, player, playerProperty, horizontalDistance, verticalDistance, to.getY() > from.getY()));
    }

    private boolean isOnGround(Location location) {
        switch (location.getBlock().getType()) {
            case SNOW:
            case SNOW_BLOCK:
            case CARPET:
            case SKULL:
            case WATER_LILY:
                return true;

            case SIGN:
            case SIGN_POST:
            case WALL_SIGN:
                return true;

            default:
                return location.getBlock().getType().isSolid();
        }
    }

    @Override
    public void enable() {
        this.getCuckCatcher().getServer().getPluginManager().registerEvents(this, this.getCuckCatcher());
    }

    @Override
    public void disable() {

    }
}
