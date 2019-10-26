package com.dax.walker;

import com.allatori.annotations.DoNotRename;
import com.dax.walker.engine.WalkerEngine;
import com.dax.walker.engine.definitions.Teleport;
import com.dax.walker.engine.definitions.WalkCondition;
import com.dax.walker.models.*;
import com.dax.walker.models.exceptions.AuthorizationException;
import com.dax.walker.models.exceptions.RateLimitException;
import com.dax.walker.models.exceptions.UnknownException;
import com.dax.walker.store.DaxStore;
import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.ui.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@DoNotRename
public class DaxWalker {

    private Server server;
    private WalkerEngine walkerEngine;
    private DaxStore store;
    private boolean useTeleports;

    public DaxWalker(Server server) {
        this.server = server;
        this.store = new DaxStore();
        this.walkerEngine = new WalkerEngine(null, this);
        this.useTeleports = true;
    }

    public DaxStore getStore() {
        return store;
    }

    /**
     * This condition will override the default walk condition that enables run for you.
     *
     * @param walkCondition
     */
    public void setGlobalCondition(WalkCondition walkCondition) {
        walkerEngine.setWalkCondition(walkCondition);
    }

    public WalkState walkTo(Positionable positionable) {
        return walkTo(positionable, null);
    }

    public boolean isUseTeleports() {
        return useTeleports;
    }

    public void setUseTeleports(boolean useTeleports) {
        this.useTeleports = useTeleports;
    }

    /**
     *
     * @param positionable
     * @param walkCondition Will trigger WITH with the global condition.
     * @return
     */
    public WalkState walkTo(Positionable positionable, WalkCondition walkCondition) {
        Log.info("DaxWalker", String.format("Navigating to (%d,%d,%d)", positionable.getX(), positionable.getY(), positionable.getFloorLevel()));
        List<PathRequestPair> pathRequestPairs = useTeleports ? getPathTeleports(positionable.getPosition()) : new ArrayList<>();
        pathRequestPairs.add(new PathRequestPair(Point3D.from(localPosition()), Point3D.from(positionable.getPosition())));

        BulkPathRequest request = new BulkPathRequest(PlayerDetails.generate(), pathRequestPairs);
        try {
            return walkerEngine.walk(server.getPaths(request), walkCondition) ? WalkState.SUCCESS : WalkState.FAILED;
        } catch (RateLimitException e) {
            return WalkState.RATE_LIMIT;
        } catch (AuthorizationException | UnknownException e) {
            return WalkState.ERROR;
        }
    }

    public WalkState walkToBank() {
        return walkToBank(null, null);
    }

    public WalkState walkToBank(WalkCondition walkCondition) {
        return walkToBank(null, walkCondition);
    }

    public WalkState walkToBank(RSBank bank) {
        return walkToBank(bank, null);
    }

    /**
     *
     * @param bank
     * @param walkCondition Will trigger WITH the global condition.
     * @return
     */
    public WalkState walkToBank(RSBank bank, WalkCondition walkCondition) {
        if (bank != null) return walkTo(bank.getPosition());

        List<BankPathRequestPair> pathRequestPairs =  useTeleports ? getBankPathTeleports() : new ArrayList<>();
        pathRequestPairs.add(new BankPathRequestPair(Point3D.from(localPosition()), null));

        BulkBankPathRequest request = new BulkBankPathRequest(PlayerDetails.generate(), pathRequestPairs);
        try {
            return walkerEngine.walk(server.getBankPaths(request), walkCondition) ? WalkState.SUCCESS : WalkState.FAILED;
        } catch (RateLimitException e) {
            return WalkState.RATE_LIMIT;
        } catch (AuthorizationException | UnknownException e) {
            return WalkState.ERROR;
        }
    }

    private Position localPosition() {
        return Players.getLocal().getPosition();
    }

    private List<BankPathRequestPair> getBankPathTeleports() {
        return Teleport.getValidStartingPositions().stream()
                .map(position -> new BankPathRequestPair(Point3D.from(position), null))
                .collect(Collectors.toList());

    }

    private List<PathRequestPair> getPathTeleports(Position start) {
        return Teleport.getValidStartingPositions().stream()
                .map(position -> new PathRequestPair(Point3D.from(position), Point3D.from(start)))
                .collect(Collectors.toList());
    }

}
