package party.cuckcatcher.api.event;

import party.cuckcatcher.impl.event.Link;

/**
 * Made by SkidRevenant at 2018-02-28
 */
public interface EventBus {

    /**
     * Discovers all valid listeners from the Object
     * specified and then registers them in the
     * form of {@code Listeners}
     *
     * @see Link
     *
     * @param object The object containing possible Event Listeners
     */
    void subscribe(Object object);

    /**
     * Unsubscribes an object from the listener map
     *
     * @see #subscribe(Object)
     *
     * @param object The object being unsubscribed
     */
    void unsubscribe(Object object);

    /**
     * Posts an event to all registered {@code Listeners}.
     * Done via Reflection Method Invokation
     *
     * @see Link#invoke(Object)
     *
     * @param event Event being called
     */
    void post(Object event);
}