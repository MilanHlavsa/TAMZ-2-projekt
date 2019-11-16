package com.example.hla0191_tamz2;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;

import static com.example.hla0191_tamz2.Current_Activity.game;
import static com.example.hla0191_tamz2.Game.arrows;
import static com.example.hla0191_tamz2.Game.fireball;
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

    public void setEnemyPositions() {
        dropCount = 0;
        enemyPositions.clear();
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
            int x = (int)(Math.random()*((levelSize*levelSize-0)+1))+0;
            while (!(level[x] == EMPTY.get())) {x = (int)(Math.random()*((levelSize*levelSize-0)+1))+0;}

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

            if (enemyPositions.size() == 0) {
                fight = false;
                hp = 4;
                activity.getHit(0);
                activity.setBuyButtonsVisible();
                Drop();
            }
        }
        startEnemyMoves();

        game.invalidate();
    }

    public void heroSwordAttack() {
        boolean attack = false;
        int i;
        for(i = 0; i < enemyPositions.size(); i++) {
            if (canAttack(enemyPositions.get(i), heroPos)) {
                attack = true;
                break;
            }
        }

        if(attack) {
            enemyHP.set(i, enemyHP.get(i)-1);
            heroSuccessAttack(i);
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
            boolean attack = false;
            int i;
            for (i = 0; i < enemyPositions.size(); i++) {
                if (canArrowAttack(enemyPositions.get(i), heroPos)) {
                    attack = true;
                    break;
                }
            }

            if(attack) {
                enemyHP.set(i, enemyHP.get(i)-1);
                activity.ShootArrow();
                heroSuccessAttack(i);
            }
        }
    }

    public void heroFireballAttack() {
        if(fireball) {
            enemyHP.set(0, 0);
            fireball = false;
            heroSuccessAttack(0);
        }
    }
}
