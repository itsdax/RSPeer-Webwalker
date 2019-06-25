package com.dax.walker.models;


import com.allatori.annotations.DoNotRename;

@DoNotRename
public class BankPathRequestPair {
    @DoNotRename
    private Point3D start;
    @DoNotRename
    private RSBank bank;

    public BankPathRequestPair(Point3D start, RSBank bank) {
        this.start = start;
        this.bank = bank;
    }

    public Point3D getStart() {
        return start;
    }

    public RSBank getBank() {
        return bank;
    }
}
