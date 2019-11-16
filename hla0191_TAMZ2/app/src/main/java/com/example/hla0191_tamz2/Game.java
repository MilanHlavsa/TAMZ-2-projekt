package com.example.hla0191_tamz2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.example.hla0191_tamz2.Current_Activity.game;
import static com.example.hla0191_tamz2.MapImages.ARROW;
import static com.example.hla0191_tamz2.MapImages.COIN;
import static com.example.hla0191_tamz2.MapImages.DOWN;
import static com.example.hla0191_tamz2.MapImages.EMPTY;
import static com.example.hla0191_tamz2.MapImages.GOBLIN;
import static com.example.hla0191_tamz2.MapImages.HERO;
import static com.example.hla0191_tamz2.MapImages.LEFT;
import static com.example.hla0191_tamz2.MapImages.RIGHT;
import static com.example.hla0191_tamz2.MapImages.UP;
import static com.example.hla0191_tamz2.MapImages.WALL;
import static com.example.hla0191_tamz2.Fight.fight;

public class Game extends View {

    private Bitmap[] bmp;
    private int width;
    private int height;

    public static int hp = 4;
    public static int arrows = 0;
    public static boolean fireball = false;
    public static int coins = 0;

    /*int levelSize = 11;
    private int level[] = {
            1,1,1,1,1,6,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,2,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,1,
            9,0,0,0,0,0,0,0,0,0,7,
            1,0,0,0,0,0,0,0,0,0,1,
            1,0,4,0,0,0,0,0,4,0,1,
            1,0,0,0,0,4,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,8,1,1,1,1,1,
    };*/

    public static int levelSize = 9;
    public static int level[] = {
            0,0,0,0,1,0,0,0,0,
            0,5,5,5,5,5,5,5,0,
            0,5,5,5,6,5,5,5,0,
            0,5,5,5,5,5,5,5,0,
            4,5,5,5,5,5,5,5,2,
            0,5,8,5,5,5,8,5,0,
            0,5,5,5,8,5,5,5,0,
            0,5,5,5,5,5,5,5,0,
            0,0,0,0,3,0,0,0,0,
    };

    MovingManager mm = new MovingManager();

    public Game(Context context) {
        super(context);
        init(context);
    }

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Game(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[10];

        bmp[WALL.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        bmp[UP.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_up);
        bmp[LEFT.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_left);
        bmp[DOWN.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_down);
        bmp[RIGHT.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_right);
        bmp[EMPTY.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        bmp[HERO.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
        bmp[GOBLIN.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.goblin);
        bmp[COIN.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        bmp[ARROW.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);


        Current_Activity ca = new Current_Activity();
        ca.setGame(this);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / levelSize;
        height = h / levelSize;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < levelSize; i++) {
            for (int j = 0; j < levelSize; j++) {
                canvas.drawBitmap(bmp[level[i*levelSize + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int)event.getX();
        int y = (int)event.getY();
        //Log.d("reeeee", x + " " + y);

        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            if (y < 500 && y > 0 && x > 300 && x < 700) mm.move(-levelSize);
            else if(y > 500 && y < 1100 && x > 300 && x < 700) mm.move(levelSize);
            else if(x > 550) mm.move(+1);
            else mm.move(-1);

            this.invalidate();
        }
        return true;
    }

    /*public static void drawDelay(int time) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game.invalidate();
            }
        }, time);
    }*/
}
