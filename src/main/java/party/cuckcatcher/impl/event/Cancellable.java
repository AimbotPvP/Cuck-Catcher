package party.cuckcatcher.impl.event;

/**
 * Made by SkidRevenant at 2018-02-28
 */
public class Cancellable {

    /**
     * Cancelled state
     */
    private boolean cancelled;

    /**
     * Cancels the event, this is handled
     * wherever the event is injected to
     * prevent a task from occuring
     */
    public final void cancel() {
        this.cancelled = true;
    }

    /**
     * @return Whether or not the event is cancelled
     */
    public final boolean isCancelled() {
        return this.cancelled;
    }
}