package party.cuckcatcher.api.type;

import lombok.Getter;
import lombok.Setter;
import party.cuckcatcher.impl.CuckCatcher;
import party.cuckcatcher.impl.type.TypeManager;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@Getter
@Setter
public class Type {

    private String label;

    private int maxVl, secsToExpire;

    private boolean enabled, autoban;

    private TypeManifest typeManifest;

    private CuckCatcher cuckCatcher;

    public Type() {
        if (!this.getClass().isAnnotationPresent(TypeManifest.class)) {
            System.err.printf("Class \"%s\" does not have a type manifest.", this.getClass().getSimpleName());
            return;
        }

        this.typeManifest = this.getClass().getAnnotation(TypeManifest.class);

        this.label = this.typeManifest.label();
        this.enabled = this.typeManifest.enabled();
        this.autoban = this.typeManifest.autoban();
        this.maxVl = this.typeManifest.maxVl();
        this.secsToExpire = this.typeManifest.secsToExpire();

        System.out.println(String.format("Successfully initiated %s", this.getLabel()));
    }

    protected long now() {
        return System.currentTimeMillis();
    }

}
