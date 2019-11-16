package com.example.hla0191_tamz2;

import android.util.Log;

import java.util.ArrayList;
import java.util.EmptyStackException;

import static com.example.hla0191_tamz2.MapImages.EMPTY;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;

public class Fight {
    public static boolean fight = false;
    public static ArrayList<Integer> enemyPositions = new ArrayList<>();

    public void setEnemyPositions(int[] level) {
        enemyPositions.clear();
        int i = 0;
        for (int x: level) {
            if(x == GOBLIN.get()) enemyPositions.add(i);
            i++;
        }
    }

    private boolean isHeroNear(int h, int e, int ls) {
        if (h == e-ls || h == e-1 || h == e+1 || h == e+ls) return true;
        return false;
    }

    private void Attack() {}

    private boolean tryMove(int newEnemyPos, int[] level) {
        if(level[newEnemyPos] == EMPTY.get()) return true;
        else return false;
    }

    private int getNewPosition(int heroRow, int enemyRow, int heroPos, int enemyPos, int levelSize) {
        if (heroRow > enemyRow) return enemyPos+levelSize;
        else if (heroRow < enemyRow) return enemyPos-levelSize;
        else if (heroPos > enemyPos) return enemyPos+1;
        else return enemyPos-1;
    }

    public int[] enemyMove(int heroPos, int[] level, int index, int levelSize) {
        int enemyPos = enemyPositions.get(index);

        if (isHeroNear(heroPos, enemyPos, levelSize)) Attack();
        else {
            int heroRow = (int)Math.ceil(heroPos / levelSize);
            int enemyRow = (int)Math.ceil(enemyPos / levelSize);

            int newEnemyPos = getNewPosition(heroRow, enemyRow, heroPos, enemyPos, levelSize);

            if (tryMove(newEnemyPos, level)) {
                level[newEnemyPos] = GOBLIN.get();
                level[enemyPos] = EMPTY.get();
            } else Log.d("Goblin ", index + " passnul");

            /*if (heroRow > enemyRow) level[enemyPos+levelSize] = GOBLIN.get();
            else if (heroRow < enemyRow) level[enemyPos-levelSize] = GOBLIN.get();
            else if (heroPos > enemyPos) level[enemyPos+1] = GOBLIN.get();
            else level[enemyPos-1] = GOBLIN.get();*/

            setEnemyPositions(level);
        }
        return level;
    }
}
