package party.cuckcatcher.impl;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import party.cuckcatcher.api.listener.Listener;
import party.cuckcatcher.api.manager.Manager;
import party.cuckcatcher.impl.event.EventManager;
import party.cuckcatcher.impl.property.PlayerPropertyManager;
import party.cuckcatcher.impl.type.TypeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Made by SkidRevenant at 03/03/2018
 */
public class CuckCatcher extends JavaPlugin {

    @Getter
    private final EventManager bus = new EventManager();

    @Getter
    private final List<Manager> managers = new ArrayList<>();

    private final List<Listener> listeners = new ArrayList<>();


    public void onEnable() {
        this.addClassesToList("party.cuckcatcher.impl", Manager.class, this.managers);
        this.addClassesToList("party.cuckcatcher.impl.listeners", Listener.class, this.listeners);

        this.managers.forEach(Manager::enable);

        this.listeners.forEach(Listener::enable);
    }

    public void onDisable() {
        this.managers.forEach(Manager::disable);

        this.listeners.forEach(Listener::disable);

        this.managers.clear();
        this.listeners.clear();
    }

    public PlayerPropertyManager getPlayerPropertyManager() {
        return (PlayerPropertyManager) this.managers.stream().filter(manager -> manager.getClass() == PlayerPropertyManager.class).findFirst().orElse(null);
    }

    public TypeManager getTypeManager() {
        return (TypeManager) this.managers.stream().filter(manager -> manager.getClass() == TypeManager.class).findFirst().orElse(null);
    }

    private <T> void addClassesToList(String directory, Class<T> superClass, List<T> list) {
        new Reflections(directory).getSubTypesOf(superClass)
                .forEach(clazz -> {
                    try {
                        List.class.getDeclaredMethod("add", Object.class).invoke(list, clazz.getConstructor(CuckCatcher.class).newInstance(this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
