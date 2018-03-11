package party.cuckcatcher.impl.listeners;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import party.cuckcatcher.api.bridge.Bridge;
import party.cuckcatcher.api.listener.Listener;
import party.cuckcatcher.impl.CuckCatcher;
import party.cuckcatcher.impl.event.events.combat.EventBukkitAttack;
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

        Location groundLocation = from.clone().subtract(0.0, 0.01, 0.0);

        Bridge bridge = this.getCuckCatcher().getBridge();

        playerProperty.getPlayerPropertyFactory().onGround = isOnGround(groundLocation);
        playerProperty.getPlayerPropertyFactory().underBlock = bridge.underBlock(player);

        if (playerProperty.getPlayerPropertyFactory().onGround) {
            playerProperty.getPlayerPropertyFactory().lastGroundLocation = from;
        }

        playerProperty.getPlayerPropertyFactory().airTicks = playerProperty.getPlayerPropertyFactory().onGround ? 0 : playerProperty.getPlayerPropertyFactory().airTicks + 1;

        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        this.getCuckCatcher().getBus().post(new EventMove(event, player, playerProperty, horizontalDistance, verticalDistance, to.getY() > from.getY(), isOnGround(to.clone().subtract(0.0, 0.001, 0.0))));
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();

        Entity defender = event.getEntity();

        PlayerProperty playerProperty = this.getCuckCatcher().getPlayerPropertyManager().getProperty(player);

        this.getCuckCatcher().getBus().post(new EventBukkitAttack(player, defender, playerProperty));
    }

    private boolean isOnGround(Location location) {
        switch (location.getBlock().getType()) {
            case SNOW:
            case SNOW_BLOCK:
            case CARPET:
            case SKULL:
            case WATER_LILY:
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
