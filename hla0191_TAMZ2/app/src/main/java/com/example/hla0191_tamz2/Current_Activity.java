package com.example.hla0191_tamz2;

public class Current_Activity {

    public static MainActivity activity;
    public static Game game;
    public static Animation anim;

    public Current_Activity() {}

    public Current_Activity(MainActivity a) {
        this.activity = a;
    }

    public void setGame(Game g) {
        game = g;
    }

    public void setAnim(Animation a) { anim = a; }
}
