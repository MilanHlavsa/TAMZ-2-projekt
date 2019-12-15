package com.example.hla0191_tamz2;

public enum MapImages {

    WALL(0), UP(1), LEFT(2), DOWN(3), RIGHT(4), EMPTY(5), HERO(6), GOBLIN(7), COIN(8), ARROW(9), PRINCESS(10);

    private final int value;

    MapImages(int value) {
        this.value = value;
    }
    public int get() {
        return value;
    }
}
