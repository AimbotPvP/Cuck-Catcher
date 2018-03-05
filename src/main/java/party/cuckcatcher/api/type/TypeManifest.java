package party.cuckcatcher.api.type;

/**
 * Made by SkidRevenant at 03/03/2018
 */
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeManifest {
    String label();

    int maxVl() default 5;

    boolean enabled() default true;

    boolean autoban() default true;

    int secsToExpire() default 60;
}
