package com.example.hla0191_tamz2;

import static com.example.hla0191_tamz2.MapImages.GOBLIN;

public class MapGenerator {

    private int lastDoorPosition;
    Fight fight = new Fight();

    public MapGenerator(int lastDoorPosition) {
        this.lastDoorPosition = lastDoorPosition;
    }

    private int getHeroPosition(int door, int move, int[] level){
        int i = 0;
        for (int x: level) {
            if(x == door) return i + move;
            i++;
        }
        return -1;
    }

    private boolean isFight(int[] level){
        int i = 0;
        for (int x: level) {
            if(x == GOBLIN.get()) return true;
            i++;
        }
        return false;
    }


    public int[] getMap() {
        int level[] = {
                1,1,1,1,6,1,1,1,1,
                1,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,1,
                1,0,0,0,3,0,0,0,1,
                9,0,0,0,0,0,0,0,7,
                1,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,3,1,
                1,1,1,1,8,1,1,1,1,
        };

        if (lastDoorPosition == 6) level[getHeroPosition(8,-9, level)] = 2;
        else if (lastDoorPosition == 7) level[getHeroPosition(9,1, level)] = 2;
        else if (lastDoorPosition == 8) level[getHeroPosition(6,9, level)] = 2;
        else level[getHeroPosition(7,-1, level)] = 2;

        if(isFight(level)) {
            fight.fight = true;
            fight.setEnemyPositions(level);
        }

        return level;
    }
}
