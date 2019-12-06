package com.example.hla0191_tamz2;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;

import static com.example.hla0191_tamz2.Current_Activity.game;
import static com.example.hla0191_tamz2.Game.arrows;
import static com.example.hla0191_tamz2.Game.goblinsKilled;
import static com.example.hla0191_tamz2.Game.hp;
import static com.example.hla0191_tamz2.Game.level;
import static com.example.hla0191_tamz2.Game.levelSize;
import static com.example.hla0191_tamz2.MapImages.ARROW;
import static com.example.hla0191_tamz2.MapImages.COIN;
import static com.example.hla0191_tamz2.MapImages.EMPTY;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;
import static com.example.hla0191_tamz2.MapImages.HERO;
import static com.example.hla0191_tamz2.MovingManager.heroPos;
import static com.example.hla0191_tamz2.Current_Activity.activity;

public class Fight {
    public static boolean fight = false;
    public static ArrayList<Integer> enemyPositions = new ArrayList<>();
    public static ArrayList<Integer> enemyHP = new ArrayList<>();
    public static int dropCount;
    public static int canSwordAttackEnemyPos = -1;
    public static int canArrowAttackEnemyPos = -1;
    public static boolean canFireballAttack = false;

    public void setEnemyPositions() {
        dropCount = 0;
        enemyPositions.clear();
        enemyHP.clear();
        int i = 0;
        for (int x: level) {
            if(x == GOBLIN.get()) {
                enemyPositions.add(i);
                enemyHP.add(2);
            }
            i++;
        }
        dropCount = enemyPositions.size();
    }

    public boolean canAttack(int victim, int attacker) {
        if (victim == attacker-levelSize || victim == attacker-1 || victim == attacker+1 || victim == attacker+levelSize) return true;
        return false;
    }

    private boolean tryMove(int newEnemyPos, int[] level) {
        if(level[newEnemyPos] == EMPTY.get()) return true;
        else return false;
    }

    private int getNewPosition(int heroRow, int enemyRow, int enemyPos) {
        if (heroRow > enemyRow) return enemyPos+levelSize;
        else if (heroRow < enemyRow) return enemyPos-levelSize;
        else if (heroPos > enemyPos) return enemyPos+1;
        else return enemyPos-1;
    }

    public void startEnemyMoves() {
        if(fight) {
            for(int i = 0; i < enemyPositions.size(); i++) {
                enemyMove(i);
            }
            checkAttacks();
        }
    }

    private void enemyMove(int index) {
        int enemyPos = enemyPositions.get(index);

        if (canAttack(heroPos, enemyPos)) activity.getHit(-1);
        else {
            int heroRow = (int)Math.ceil(heroPos / levelSize);
            int enemyRow = (int)Math.ceil(enemyPos / levelSize);

            int newEnemyPos = getNewPosition(heroRow, enemyRow, enemyPos);

            if (tryMove(newEnemyPos, level)) {
                level[newEnemyPos] = GOBLIN.get();
                level[enemyPos] = EMPTY.get();
                enemyPositions.set(index, newEnemyPos);
            }
        }
    }

    private void Drop() {
        for (int i = 0; i < dropCount; i++)
        {
            int x = (int)(Math.random()*(((levelSize*levelSize-1)-levelSize)+1))+levelSize;
            while (!(level[x] == EMPTY.get())) {x = (int)(Math.random()*(((levelSize*levelSize-1)-levelSize)+1))+levelSize;}

            if(x%3 == 0) level[x] = EMPTY.get();
            else if(x%3 == 1) level[x] = COIN.get();
            else if(x%3 == 2) level[x] = ARROW.get();
        }
    }

    private void heroSuccessAttack(int i) {
        if (enemyHP.get(i) == 0) {
            level[enemyPositions.get(i)] = EMPTY.get();
            enemyPositions.remove(i);
            enemyHP.remove(i);
            goblinsKilled++;
            if (enemyPositions.size() == 0) {
                fight = false;
                hp = 4;
                activity.getHit(0);
                activity.setBuyButtonsVisible();
                Drop();
            }
            checkAttacks();
        }
        startEnemyMoves();

        game.invalidate();
    }

    public void heroSwordAttack() {
        if(canSwordAttackEnemyPos > -1) {
            enemyHP.set(canSwordAttackEnemyPos, enemyHP.get(canSwordAttackEnemyPos)-1);
            heroSuccessAttack(canSwordAttackEnemyPos);
        }
    }

    private boolean canArrowAttackHelper(int victim, int attacker, int direction) {
        int i = 0;
        while(level[attacker+i*direction] > 4) {
            if (victim == attacker+i*direction) return true;
            i++;
        }
        return false;
    }

    private boolean canArrowAttack(int victim, int attacker) {
        if(canArrowAttackHelper(victim, attacker, -levelSize)) return true;
        else if(canArrowAttackHelper(victim, attacker, levelSize)) return true;
        else if(canArrowAttackHelper(victim, attacker, -1)) return true;
        else if(canArrowAttackHelper(victim, attacker, 1)) return true;
        else return false;
    }

    public void heroArrowAttack() {
        if(arrows > 0) {
            if(canArrowAttackEnemyPos > -1) {
                enemyHP.set(canArrowAttackEnemyPos, enemyHP.get(canArrowAttackEnemyPos)-1);
                activity.ShootArrow();
                heroSuccessAttack(canArrowAttackEnemyPos);
            }
        }
    }

    public void heroFireballAttack() {
        if(canFireballAttack) {
            enemyHP.set(0, 0);
            canFireballAttack = false;
            heroSuccessAttack(0);
        }
    }

    public void checkAttacks() {

        canArrowAttackEnemyPos = -1;
        canSwordAttackEnemyPos = -1;
        for(int i = 0; i < enemyPositions.size(); i++) {
            if (canAttack(enemyPositions.get(i), heroPos)) {
                canSwordAttackEnemyPos = i;
                break;
            }
        }

        if (arrows > 0) {
            for (int i = 0; i < enemyPositions.size(); i++) {
                if (canArrowAttack(enemyPositions.get(i), heroPos)) {
                    canArrowAttackEnemyPos = i;
                    break;
                }
            }
        }

        activity.setAttackButtonsImages();
    }
}
