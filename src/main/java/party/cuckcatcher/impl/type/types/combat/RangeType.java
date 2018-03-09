package party.cuckcatcher.impl.type.types.combat;

import org.bukkit.Location;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.combat.EventPacketAttack;

/**
 * Made by SkidRevenant at 04/03/2018
 */
@TypeManifest(label = "Range")
public class RangeType extends Type {

    private Link<EventPacketAttack> eventAttackListener = new Link<>(event -> {

    });

    private Location getLocation(Location location, float yaw, double distance) {
        float xExpand = getYawExpand(yaw, distance, true),
                zExpand = getYawExpand(yaw, distance, false);
        return location.clone().add(xExpand, 0, zExpand);
    }

    private float getYawExpand(float yaw, double distance, boolean xAxis) {
        return xAxis ? (float) (Math.cos((yaw + 90.0F) * Math.PI / 180.0D) * distance) : (float) (Math.sin((yaw + 90.0F) * Math.PI / 180.0D) * distance);
    }

}
