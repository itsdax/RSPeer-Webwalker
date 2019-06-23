package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public class IntPair {


    @DoNotRename
    private int key;

    @DoNotRename
    private int value;

    public IntPair() {
    }

    public IntPair(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public int getValue() {
        return this.value;
    }

}
