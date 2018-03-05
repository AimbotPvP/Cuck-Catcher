package party.cuckcatcher.impl.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.impl.alert.AlertManager;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class TypeInfo {

    @Getter
    private final Type type;

    @Getter
    private final AlertManager alertManager = new AlertManager(type == null ? 60 : type.getTypeManifest().secsToExpire());
}
