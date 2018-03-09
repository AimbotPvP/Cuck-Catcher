package party.cuckcatcher.impl.bridges.spigot1_8;

import com.google.common.util.concurrent.AtomicDouble;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import party.cuckcatcher.api.bridge.Bridge;

import java.util.UUID;

/**
 * Made by Bilk at 05/03/2018
 */
public class Spigot1_8 implements Bridge {

    private UUID movementSpeedUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");

    @Override
    public double updateMovementSpeed(Entity entity) {
        EntityLiving player = ((CraftPlayer) entity).getHandle();
        AttributeModifiable attribute = (AttributeModifiable) player.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
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

    @Override
    public double getBlockfriction(Player player) {
        EntityPlayer craftPlayer = ((CraftPlayer) player).getHandle();

        int blockX = NumberConversions.floor(craftPlayer.locX);
        int blockY = NumberConversions.floor(craftPlayer.locY - 1);
        int blockZ = NumberConversions.floor(craftPlayer.locZ);

        return craftPlayer.world.getType(new BlockPosition(blockX, blockY, blockZ)).getBlock().frictionFactor;
    }

    @Override
    public boolean onGround(Player player) {
        return ((CraftPlayer) player).getHandle().onGround;
    }

    @Override
    public boolean underBlock(Player player) {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

        return nmsPlayer.world.c(nmsPlayer.getBoundingBox().grow(0.5, 0.5, 0.5));
    }
}
