package com.example.hla0191_tamz2;

import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.util.Log;

import java.io.Console;
import java.math.MathContext;
import java.util.Random;

import static com.example.hla0191_tamz2.Current_Activity.game;
import static com.example.hla0191_tamz2.Game.level;
import static com.example.hla0191_tamz2.Fight.fight;
import static com.example.hla0191_tamz2.Game.levelSize;
import static com.example.hla0191_tamz2.MapImages.DOWN;
import static com.example.hla0191_tamz2.MapImages.EMPTY;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;
import static com.example.hla0191_tamz2.Current_Activity.activity;
import static com.example.hla0191_tamz2.MapImages.HERO;
import static com.example.hla0191_tamz2.MapImages.LEFT;
import static com.example.hla0191_tamz2.MapImages.RIGHT;
import static com.example.hla0191_tamz2.MapImages.UP;
import static com.example.hla0191_tamz2.MapImages.WALL;

public class MapGenerator {

    private int lastDoorPosition;

    public MapGenerator(int lastDoorPosition) {
        this.lastDoorPosition = lastDoorPosition;
    }

    private boolean isFight(){
        for (int x: level) {
            if(x == GOBLIN.get()) return true;
        }
        return false;
    }

    private int getNewLevelSize(int max, int min) {
        int x = (int)(Math.random()*((max-min)+1))+min;
        if (x%2 == 1) return x;
        else return x-1;
    }

    private void setDefaultMap(int size) {

        for (int j = 0; j < size - 1; j++) {
            level[j] = EMPTY.get();
        }
        for (int i = 1; i <= levelSize; i++) {
            level[i] = WALL.get();
            level[size-i] = WALL.get();
            level[levelSize*(i-1)] = WALL.get();
            level[(levelSize*i)-1] = WALL.get();
        }
    }

    private void setDoors(int size, int  x) {
        level[x] = UP.get();
        level[levelSize*(x+1)-1] = LEFT.get();
        level[size-x-1] = DOWN.get();
        level[levelSize*x] = RIGHT.get();
    }

    private void setHero(int[] tabu) {
        if (lastDoorPosition == UP.get()) level[tabu[0]] = HERO.get();
        else if (lastDoorPosition == LEFT.get()) level[tabu[1]] = HERO.get();
        else if (lastDoorPosition == DOWN.get()) level[tabu[2]] = HERO.get();
        else if (lastDoorPosition == RIGHT.get()) level[tabu[3]] = HERO.get();
    }

    private int  getRandomWallsCount() {
        if(levelSize == 7) return (int)(Math.random()*((2-1)+1))+1;
        else if (levelSize == 9) return (int)(Math.random()*((4-2)+1))+2;
        else if (levelSize == 11) return (int)(Math.random()*((8-4)+1))+4;
        else return 1;
    }

    private int  getRandomGoblinsCount() {
        if(levelSize == 7) return (int)(Math.random()*((2-1)+1))+1;
        else if (levelSize == 9) return (int)(Math.random()*((3-2)+1))+2;
        else if (levelSize == 11) return (int)(Math.random()*((5-3)+1))+3;
        else return 1;
    }

    private boolean isTabuPos(int[] tabu, int pos) {
        for (int i = 0; i < tabu.length; i++) {
            if(tabu[i] == pos) return true;
        }
        return false;
    }

    private void setRandomBlocks(int[] tabu, int count, int block) {
        int i = 0;
        while (i < count) {
            int randomPos = (int)(Math.random()*(((levelSize*levelSize-1)-levelSize)+1))+levelSize;
            //Log.d("randomPos", randomPos+"");
            if(level[randomPos] == EMPTY.get() && !isTabuPos(tabu, randomPos)) {
                level[randomPos] = block;
                i++;
            }
        }
    }

    private void generateRandomMap() {
        //double x = (Math.random()*((max-min)+1))+min;
        levelSize = getNewLevelSize(12,7);
        int size = levelSize*levelSize;
        level = new int[size];
        setDefaultMap(size);

        int halfLevelPosition = (int)Math.floor(levelSize/2);
        int[] tabuPositions = {size-halfLevelPosition-1-levelSize, levelSize*halfLevelPosition+1, halfLevelPosition+levelSize, levelSize*(halfLevelPosition+1)-2};
        setDoors(size, halfLevelPosition);
        setHero(tabuPositions);

        setRandomBlocks(tabuPositions, getRandomWallsCount(), WALL.get());
        setRandomBlocks(tabuPositions, getRandomGoblinsCount(), GOBLIN.get());
    }


    public void getMap() {
        generateRandomMap();
        game.onSizeChanged (game.getWidth(),game.getHeight(),game.getWidth(),game.getHeight());

        if(isFight()) {
            fight = true;
            Fight f = new Fight();
            f.setEnemyPositions();
            activity.setBuyButtonsInvisible();
        }
    }
}
