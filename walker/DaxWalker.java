package com.dax.walker;

import com.dax.walker.engine.WalkerEngine;
import com.dax.walker.engine.definitions.Teleport;
import com.dax.walker.models.*;
import com.dax.walker.models.exceptions.AuthorizationException;
import com.dax.walker.models.exceptions.RateLimitException;
import com.dax.walker.models.exceptions.UnknownException;
import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DaxWalker {

    private Server server;
    private WalkerEngine walkerEngine;

    public DaxWalker(Server server) {
        this.server = server;
        this.walkerEngine = new WalkerEngine();
    }

    public WalkState walkTo(Positionable positionable) {
        List<PathRequestPair> pathRequestPairs = new ArrayList<>();
        pathRequestPairs.add(new PathRequestPair(Point3D.from(localPosition()), Point3D.from(positionable.getPosition())));

        addTeleports(pathRequestPairs, positionable.getPosition());

        BulkPathRequest request = new BulkPathRequest(PlayerDetails.generate(), pathRequestPairs);
        try {
            return walkerEngine.walk(server.getPaths(request)) ? WalkState.SUCCESS : WalkState.FAILED;
        } catch (RateLimitException e) {
            return WalkState.RATE_LIMIT;
        } catch (AuthorizationException | UnknownException e) {
            return WalkState.ERROR;
        }
    }

    public WalkState walkToBank() {
        return walkToBank(null);
    }

    public WalkState walkToBank(RSBank bank) {
        if (bank != null) return walkTo(bank.getPosition());

        List<BankPathRequestPair> pathRequestPairs = new ArrayList<>();
        pathRequestPairs.add(new BankPathRequestPair(Point3D.from(localPosition()), null));

        addTeleports(pathRequestPairs);

        BulkBankPathRequest request = new BulkBankPathRequest(PlayerDetails.generate(), pathRequestPairs);
        try {
            return walkerEngine.walk(server.getBankPaths(request)) ? WalkState.SUCCESS : WalkState.FAILED;
        } catch (RateLimitException e) {
            return WalkState.RATE_LIMIT;
        } catch (AuthorizationException | UnknownException e) {
            return WalkState.ERROR;
        }
    }

    private Position localPosition() {
        return Players.getLocal().getPosition();
    }

    private void addTeleports(List<BankPathRequestPair> list) {
        list.addAll(Teleport.getValidStartingPositions().stream()
                .map(position -> new BankPathRequestPair(Point3D.from(position), null))
                .collect(Collectors.toList())
        );
    }

    private void addTeleports(List<PathRequestPair> list, Position start) {
        list.addAll(Teleport.getValidStartingPositions().stream()
                .map(position -> new PathRequestPair(Point3D.from(position), Point3D.from(start)))
                .collect(Collectors.toList())
        );
    }

}
