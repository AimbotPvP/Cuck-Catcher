package party.cuckcatcher.impl.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import party.cuckcatcher.api.manager.Manager;
import party.cuckcatcher.api.type.Type;
import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.CuckCatcher;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
@Getter
public class TypeManager implements Manager {

    private final CuckCatcher cuckCatcher;

    private final List<Type> types = new ArrayList<>();

    @Override
    public void enable() {
        new Reflections("party.cuckcatcher.impl.type.types").getSubTypesOf(Type.class).stream()
                .filter(clazz -> clazz.isAnnotationPresent(TypeManifest.class))
                .forEach(this::intializeTypeFromClass);
    }

    @Override
    public void disable() {
        this.types.forEach(this.cuckCatcher.getBus()::unsubscribe);
        this.types.clear();
    }

    private Type intializeTypeFromClass(Class clazz) {
        Type type = null;
        try {
            type = (Type) clazz.newInstance();
            this.getCuckCatcher().getBus().subscribe(type);
            type.setCuckCatcher(this.getCuckCatcher());
            this.types.add(type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return type;
    }
}
