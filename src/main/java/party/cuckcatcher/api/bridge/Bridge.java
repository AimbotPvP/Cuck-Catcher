package party.cuckcatcher.api.bridge;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Made by SkidRevenant at 05/03/2018
 */
public interface Bridge {

    double updateMovementSpeed(Entity player);

    double getBlockfriction(Player player);

    boolean onGround(Player player);
}
