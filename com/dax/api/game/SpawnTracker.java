package com.dax.api.game;

import com.dax.api.paint.debug.PositionalDebug;
import com.dax.api.utils.DaxPosition;
import com.dax.api.walking.WalkQueue;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.event.listeners.NpcSpawnListener;
import org.rspeer.runetek.event.types.NpcSpawnEvent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpawnTracker implements NpcSpawnListener {

    private Map<Integer, Position> map;
    private Lock lock;
    private Predicate<Npc> filter;
    private ExecutorService executorService;

    public SpawnTracker(Predicate<Npc> filter) {
        Game.getEventDispatcher().register(this);
        this.map = new HashMap<>();
        this.lock = new ReentrantLock();
        this.filter = filter;
        executorService = Executors.newFixedThreadPool(3);
    }

    public Npc getFurthestFromSpawn(Position position, int distance) {
        Position truePosition = WalkQueue.getTruePosition();
        return getAll(position, distance).stream().max((o1, o2) -> {
            Position a1 = WalkQueue.getTruePosition(o1);
            Position a2 = WalkQueue.getTruePosition(o2);
            return Double.compare(a1.distance(truePosition), a2.distance(truePosition));
        }).orElse(null);
    }

    public List<Npc> getAll(Position position, int distance) {
        Set<DaxPosition> surroundingArea = Area.surrounding(position, distance).getTiles().stream().map(DaxPosition::new).collect(Collectors.toSet());
        return Arrays.stream(Npcs.getLoaded(filter)).filter(npc -> {
            Position spawn = getSpawn(npc.getIndex());
            return spawn != null && surroundingArea.contains(new DaxPosition(spawn));
        }).collect(Collectors.toList());
    }

    private Position getSpawn(int index) {
        lock.lock();
        Position position = map.get(index);
        lock.unlock();
        return position;
    }

    public void end() {
        Game.getEventDispatcher().deregister(this);
    }

    public void paintDebug(Graphics2D graphics2D) {
        for (Npc npc : Npcs.getLoaded(filter)) {
            Position position;
            lock.lock();
            position = map.get(npc.getIndex());
            lock.unlock();

            if (position == null) continue;

            PositionalDebug.drawArrow(graphics2D, position, WalkQueue.getTruePosition(npc), new Color(255, 255, 255, 180));
        }

    }

    @Override
    public void notify(NpcSpawnEvent npcSpawnEvent) {
        Npc npc = npcSpawnEvent.getSource();

        if (!filter.test(npc)) return;

        executorService.submit(() -> {
            Time.sleep(500);
            lock.lock();
            map.put(npc.getIndex(), npcSpawnEvent.getSource().getPosition());
            lock.unlock();
        });
    }


}
