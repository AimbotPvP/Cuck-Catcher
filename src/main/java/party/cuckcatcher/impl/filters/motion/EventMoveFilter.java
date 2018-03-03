package party.cuckcatcher.impl.filters.motion;

import lombok.RequiredArgsConstructor;
import party.cuckcatcher.impl.event.events.motion.EventMove;

import java.util.function.Predicate;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@RequiredArgsConstructor
public class EventMoveFilter implements Predicate<EventMove> {

    private final boolean vertical;

    @Override
    public boolean test(EventMove move) {

        double verticalDistance = move.getVerticalDistance(),
        horizontalDistance = move.getHorizontalDistance();

        return vertical ? verticalDistance > 0 : horizontalDistance > 0;
    }
}
