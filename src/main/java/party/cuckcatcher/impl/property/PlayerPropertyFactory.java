package party.cuckcatcher.impl.property;

import lombok.Getter;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.impl.type.TypeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Made by SkidRevenant at 04/03/2018
 */
public class PlayerPropertyFactory {

    private Map<Type, TypeInfo> typeInformation = new HashMap<>();

    public TypeInfo get(Type type) {
        TypeInfo typeInformation = this.typeInformation.get(type);

        if (typeInformation == null) {
            typeInformation = new TypeInfo(type);
            this.typeInformation.put(type, typeInformation);
        }

        return typeInformation;
    }

    @Getter
    private List<Double> moveSpeedSamples = new ArrayList<>();

    public double previousHorizontalDistance = 0,
            jumpTicks = 0,
            airTicks = 0,
            moveSpeed = 1.0;

    public boolean assumeHitGround = false,
            onGround = false,
            underBlock = false,
            attacking = false,
            digging = false,
            placing = false;
}
