package com.example.hla0191_tamz2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import static com.example.hla0191_tamz2.Current_Activity.game;
import static com.example.hla0191_tamz2.Fight.enemyPositions;
import static com.example.hla0191_tamz2.Game.level;
import static com.example.hla0191_tamz2.Current_Activity.activity;
import static com.example.hla0191_tamz2.Fight.fight;
import static com.example.hla0191_tamz2.MapImages.ARROW;
import static com.example.hla0191_tamz2.MapImages.COIN;
import static com.example.hla0191_tamz2.MapImages.DOWN;
import static com.example.hla0191_tamz2.MapImages.EMPTY;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;
import static com.example.hla0191_tamz2.MapImages.HERO;
import static com.example.hla0191_tamz2.MapImages.LEFT;
import static com.example.hla0191_tamz2.MapImages.PRINCESS;
import static com.example.hla0191_tamz2.MapImages.RIGHT;
import static com.example.hla0191_tamz2.MapImages.UP;
import static com.example.hla0191_tamz2.MapImages.WALL;

public class MovingManager {

    public static int heroPos;
    private Fight f = new Fight();

    private Current_Activity a = new Current_Activity();

    public MovingManager()
    {
        setHeroPos();
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
