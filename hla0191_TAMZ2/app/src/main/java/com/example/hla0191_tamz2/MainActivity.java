package com.example.hla0191_tamz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.hla0191_tamz2.Current_Activity.game;
import static com.example.hla0191_tamz2.Game.arrows;
import static com.example.hla0191_tamz2.Game.coins;
import static com.example.hla0191_tamz2.Game.fireball;
import static com.example.hla0191_tamz2.Game.hp;
import static com.example.hla0191_tamz2.MapImages.ARROW;
import static com.example.hla0191_tamz2.MapImages.COIN;
import static com.example.hla0191_tamz2.MovingManager.heroPos;
import static com.example.hla0191_tamz2.Game.level;

public class MainActivity extends Activity {

    private TextView coinCount;
    private TextView arrowCount;
    private Fight fight = new Fight();
    private ImageView swordAttack;
    private ImageView arrowAttack;
    private ImageView fireballAttack;
    private ImageView hpImage;
    private Button buyArrowButton;
    private Button buyFireballButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinCount = findViewById(R.id.coinCount);
        hpImage = findViewById(R.id.HP);
        arrowCount = findViewById(R.id.arrowCount);
        buyArrowButton = findViewById(R.id.buy_arrow);
        buyFireballButton = findViewById(R.id.buy_fireball);

        swordAttack = findViewById(R.id.sword);
        swordAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fight.heroSwordAttack();
            }
        });

        arrowAttack = findViewById(R.id.arrow);
        arrowAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fight.heroArrowAttack();
            }
        });

        fireballAttack = findViewById(R.id.fireball);
        fireballAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fight.heroFireballAttack();
            }
        });

        Current_Activity c = new Current_Activity(this);
    }

    public void tryPick(int d) {
        if(level[heroPos+d] == COIN.get()) {
            coins++;
            coinCount.setText(Integer.toString(coins));
        }
        else if(level[heroPos+d] == ARROW.get()) {
            arrows++;
            arrowCount.setText(Integer.toString(arrows));
        }
    }

    public void getHit(int d) {
        hp += d;
        if(hp <= 0) {game.setVisibility(View.GONE);}
        else if(hp == 1) hpImage.setImageResource(R.drawable.hp0);
        else if(hp == 2) hpImage.setImageResource(R.drawable.hp1);
        else if(hp == 3) hpImage.setImageResource(R.drawable.hp2);
        else if(hp == 4) hpImage.setImageResource(R.drawable.hp3);
    }

    public void buyArrow(View view) {
        if (coins>0){
            coins--;
            arrows++;
            arrowCount.setText(Integer.toString(arrows));
            coinCount.setText(Integer.toString(coins));
        }
    }

    public void buyFireball(View view) {
        if (coins>2 && !fireball){
            coins -=3;
            fireball = true;
            coinCount.setText(Integer.toString(coins));
            buyFireballButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setBuyButtonsInvisible() {
        buyFireballButton.setVisibility(View.INVISIBLE);
        buyArrowButton.setVisibility(View.INVISIBLE);
    }

    public void setBuyButtonsVisible() {
        if (!fireball) buyFireballButton.setVisibility(View.VISIBLE);
        buyArrowButton.setVisibility(View.VISIBLE);
    }

    public void ShootArrow(){
        arrows--;
        arrowCount.setText(Integer.toString(arrows));
    }
}
