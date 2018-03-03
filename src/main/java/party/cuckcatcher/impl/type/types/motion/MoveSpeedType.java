package party.cuckcatcher.impl.type.types.motion;

import party.cuckcatcher.api.type.TypeManifest;
import party.cuckcatcher.impl.event.Link;
import party.cuckcatcher.impl.event.events.motion.EventMove;
import party.cuckcatcher.impl.filters.motion.EventMoveFilter;

/**
 * Made by SkidRevenant at 03/03/2018
 */
@TypeManifest(label = "MoveSpeed")
public class MoveSpeedType {

    private Link<EventMove> onMove = new Link<>(event -> {



    }, new EventMoveFilter(false));
}
