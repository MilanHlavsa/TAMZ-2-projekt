package com.example.hla0191_tamz2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import static com.example.hla0191_tamz2.MapImages.COIN;
import static com.example.hla0191_tamz2.MapImages.DOWN;
import static com.example.hla0191_tamz2.MapImages.EMPTY;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;
import static com.example.hla0191_tamz2.MapImages.HERO;
import static com.example.hla0191_tamz2.MapImages.LEFT;
import static com.example.hla0191_tamz2.MapImages.RIGHT;
import static com.example.hla0191_tamz2.MapImages.UP;
import static com.example.hla0191_tamz2.MapImages.WALL;

public class MovingManager {

    public static int heroPos;

    private Current_Activity a = new Current_Activity();

    public MovingManager(int[] level)
    {
        setHeroPos(level);
    }

    private void setHeroPos(int[] level) {
        int i = 0;
        for (int x: level) {
            if(x == HERO.get()) this.heroPos = i;
            i++;
        }
    }

    private boolean tryMove(int d, int[] level) {
        if(level[this.heroPos+d] == WALL.get() || level[this.heroPos+d] == GOBLIN.get()) return false;
        else return true;
    }

    private int getDoor(int d, int[] level) {
        if(level[this.heroPos+d] == UP.get()) return 6;
        else if(level[this.heroPos+d] == LEFT.get()) return 7;
        else if(level[this.heroPos+d] == DOWN.get()) return 8;
        else if(level[this.heroPos+d] == RIGHT.get()) return 9;
        else return 0;
    }

    public int[] move(int[] level, int direction) {
        if(tryMove(direction, level)) {
            int door = getDoor(direction, level);
            if (door == 0) {
                a.activity.tryPickCoin(heroPos, direction, level);
                level[this.heroPos] = EMPTY.get();
                this.heroPos += direction;
                level[this.heroPos] = HERO.get();
            }
            else {
                MapGenerator g = new MapGenerator(door);
                level = g.getMap();
                setHeroPos(level);
                return level;
            }
        }
        return level;
    }
}
