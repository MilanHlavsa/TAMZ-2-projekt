package com.example.hla0191_tamz2;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.hla0191_tamz2.Current_Activity.activity;
import static com.example.hla0191_tamz2.Current_Activity.game;
import static com.example.hla0191_tamz2.Fight.canArrowAttackEnemyPos;
import static com.example.hla0191_tamz2.Fight.canFireballAttack;
import static com.example.hla0191_tamz2.Fight.canSwordAttackEnemyPos;
import static com.example.hla0191_tamz2.Fight.enemyHP;
import static com.example.hla0191_tamz2.Fight.enemyPositions;
import static com.example.hla0191_tamz2.Game.arrows;
import static com.example.hla0191_tamz2.Game.coins;
import static com.example.hla0191_tamz2.Game.goblinsKilled;
import static com.example.hla0191_tamz2.Game.goblinsKilledGoal;
import static com.example.hla0191_tamz2.Game.hp;
import static com.example.hla0191_tamz2.Game.level;
import static com.example.hla0191_tamz2.Game.levelSize;
import static com.example.hla0191_tamz2.MapImages.ARROW;
import static com.example.hla0191_tamz2.MapImages.COIN;
import static com.example.hla0191_tamz2.MapImages.PRINCESS;
import static com.example.hla0191_tamz2.MovingManager.heroPos;
import static com.example.hla0191_tamz2.Sounds.coinSound;
import static com.example.hla0191_tamz2.Sounds.loseSound;
import static com.example.hla0191_tamz2.Sounds.winSound;

public class MainActivity extends Activity {

    private String playerName;
    private String difficulty;

    private TextView coinCount;
    private TextView arrowCount;
    private Fight fight = new Fight();
    private ImageView swordAttack;
    private ImageView arrowAttack;
    private ImageView fireballAttack;
    private ImageView hpImage;
    private Button buyArrowButton;
    private Button buyFireballButton;
    private Button endGameButton;
    private Button endGameButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        playerName = prefs.getString("name", "");
        difficulty = prefs.getString("difficulty", "");
        goblinsKilledGoal = prefs.getInt("goblinCount", 10);

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

        endGameButton = findViewById(R.id.endGame_button);
        endGameButton.setVisibility(View.INVISIBLE);
        endGameButton2 = findViewById(R.id.endGame_button2);
        endGameButton2.setVisibility(View.INVISIBLE);
        endGameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Menu.class);
                finish();
                startActivity(intent);
                playAgain(null);
            }
        });

        Current_Activity c = new Current_Activity(this);

        Sounds s = new Sounds();
    }

    private void Win() {
        winSound.start();
        endGameButton.setText("YOU WIN,\nPLAY AGAIN?");
        endGameButton.setVisibility(View.VISIBLE);
        endGameButton2.setVisibility(View.VISIBLE);
        if(playerName != "") {
            Database db = new Database(this);
            db.insertData(playerName, difficulty, goblinsKilled);
        }
    }

    public void tryPick(int d) {
        if(level[heroPos+d] == COIN.get()) {
            coinSound.start();
            coins++;
            coinCount.setText(Integer.toString(coins));
        }
        else if(level[heroPos+d] == ARROW.get()) {
            coinSound.start();
            arrows++;
            arrowCount.setText(Integer.toString(arrows));
        }
        else if(level[heroPos+d] == PRINCESS.get()) {
            Win();
        }
    }

    public void getHit(int d) {
        hp += d;
        if(hp <= 0) {
            endGameButton.setText("YOU LOSE,\nPLAY AGAIN?");
            endGameButton.setVisibility(View.VISIBLE);
            endGameButton2.setVisibility(View.VISIBLE);
            loseSound.start();
        }
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
        if (coins>2 && !canFireballAttack){
            coins -=3;
            canFireballAttack = true;
            coinCount.setText(Integer.toString(coins));
            buyFireballButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setBuyButtonsInvisible() {
        buyFireballButton.setVisibility(View.INVISIBLE);
        buyArrowButton.setVisibility(View.INVISIBLE);
    }

    public void setBuyButtonsVisible() {
        if (!canFireballAttack) buyFireballButton.setVisibility(View.VISIBLE);
        buyArrowButton.setVisibility(View.VISIBLE);
    }

    public void ShootArrow(){
        arrows--;
        arrowCount.setText(Integer.toString(arrows));
    }

    public void setAttackButtonsImages() {
        if (canSwordAttackEnemyPos > -1) swordAttack.setImageResource(R.drawable.swordbutton);
        else swordAttack.setImageResource(R.drawable.swordbutton_dis);

        if (canArrowAttackEnemyPos > -1 && arrows > 0) arrowAttack.setImageResource(R.drawable.arrowbutton);
        else arrowAttack.setImageResource(R.drawable.arrowbutton_dis);

        if (canFireballAttack) fireballAttack.setImageResource(R.drawable.fireballbutton);
        else fireballAttack.setImageResource(R.drawable.fireballbutton_dis);
    }

    public void playAgain(View view) {
        coins = 0;
        coinCount.setText(Integer.toString(coins));
        arrows = 0;
        arrowCount.setText(Integer.toString(arrows));
        canFireballAttack = false;
        hp = 4;
        getHit(0);
        goblinsKilled = 0;

        fight.fight = false;
        enemyPositions.clear();
        enemyHP.clear();

        levelSize = 9;
        level = new int[] {
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
        heroPos = 22;
        endGameButton.setVisibility(View.INVISIBLE);
        endGameButton2.setVisibility(View.INVISIBLE);
        game.onSizeChanged (game.getWidth(),game.getHeight(),game.getWidth(),game.getHeight());

        swordAttack.setImageResource(R.drawable.swordbutton_dis);
        arrowAttack.setImageResource(R.drawable.arrowbutton_dis);
        fireballAttack.setImageResource(R.drawable.fireballbutton_dis);
        buyArrowButton.setVisibility(View.VISIBLE);
        buyFireballButton.setVisibility(View.VISIBLE);

        game.invalidate();
    }

    @Override
    public void onBackPressed() {
        if (true) {}
        else {
            super.onBackPressed();
        }
    }
}
