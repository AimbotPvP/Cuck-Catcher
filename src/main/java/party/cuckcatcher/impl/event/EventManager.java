package party.cuckcatcher.impl.event;

import party.cuckcatcher.api.event.EventBus;
import party.cuckcatcher.api.event.EventListener;
import party.cuckcatcher.api.event.EventPriority;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Made by SkidRevenant at 2018-02-28
 */

public class EventManager implements EventBus {

    private final Map<Object, List<Link>> SUBSCRIPTION_CACHE = new ConcurrentHashMap<>();

    private final Map<Class<?>, List<Link>> SUBSCRIPTION_MAP = new ConcurrentHashMap<>();

    @Override
    public void subscribe(Object object) {
        List<Link> links = SUBSCRIPTION_CACHE.computeIfAbsent(object, o ->
                Arrays.stream(o.getClass().getDeclaredFields())
                        .filter(EventManager::isValidField)
                        .map(field -> asListener(o, field))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));

        links.forEach(this::subscribe);
    }

    @Override
    public void unsubscribe(Object object) {
        List<Link> objectLinks = SUBSCRIPTION_CACHE.get(object);
        if (objectLinks == null) return;

        SUBSCRIPTION_MAP.values().forEach(listeners -> listeners.removeIf(objectLinks::contains));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void post(Object event) {
        List<Link> links = SUBSCRIPTION_MAP.get(event.getClass());

        if (links != null) links.forEach(link -> link.invoke(event));
    }

    private static boolean isValidField(Field field) {
        return field.isAnnotationPresent(EventListener.class) && Link.class.isAssignableFrom(field.getType());
    }

    private static Link asListener(Object object, Field field) {
        try {
            boolean accessible = field.isAccessible();

            field.setAccessible(true);
            Link link = (Link) field.get(object);
            field.setAccessible(accessible);

            if (link == null) return null;

            if (link.getPriority() > EventPriority.LOWEST || link.getPriority() < EventPriority.HIGHEST)
                throw new RuntimeException("Event Priority out of bounds! %s");

            return link;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private void subscribe(Link link) {
        List<Link> links = SUBSCRIPTION_MAP.computeIfAbsent(link.getTarget(), target -> new CopyOnWriteArrayList<>());

        int index = 0;
        for (; index < links.size(); index++) {
            if (link.getPriority() < links.get(index).getPriority()) {
                break;
            }
        }

        links.add(index, link);

    }
}
