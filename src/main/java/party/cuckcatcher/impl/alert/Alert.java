package party.cuckcatcher.impl.alert;

import lombok.Getter;
import lombok.Setter;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.impl.property.PlayerProperty;
import party.cuckcatcher.impl.type.TypeInfo;

/**
 * Made by SkidRevenant at 04/03/2018
 */
@Getter
@Setter
public class Alert {

    private final Type type;

    private final PlayerProperty playerProperty;

    private final TypeInfo typeInfo;

    private final boolean alert;

    private String info;

    public Alert(Type type, PlayerProperty playerProperty, Object... args) {
        this.type = type;
        this.playerProperty = playerProperty;

        this.typeInfo = playerProperty.getPlayerPropertyFactory().get(type);
        this.alert = this.typeInfo.getAlertManager().alert();

        try {
            this.info = args == null ? "" : String.format(" (%s)", args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            this.info = "";
        }
    }
}
