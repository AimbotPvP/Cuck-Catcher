package party.cuckcatcher.impl.property;

import lombok.Getter;
import org.bukkit.Location;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.impl.property.containers.VelocityContainer;
import party.cuckcatcher.impl.type.TypeInfo;
import party.cuckcatcher.impl.util.LookProperty;
import party.cuckcatcher.impl.util.RiseOff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Made by SkidRevenant at 04/03/2018
 */
public class PlayerPropertyFactory {

    private Map<Type, TypeInfo> typeInformation = new HashMap<>();

    @Getter
    private VelocityContainer velocityContainer = new VelocityContainer();

    public TypeInfo get(Type type) {
        TypeInfo typeInformation = this.typeInformation.get(type);

        if (typeInformation == null) {
            typeInformation = new TypeInfo(type);
            this.typeInformation.put(type, typeInformation);
        }

        return typeInformation;
    }

    public RiseOff riseOff = RiseOff.NONE;

    @Getter
    private List<LookProperty> lookList = new ArrayList<>();

    public float lastYaw,
    lastPitch;

    public double previousHorizontalDistance = 0,
            airTicks = 0,
            moveSpeed = 1.0,
            blockfriction = 0.91;

    public int flyTicks = 0,
    waterTicks = 0;

    public long lastMovePacketTime = 0L;

    public boolean onGround = false,
            underBlock = false,
            attacking = false,
            digging = false,
            placing = false;

    public Location lastGroundLocation = null;

    public void onMovePacket() {
        this.lastMovePacketTime = System.currentTimeMillis();
        this.digging = false;
        this.attacking = false;
        this.placing = false;
    }

    public boolean isMoving() {
        return System.currentTimeMillis() - this.lastMovePacketTime < 3;
    }
}
