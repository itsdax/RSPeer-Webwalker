package com.dax.walker.models;


import java.util.List;

public class BulkPathRequest {
    private PlayerDetails player;
    private List<PathRequestPair> requests;

    public BulkPathRequest(PlayerDetails player, List<PathRequestPair> requests) {
        this.player = player;
        this.requests = requests;
    }

    public PlayerDetails getPlayer() {
        return player;
    }

    public List<PathRequestPair> getRequests() {
        return requests;
    }
}
