package com.example.hla0191_tamz2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.os.Handler;

import java.util.Timer;

import static com.example.hla0191_tamz2.AnimImages.ANEMPTY;
import static com.example.hla0191_tamz2.AnimImages.EX1;
import static com.example.hla0191_tamz2.AnimImages.EX2;
import static com.example.hla0191_tamz2.AnimImages.EX3;
import static com.example.hla0191_tamz2.AnimImages.EX4;
import static com.example.hla0191_tamz2.AnimImages.EX5;
import static com.example.hla0191_tamz2.AnimImages.EX6;
import static com.example.hla0191_tamz2.AnimImages.EX7;
import static com.example.hla0191_tamz2.AnimImages.EX8;
import static com.example.hla0191_tamz2.Game.levelSize;
import static com.example.hla0191_tamz2.Current_Activity.anim;

public class Animation extends View {

    private Bitmap[] bmp;
    private int width;
    private int height;

    private final Handler handler = new Handler();

    private Timer timer = new Timer();

    public static int animLevel[] = {
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8,
            8,8,8,8,8,8,8,8,8
    };

    public Animation(Context context) {
        super(context);
        init(context);
    }

    public Animation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Animation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[11];

        bmp[EX1.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex1);
        bmp[EX2.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex2);
        bmp[EX3.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex3);
        bmp[EX4.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex4);
        bmp[EX5.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex5);
        bmp[EX6.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex6);
        bmp[EX7.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex7);
        bmp[EX8.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.ex8);
        bmp[ANEMPTY.get()] = BitmapFactory.decodeResource(getResources(), R.drawable.nan);

        Current_Activity ca = new Current_Activity();
        ca.setAnim(this);
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
                canvas.drawBitmap(bmp[animLevel[i*levelSize + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
            }
        }
    }

    public void playExplosion(int pos) {
        final int exPos = pos;
        for (int i = 0; i < 9; i++) {
            final int j = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animLevel[exPos] = j;
                    anim.invalidate();
                }
            }, (i + 1) * 75);
        }
    }
}
