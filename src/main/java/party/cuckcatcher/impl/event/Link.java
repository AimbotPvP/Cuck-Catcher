package party.cuckcatcher.impl.event;

import party.cuckcatcher.api.event.EventHook;
import party.cuckcatcher.api.event.EventPriority;
import party.cuckcatcher.impl.type.TypeResolver;

import java.util.function.Predicate;

/**
 * Made by SkidRevenant at 2018-02-28
 */
public final class Link<T> implements EventHook<T> {

    /**
     * The target event class.
     */
    private final Class<T> target;

    /**
     * The "body" of this {@code Link}
     */
    private final EventHook<T> hook;

    /**
     * Filters to be run on all of the events passed to this {@code Link}
     */
    private final Predicate<T>[] filters;

    /**
     * Priority of this {@code Link}
     *
     * @see EventPriority
     */
    private final byte priority;

    @SafeVarargs
    public Link(EventHook<T> hook, Predicate<T>... filters) {
        this(hook, EventPriority.DEFAULT, filters);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public Link(EventHook<T> hook, byte priority, Predicate<T>... filters) {
        this.hook = hook;
        this.priority = priority;
        this.target = (Class<T>) TypeResolver.resolveRawArgument(EventHook.class, hook.getClass());
        this.filters = filters;
    }

    /**
     * Returns the target event class, represented
     * by the {@code <T>} class parameter.
     *
     * @return The target event class
     */
    public final Class<T> getTarget() {
        return this.target;
    }

    /**
     * Returns the priority of this listener. The priority
     * is used to determine the order of listener invocations
     * for a given event. The return value is limited by the
     * {@code HIGHEST} and {@code LOWEST} values of {@code EventPriority}.
     *
     * @see EventPriority
     *
     * @return Priority of Link
     */
    public final byte getPriority() {
        return priority;
    }

    /**
     * Passes the event to this Link's body after
     * all of the filters have passed their checks.
     *
     * @see EventHook
     *
     * @param event Event being called
     */
    @Override
    public final void invoke(T event) {
        if (filters.length > 0)
            for (Predicate<T> filter : filters)
                if (!filter.test(event))
                    return;

        this.hook.invoke(event);
    }
}