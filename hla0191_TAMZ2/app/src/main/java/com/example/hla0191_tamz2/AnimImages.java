package com.example.hla0191_tamz2;

public enum AnimImages {

    EX1(0), EX2(1), EX3(2), EX4(3), EX5(4), EX6(5), EX7(6), EX8(7), ANEMPTY(8);
    private final int value;

    AnimImages(int value) {
        this.value = value;
    }
    public int get() {
        return value;
    }
}
