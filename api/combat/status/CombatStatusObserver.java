package com.dax.api.combat.status;

import com.dax.api.collections.FastRemovalQueue;
import com.dax.api.utils.DaxObserver;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.PathingEntity;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.Projectile;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.Projectiles;
import org.rspeer.ui.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CombatStatusObserver extends DaxObserver<AttackEvent, CombatStatusListener> {

    private FastRemovalQueue<Integer> queue;
    private Map<Integer, AttackEvent> map;
    private Lock lock;

    public CombatStatusObserver() {
        this.queue = new FastRemovalQueue<>();
        this.map = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public int observe() {
        checkEvents();
        cleanup();
        if (map.size() > 0) Log.info(map.toString());
        return 150;
    }

    public boolean isInCombat(PathingEntity entity) {
        return isInCombat(entity.getIndex());
    }

    public boolean isInCombat(int index) {
        AttackEvent attackEvent = map.get(index);
        return attackEvent != null && !attackEvent.isExpired();
    }

    private void checkEvents() {
        lock.lock();

        for (Npc npc : Npcs.getLoaded()) {
            if (npc.getAnimation() == -1 || npc.getTargetIndex() == -1) continue;
            if (npc.getTarget() == null) continue;
            addEvent(npc.getIndex(), npc.getTarget().getIndex());
        }

        for (Player player : Players.getLoaded()) {
            if (player.getAnimation() == -1 || player.getTargetIndex() == -1) continue;
            addEvent(player.getIndex(), player.getTargetIndex());
        }

        for (Projectile projectile : Projectiles.getLoaded()) {
            addEvent(-1, projectile.getTargetIndex());
        }

        lock.unlock();
    }

    /**
     * This method assumes each attack input is in sequential order
     *
     * @param from index attack source is coming from
     * @param to   index attack source is going to
     */
    private void addEvent(int from, int to) {
        AttackEvent attackEvent = new AttackEvent(from, to);
        lock.lock();
        map.put(to, attackEvent);
        queue.add(to);
        lock.unlock();
    }

    private void cleanup() {
        this.lock.lock();
        while (!queue.isEmpty()) {
            AttackEvent attackEvent = map.get(queue.peek());
            if (!attackEvent.isExpired()) break; // The rest are not expired since they're sequential. Lets exit.
            map.remove(queue.poll());
        }
        this.lock.unlock();
    }

}
