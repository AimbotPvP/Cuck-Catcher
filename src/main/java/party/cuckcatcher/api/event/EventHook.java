package party.cuckcatcher.api.event;

/**
 * Made by SkidRevenant at 2018-02-28
 */
@FunctionalInterface
public interface EventHook<T> {

    /**
     * Invokes this Link with the event
     *
     * @param event The Event being Invoked
     */
    void invoke(T event);
}