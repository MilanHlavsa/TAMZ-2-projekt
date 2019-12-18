package com.example.hla0191_tamz2;

import static com.example.hla0191_tamz2.Game.level;
import static com.example.hla0191_tamz2.Current_Activity.activity;
import static com.example.hla0191_tamz2.MapImages.ARROW;
import static com.example.hla0191_tamz2.MapImages.COIN;
import static com.example.hla0191_tamz2.MapImages.EMPTY;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;
import static com.example.hla0191_tamz2.MapImages.HERO;
import static com.example.hla0191_tamz2.MapImages.PRINCESS;
import static com.example.hla0191_tamz2.MapImages.WALL;
import static com.example.hla0191_tamz2.Sounds.moveSound;

public class MovingManager {

    public static int heroPos;
    private Fight f = new Fight();

    public MovingManager()
    {
        setHeroPos();
    }

    public String getDirection(int xStart, int yStart, int xEnd, int yEnd) {
        int xDif = xStart-xEnd;
        int yDif = yStart-yEnd;

        if(Math.abs(xDif) >= Math.abs(yDif)){
            if (xDif > 50) return "right";
            else if(xDif < -50) return "left";
        }
        else {
            if (yDif > 50) return "up";
            else if(yDif < -50) return "down";
        }

        return "";
    }

    private void setHeroPos() {
        int i = 0;
        for (int x: level) {
            if(x == HERO.get()) heroPos = i;
            i++;
        }
    }

    private boolean tryMove(int d) {
        if(level[heroPos+d] == WALL.get() || level[heroPos+d] == GOBLIN.get()) return false;
        else return true;
    }


    public void move(int direction) {
        if(tryMove(direction)) {
            moveSound.start();
            int door = level[heroPos+direction];
            if (door == EMPTY.get() || door == COIN.get() || door == ARROW.get() || door == PRINCESS.get()) {
                activity.tryPick(direction);
                level[heroPos] = EMPTY.get();
                heroPos += direction;
                level[heroPos] = HERO.get();
                f.startEnemyMoves();
            }
            else {
                MapGenerator g = new MapGenerator(door);
                g.getMap();
                setHeroPos();
                f.checkAttacks();
                activity.setAttackButtonsImages();
            }
        }
    }
}
