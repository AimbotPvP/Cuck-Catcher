package party.cuckcatcher.impl.property;

import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.impl.type.TypeInfo;

import java.util.HashMap;
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
}
