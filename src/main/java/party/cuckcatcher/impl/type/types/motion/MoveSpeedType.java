package party.cuckcatcher.impl.type.types.motion;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.v1_8_R3.AttributeModifiable;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.filters.motion.EventMoveFilter;
import party.cuckcatcher.impl.property.PlayerProperty;

import java.util.Collection;
import java.util.UUID;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@TypeManifest(label = "MoveSpeed")
public class MoveSpeedType extends Type {
    private UUID movementSpeedUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");

    //this is all from the minecraft source code, it's still a work in progress

    @EventListener
    private Link<EventMove> onMoveListener = new Link<>(event -> {
        PlayerProperty playerProperty = event.getPlayerProperty();

        double horizontalDistance = event.getHorizontalDistance(),
                verticalDistance = event.getVerticalDistance();

        double horizontalSpeed = playerProperty.moveSpeed;

        double speedLimit = playerProperty.underBlock ? 1.17453292 : 1.0;

        double blockFriction = 0.91;

        if (playerProperty.onGround) {

            horizontalSpeed *= 1.3;
            blockFriction *= 0.91;
            horizontalSpeed *= 0.16277136 / (blockFriction * blockFriction * blockFriction);

            if (verticalDistance > 1.0E-4) {
                horizontalSpeed += 0.2;
            }

        } else {
            horizontalSpeed = 0.026;
            blockFriction = 0.91;
        }

        double moveSpeed = (horizontalDistance - playerProperty.previousHorizontalDistance) / horizontalSpeed;

        moveSpeed *= 0.98;

        if (moveSpeed > speedLimit) {
            Bukkit.broadcastMessage(String.format("%s", moveSpeed));
        }

        playerProperty.previousHorizontalDistance = horizontalDistance * blockFriction;

    }, new EventMoveFilter(false));

    private double updateMovementSpeed(EntityLiving entity) {
        AttributeModifiable attribute = (AttributeModifiable) entity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
        double base = attribute.b();

        AtomicDouble value = new AtomicDouble(base);

        attribute.a(0).forEach(modifier -> value.getAndAdd(modifier.d()));

        attribute.a(1).forEach(modifier -> value.getAndAdd(modifier.d() * base));

        attribute.a(2).stream().filter(modifier -> !modifier.a().equals(this.movementSpeedUUID)).forEach(modifier -> {
            double newValue = value.get();

            newValue *= 1.0 + modifier.d();
            value.set(newValue);
        });

        return value.get();
    }
}
