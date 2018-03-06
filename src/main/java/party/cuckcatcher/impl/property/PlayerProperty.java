package party.cuckcatcher.impl.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import party.cuckcatcher.impl.alert.Alert;

import java.util.UUID;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class PlayerProperty {

    @Getter
    private final PlayerPropertyFactory playerPropertyFactory = new PlayerPropertyFactory();

    private final UUID uuid;

    public void addAlert(Alert alert) {
        if (alert.isAlert()) {
            Bukkit.getOnlinePlayers().stream()
                    .filter(Player::isOp)
                    .forEach(player -> {
                        player.sendMessage(String.format("\u00a77(#) \u00a7c[CC] \u00a73%s \u00a77logged %s %d VL (#)", Bukkit.getPlayer(this.uuid).getName(), alert.getType().getLabel(), alert.getTypeInfo().getAlertManager().getCurrentVl()));
                    });
        }
    }
}
