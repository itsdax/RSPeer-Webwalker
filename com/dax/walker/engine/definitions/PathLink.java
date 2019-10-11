package com.dax.walker.engine.definitions;

import com.dax.walker.engine.BrokenPathHandler;
import com.dax.walker.engine.EntityHandler;
import com.dax.walker.engine.utils.LockPickHandler;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.movement.transportation.CharterShip;
import org.rspeer.ui.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class PathLink {

    private static List<PathLink> values;

    public static final PathLink KARAMJA_PORT_SARIM =
            new PathLink(new Position(2953, 3146, 0), new Position(3029, 3217, 0),
                    (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition));

    public static final PathLink KARAMJA_PORT_PHASMATYS = new PathLink(new Position(2953, 3146, 0), new Position(3702, 3503, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_PHASMATYS, walkCondition));

    public static final PathLink PORT_PHASMATYS_KARAMJA = new PathLink(new Position(3702, 3503, 0), new Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.MUSA_POINT, walkCondition)
    );

    public static final PathLink PORT_PHASMATYS_PORTSARIM = new PathLink(new Position(3702, 3503, 0), new
            Position(2796, 3414, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_PHASMATYS, walkCondition)
    );

    public static final PathLink PORT_SARIM_PORT_PHASMATYS = new PathLink(new Position(2796, 3414, 0), new
            Position(3702, 3503, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_PHASMATYS, walkCondition)
    );

    public static final PathLink PORT_SARIM_CATHERBY = new PathLink(new Position(3041, 3193, 0), new
            Position(2796, 3414, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.CATHERBY, walkCondition)
    );

    public static final PathLink PORT_SARIM_BRIMHAVEN = new PathLink(new Position(3041, 3193, 0), new
            Position(2760, 3237, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.BRIMHAVEN, walkCondition)
    );

    public static final PathLink PORT_SARIM_KARAMJA = new PathLink(new Position(3029, 3217, 0), new
            Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition)
    );

    public static final PathLink CATHERBY_PORT_SARIM = new PathLink(new Position(2796, 3414, 0), new
            Position(3041, 3193, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_SARIM, walkCondition)
    );

    public static final PathLink CATHERBY_PORT_BRIMHAVEN = new PathLink(new Position(2796, 3414, 0), new
            Position(2760, 3237, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.BRIMHAVEN, walkCondition)
    );

    public static final PathLink CATHERBY_MUSA_POINT = new PathLink(new Position(2796, 3414, 0), new
            Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.MUSA_POINT, walkCondition)
    );

    public static final PathLink CATHERBY_PORT_KHAZARD = new PathLink(new Position(2796, 3414, 0), new
            Position(2673, 3148, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_KHAZARD, walkCondition)
    );

    public static PathLink BRIMHAVEN_ARDOUGHNE = new PathLink(new Position(2772, 3225, 0), new
            Position(2681, 3275, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition)
    );

    public static final PathLink ARDOUGHNE_BRIMHAVEN = new PathLink(new Position(2681, 3275, 0), new
            Position(2772, 3225, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)pay.fare"), start, end, walkCondition)
    );

    public static final PathLink BRIMHAVEN_PORT_SARIM = new PathLink(new Position(2760, 3237, 0), new
            Position(2953, 3146, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_SARIM, walkCondition)
    );

    public static final PathLink KHAZARD_CATHERBY = new PathLink(new Position(2673, 3148, 0), new
            Position(2796, 3414, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.CATHERBY, walkCondition)
    );

    public static final PathLink KHAZARD_PORT_SARIM = new PathLink(
            new Position(2673, 3148, 0), new
            Position(3041, 3193, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_SARIM, walkCondition)
    );

    public static final PathLink PORT_SARIM_KHAZARD = new PathLink(new Position(3041, 3193, 0), new
            Position(2673, 3148, 0),
            (start, end, walkCondition) -> EntityHandler.handleCharter(CharterShip.Destination.PORT_KHAZARD, walkCondition)
    );

    public static final PathLink PORT_SARIM_PEST_CONTROL = new PathLink(new Position(3041, 3202, 0), new
            Position(2659, 2676, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)travel"), start, end, walkCondition)
    );

    public static final PathLink PEST_CONTROL_PORT_SARIM = new PathLink(new Position(2659, 2676, 0), new

            Position(3041, 3202, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)travel"), start, end, walkCondition)
    );

    public static final PathLink PORT_SARIM_PISCARILIUS = new PathLink(new Position(3054, 3245, 0), new Position(1824, 3691, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port pis.+"), start, end, walkCondition));

    public static final PathLink PORT_SARIM_LANDS_END = new PathLink(new Position(3054, 3245, 0), new
            Position(1504, 3399, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)land.s end"), start, end, walkCondition)
    );

    public static final PathLink LANDS_END_PISCARILIUS = new PathLink(new Position(1504, 3399, 0), new
            Position(1824, 3691, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port pis.+"), start, end, walkCondition)
    );

    public static final PathLink LANDS_END_PORT_SARIM = new PathLink(new Position(1504, 3399, 0), new
            Position(3054, 3245, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port sarim"), start, end, walkCondition)
    );

    public static final PathLink PISCARILIUS_LANDS_END = new PathLink(new Position(1824, 3691, 0), new
            Position(1504, 3399, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)land.s end"), start, end, walkCondition)
    );

    public static final PathLink PISCARILIUS_PORT_SARIM = new PathLink(
            new Position(1824, 3691, 0), new
            Position(3054, 3245, 0),
            (start, end, walkCondition) -> EntityHandler.handleWithAction(Pattern.compile("(?i)port sarim"), start, end, walkCondition)
    );

    public static final PathLink LUMBRIDGE_HAM_HIDEOUT = new PathLink(new Position(3166, 3251, 0), new
            Position(3149, 9652, 0),
            LockPickHandler::handle);

    public static final PathLink HAM_JAIL = new PathLink(new Position(3183, 9611, 0), new
            Position(3182, 9611, 0),
            LockPickHandler::handle);

    public static final PathLink BURTHORP_DOWNSTAIRS = new PathLink(new Position(2899, 3565, 0), new
            Position(2205, 4934, 1),
            (start, end, walkCondition) -> BrokenPathHandler.NextMove.FLOOR_UNDER.handle() ? PathHandleState.SUCCESS : PathHandleState.FAILED);

    public static final PathLink BURTHORP_UPSTAIRS = new PathLink(new Position(2205, 4934, 1), new
            Position(2899, 3565, 0),
            (start, end, walkCondition) -> BrokenPathHandler.NextMove.FLOOR_ABOVE.handle() ? PathHandleState.SUCCESS : PathHandleState.FAILED);

    public static final PathLink PORT_PHASMATYS_SOUTH = new PathLink(new Position(3659, 3507, 0), new
            Position(3659, 3509, 0),
            (start, end, walkCondition) -> BrokenPathHandler.NextMove.SAME_FLOOR.handle() ? PathHandleState.SUCCESS : PathHandleState.FAILED);


    public static final PathLink DEATH_PLATEAU_DUNGEON = new PathLink(new Position(2858, 3577, 0), new
            Position(2269, 4752, 0),
            (start, end, walkCondition) -> BrokenPathHandler.NextMove.SAME_FLOOR.handle() ? PathHandleState.SUCCESS : PathHandleState.FAILED);


    static {
        values = new ArrayList<>();
        values.add(KARAMJA_PORT_SARIM);
        values.add(KARAMJA_PORT_PHASMATYS);
        values.add(PORT_PHASMATYS_KARAMJA);
        values.add(PORT_PHASMATYS_PORTSARIM);
        values.add(PORT_SARIM_PORT_PHASMATYS);
        values.add(PORT_SARIM_CATHERBY);
        values.add(PORT_SARIM_BRIMHAVEN);
        values.add(PORT_SARIM_KARAMJA);
        values.add(CATHERBY_PORT_SARIM);
        values.add(CATHERBY_PORT_BRIMHAVEN);
        values.add(CATHERBY_MUSA_POINT);
        values.add(CATHERBY_PORT_KHAZARD);
        values.add(BRIMHAVEN_ARDOUGHNE);
        values.add(ARDOUGHNE_BRIMHAVEN);
        values.add(BRIMHAVEN_PORT_SARIM);
        values.add(KHAZARD_CATHERBY);
        values.add(KHAZARD_PORT_SARIM);
        values.add(PORT_SARIM_KHAZARD);
        values.add(PORT_SARIM_PEST_CONTROL);
        values.add(PEST_CONTROL_PORT_SARIM);
        values.add(PORT_SARIM_PISCARILIUS);
        values.add(PORT_SARIM_LANDS_END);
        values.add(LANDS_END_PISCARILIUS);
        values.add(LANDS_END_PORT_SARIM);
        values.add(PISCARILIUS_LANDS_END);
        values.add(PISCARILIUS_PORT_SARIM);
        values.add(LUMBRIDGE_HAM_HIDEOUT);
        values.add(HAM_JAIL);
        values.add(BURTHORP_DOWNSTAIRS);
        values.add(BURTHORP_UPSTAIRS);
        values.add(PORT_PHASMATYS_SOUTH);
        values.add(DEATH_PLATEAU_DUNGEON);
    }

    public static List<PathLink> getValues() {
        return values;
    }

    private Position a;
    private Position b;
    private PathLinkHandler pathLinkHandler;

    public PathLink(Position start, Position end, PathLinkHandler pathLinkHandler) {
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
    
