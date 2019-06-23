package com.dax.walker.models;


public class BankPathRequestPair {
    private Point3D start;
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
