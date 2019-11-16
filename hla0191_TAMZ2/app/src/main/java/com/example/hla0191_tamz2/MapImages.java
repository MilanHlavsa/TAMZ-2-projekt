package com.example.hla0191_tamz2;

public enum MapImages {
    /*bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
    bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
    bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
    bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.goblin);
    bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
    bmp[5] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
    bmp[6] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_up);
    bmp[7] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_left);
    bmp[8] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_down);
    bmp[9] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_right);*/

    WALL(0), UP(1), LEFT(2), DOWN(3), RIGHT(4), EMPTY(5), HERO(6), GOBLIN(7), COIN(8), ARROW(9);

    private final int value;

    MapImages(int value) {
        this.value = value;
    }
    public int get() {
        return value;
    }
}
