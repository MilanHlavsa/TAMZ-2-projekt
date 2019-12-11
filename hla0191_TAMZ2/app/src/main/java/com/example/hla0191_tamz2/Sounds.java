package com.example.hla0191_tamz2;

import android.media.MediaPlayer;

import static com.example.hla0191_tamz2.Current_Activity.activity;

public class Sounds {

    public static MediaPlayer arrowSound = MediaPlayer.create(activity, R.raw.arrow);
    public static MediaPlayer coinSound = MediaPlayer.create(activity, R.raw.coin);
    public static MediaPlayer fireballSound = MediaPlayer.create(activity, R.raw.fireball);
    public static MediaPlayer loseSound = MediaPlayer.create(activity, R.raw.lose);
    public static MediaPlayer moveSound = MediaPlayer.create(activity, R.raw.move);
    public static MediaPlayer swordSound = MediaPlayer.create(activity, R.raw.sword);
    public static MediaPlayer winSound = MediaPlayer.create(activity, R.raw.win);

    public Sounds(){
        MediaPlayer music = MediaPlayer.create(activity, R.raw.music);
        music.setLooping(true);
        music.setVolume(0.5f, 0.5f);
        music.start();
    }

}
