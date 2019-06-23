package com.dax.api.walking;

import org.rspeer.runetek.adapter.scene.PathingEntity;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.Scene;

public class WalkQueue {


    public static boolean isWalkingTowards(Position tile){
        Position tile1 = getWalkingTowards();
        return tile1 != null && tile1.equals(tile);
    }

    public static Position getTruePosition(PathingEntity player){
        Position position = getWalkingTowards(player);
        return position != null ? position : player.getPosition();
    }

    public static Position getTruePosition(){
        return getTruePosition(Players.getLocal());
    }

    public static Position getWalkingTowards(){
        return getWalkingTowards(Players.getLocal());
    }

    public static Position getWalkingTowards(PathingEntity rsCharacter){
        int[] xIndex = rsCharacter.getPathXQueue(), yIndex = rsCharacter.getPathYQueue();
        if (xIndex == null || yIndex == null) return null;
        if (xIndex.length <= 0 || yIndex.length <= 0) return null;
        return Scene.getBase().translate(xIndex[0], yIndex[0]);
    }

}
