package party.cuckcatcher.impl.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.impl.alert.AlertManager;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
@Getter
public class TypeInfo {

    private final Type type;

    private final AlertManager alertManager = new AlertManager(this.getType() == null ? 60 : this.getType().getTypeManifest().secsToExpire());
}
