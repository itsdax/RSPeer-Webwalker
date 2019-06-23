package com.dax.walker.engine.definitions;

import org.rspeer.runetek.api.movement.position.Position;

public interface PathLinkHandler {
    PathHandleState handle(Position start, Position end, WalkCondition walkCondition);
}
