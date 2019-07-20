package com.dax.walker.engine.definitions;

import com.dax.walker.engine.BrokenPathHandler;
import com.dax.walker.engine.EntityHandler;
import com.dax.walker.engine.utils.LockPickHandler;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.movement.transportation.CharterShip;
import org.rspeer.ui.Log;

import java.util.logging.Level;
import java.util.regex.Pattern;

public enum PathLink {
    
    KARAMJA_PORT_SARIM(
            new Position(2953, 3146, 0), new Position(3029, 3217, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition)
    ),
    
    KARAMJA_PORT_PHASMATYS(
            new Position(2953, 3146, 0),  new Position(3702, 3503, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_PHASMATYS, walkCondition)
    ),
    PORT_PHASMATYS_KARAMJA(
            new Position(3702, 3503, 0), new Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.MUSA_POINT, walkCondition)
    ),
    
    PORT_PHASMATYS_PORTSARIM(
            new Position(3702, 3503, 0), new Position(2796, 3414, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_PHASMATYS, walkCondition)
    ),
    
    PORT_SARIM_PORT_PHASMATYS(
            new Position(2796, 3414, 0), new Position(3702, 3503, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_PHASMATYS, walkCondition)
    ),
    
    PORT_SARIM_CATHERBY(
            new Position(3041, 3193, 0), new Position(2796, 3414, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.CATHERBY, walkCondition)
    ),
    
    PORT_SARIM_BRIMHAVEN(
            new Position(3041, 3193, 0), new Position(2760, 3237, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.BRIMHAVEN, walkCondition)
    ),
    
    PORT_SARIM_KARAMJA(
            new Position(3029, 3217, 0), new Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition)
    ),
    
    CATHERBY_PORT_SARIM(
            new Position(2796, 3414, 0), new Position(3041, 3193, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_SARIM, walkCondition)
    ),
    
    CATHERBY_PORT_BRIMHAVEN(
            new Position(2796, 3414, 0), new Position(2760, 3237, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.BRIMHAVEN, walkCondition)
    ),
    
    CATHERBY_MUSA_POINT(
            new Position(2796, 3414, 0), new Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.MUSA_POINT, walkCondition)
    ),
    
    CATHERBY_PORT_KHAZARD(
            new Position(2796, 3414, 0), new Position(2673, 3148, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_KHAZARD, walkCondition)
    ),
    
    BRIMHAVEN_ARDOUGHNE(
            new Position(2772, 3225, 0), new Position(2681, 3275, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition)
    ),
    
    ARDOUGHNE_BRIMHAVEN(
            new Position(2681, 3275, 0), new Position(2772, 3225, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition)
    ),
    
    BRIMHAVEN_PORT_SARIM (
            new Position(2760, 3237, 0), new Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_SARIM, walkCondition)
    ),
    
    KHAZARD_CATHERBY(
            new Position(2673, 3148, 0), new Position(2796, 3414, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.CATHERBY, walkCondition)
    ),
    
    KHAZARD_PORT_SARIM(
            new Position(2673, 3148, 0), new Position(3041, 3193, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_SARIM, walkCondition)
    ),
    
    PORT_SARIM_KHAZARD(
            new Position(3041, 3193, 0), new Position(2673, 3148, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_KHAZARD, walkCondition)
    ),
    
    PORT_SARIM_PEST_CONTROL(
            new Position(3041, 3202, 0), new Position(2659, 2676, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)travel"), start, end, walkCondition)
    ),
    
    PEST_CONTROL_PORT_SARIM(
            new Position(2659, 2676, 0), new Position(3041, 3202, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)travel"), start, end, walkCondition)
    ),
    
    PORT_SARIM_PISCARILIUS(
            new Position(3054, 3245, 0), new Position(1824, 3691, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port pis.+"), start, end, walkCondition)
    ),
    
    PORT_SARIM_LANDS_END(
            new Position(3054, 3245, 0), new Position(1504, 3399, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)land.s end"), start, end, walkCondition)
    ),
    
    LANDS_END_PISCARILIUS(
            new Position(1504, 3399, 0), new Position(1824, 3691, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port pis.+"), start, end, walkCondition)
    ),
    
    LANDS_END_PORT_SARIM(
            new Position(1504, 3399, 0), new Position(3054, 3245, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port sarim"), start, end, walkCondition)
    ),
    
    PISCARILIUS_LANDS_END(
            new Position(1824, 3691, 0), new Position(1504, 3399, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)land.s end"), start, end, walkCondition)
    ),
    
    PISCARILIUS_PORT_SARIM(
            new Position(1824, 3691, 0), new Position(3054, 3245, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port sarim"), start, end, walkCondition)
    ),
    
    LUMBRIDGE_HAM_HIDEOUT(
            new Position(3166, 3251, 0), new Position(3149, 9652, 0),
            LockPickHandler::handle
    ),
    
    HAM_JAIL(
            new Position(3183, 9611, 0), new Position(3182, 9611, 0),
            LockPickHandler::handle
    ),
    
    BURTHORP_DOWNSTAIRS (
            new Position(2899, 3565, 0), new Position(2205, 4934, 1),
            (start, end, walkCondition) -> BrokenPathHandler.NextMove.FLOOR_UNDER.handle() ? PathHandleState.SUCCESS : PathHandleState.FAILED
    ),
    
    BURTHORP_UPSTAIRS (
            new Position(2205, 4934, 1), new Position(2899, 3565, 0),
            (start, end, walkCondition) -> BrokenPathHandler.NextMove.FLOOR_ABOVE.handle() ? PathHandleState.SUCCESS : PathHandleState.FAILED
    ),
    
    PORT_PHASMATYS_SOUTH (
            new Position(3659, 3507 ,0), new Position(3659, 3509, 0),
            (start, end, walkCondition) -> BrokenPathHandler.NextMove.SAME_FLOOR.handle() ? PathHandleState.SUCCESS : PathHandleState.FAILED
    );
    
    private Position a;
    private Position b;
    private PathLinkHandler pathLinkHandler;
    
    PathLink(Position start, Position end, PathLinkHandler pathLinkHandler) {
        this.a = start;
        this.b = end;
        this.pathLinkHandler = pathLinkHandler;
    }
    
    public Position getStart() {
        return a;
    }
    
    public Position getEnd() {
        return b;
    }
    
    public PathHandleState handle(WalkCondition walkCondition) {
        Log.log(Level.FINE, "DaxWalker", "Triggering " + this);
        return this.pathLinkHandler.handle(a, b, walkCondition);
    }
    
}
