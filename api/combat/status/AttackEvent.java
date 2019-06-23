package com.dax.api.combat.status;

import com.dax.api.time.Expirable;
import com.dax.api.utils.DaxEvent;

public class AttackEvent extends DaxEvent implements Expirable {

    private static final long EXPIRATION = 4500;

    private int from;
    private int to;
    private long time;

    public AttackEvent(int from, int to) {
        this.from = from;
        this.to = to;
        this.time = System.currentTimeMillis();
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() - time >= EXPIRATION;
    }

    @Override
    public int hashCode() {
        int result = getFrom();
        result = 31 * result + getTo();
        result = 31 * result + (int) (getTime() ^ (getTime() >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AttackEvent)) return false;
        AttackEvent that = (AttackEvent) o;

        if (this.from != that.from) return false;
        if (this.to != that.to) return false;

        // Consider the events equal if they have are within expiration constraints
        return Math.abs(this.time - that.time) < EXPIRATION;
    }

    @Override
    public String toString() {
        return String.format("%d->%d", from, to);
    }

}
