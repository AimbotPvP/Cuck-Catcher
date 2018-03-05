package party.cuckcatcher.impl.type.types.combat;

import org.bukkit.entity.Player;

import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.combat.EventAttack;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.filters.motion.EventMoveFilter;
import party.cuckcatcher.impl.property.PlayerProperty;

/**
 * Made by SkidRevenant at 04/03/2018
 */
@TypeManifest(label = "Criticals")
public class CriticalsType extends Type {

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        Player player = event.getPlayer();

        PlayerProperty playerProperty = event.getPlayerProperty();

        double verticalDistance = event.getVerticalDistance();

        if (event.isFromGround() && playerProperty.attacking && !playerProperty.underBlock) {

            if (verticalDistance < 0.3) {

            }
        }

        playerProperty.attacking = false;

    }, new EventMoveFilter(true));

    @EventListener
    private Link<EventAttack> onAttackListener = new Link<>(event -> {

        PlayerProperty playerProperty = event.getPlayerProperty();

        playerProperty.attacking = true;


    });
}
