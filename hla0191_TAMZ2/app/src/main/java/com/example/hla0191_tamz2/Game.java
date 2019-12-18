package com.example.hla0191_tamz2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

public class Game extends View {

    private Bitmap[] bmp;
    private int width;
    private int height;

    public static int hp = 4;
    public static int arrows = 0;
    public static int coins = 0;
    public static int goblinsKilled = 0;
    public static int goblinsKilledGoal;

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
        bmp = new Bitmap[11];

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
        bmp[PRINCESS.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.princess);

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

    private int xStart;
    private int yStart;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xStart = (int)event.getX();
            yStart = (int)event.getY();
        }

        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            int x = (int)event.getX();
            int y = (int)event.getY();

            String direction = mm.getDirection(xStart, yStart, x, y);

            if (direction == "up") mm.move(-levelSize);
            else if(direction == "down") mm.move(levelSize);
            else if(direction == "left") mm.move(+1);
            else if(direction == "right") mm.move(-1);


            this.invalidate();
        }
        return true;
    }
}
