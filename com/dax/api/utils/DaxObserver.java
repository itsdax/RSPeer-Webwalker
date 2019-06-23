package com.dax.api.utils;

import org.rspeer.ui.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public  abstract class DaxObserver<E extends DaxEvent, T extends DaxListener<E>> {

    private boolean end;
    private Thread thread;

    private List<T> listeners;
    private Lock listenerLock;

    public DaxObserver() {
        this.listeners = new ArrayList<>();
        this.listenerLock = new ReentrantLock();
        this.thread = new Thread(() -> {
            try {
                while (!end) Thread.sleep(observe());
            } catch (InterruptedException e) {
                end = true;
            } catch (Exception e) {
                Log.severe(e);
            }
        });
        this.thread.start();
    }

    public abstract int observe() throws InterruptedException;

    public void stop(boolean immediately) {
        this.end = true;
        if (immediately) this.thread.interrupt();
    }

    // V extends T solely to explicitly tell compiler.
    // When compiler is smarter, it should not throw any warnings, and we can simply use T instead of declaring V
    public <V extends T> void addListener(V t) {
        listenerLock.lock();
        this.listeners.add(t);
        listenerLock.unlock();
    }

    public List<T> getListeners() {
        return listeners;
    }

    public void notify(E event) {
        listenerLock.lock();
        for (T listener : listeners) {
            listener.trigger(event);
        }
    }

}
