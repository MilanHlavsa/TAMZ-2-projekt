package com.example.hla0191_tamz2;

import static com.example.hla0191_tamz2.Game.level;
import static com.example.hla0191_tamz2.Fight.fight;
import static com.example.hla0191_tamz2.Game.levelSize;
import static com.example.hla0191_tamz2.MapImages.DOWN;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;
import static com.example.hla0191_tamz2.Current_Activity.activity;
import static com.example.hla0191_tamz2.MapImages.HERO;
import static com.example.hla0191_tamz2.MapImages.LEFT;
import static com.example.hla0191_tamz2.MapImages.RIGHT;
import static com.example.hla0191_tamz2.MapImages.UP;

public class MapGenerator {

    private int lastDoorPosition;

    public MapGenerator(int lastDoorPosition) {
        this.lastDoorPosition = lastDoorPosition;
    }

    private int getHeroPosition(int door, int move){
        int i = 0;
        for (int x: level) {
            if(x == door) return i + move;
            i++;
        }
        return -1;
    }

    private boolean isFight(){
        int i = 0;
        for (int x: level) {
            if(x == GOBLIN.get()) return true;
            i++;
        }
        return false;
    }


    public void getMap() {
        level = new int[]{
                0,0,0,0,1,0,0,0,0,
                0,5,5,5,5,5,5,5,0,
                0,5,5,5,5,5,5,5,0,
                0,5,5,5,7,5,5,5,0,
                4,5,5,5,5,5,5,5,2,
                0,5,5,5,5,5,5,5,0,
                0,5,5,5,5,5,5,5,0,
                0,5,5,5,5,5,5,7,0,
                0,0,0,0,3,0,0,0,0,
        };

        if (lastDoorPosition == UP.get()) level[getHeroPosition(DOWN.get(),-levelSize)] = HERO.get();
        else if (lastDoorPosition == LEFT.get()) level[getHeroPosition(RIGHT.get(),1)] = HERO.get();
        else if (lastDoorPosition == DOWN.get()) level[getHeroPosition(UP.get(),levelSize)] = HERO.get();
        else if (lastDoorPosition == RIGHT.get())level[getHeroPosition(LEFT.get(),-1)] = HERO.get();

        if(isFight()) {
            fight = true;
            Fight f = new Fight();
            f.setEnemyPositions();
            activity.setBuyButtonsInvisible();
        }
    }
}
