package com.example.hla0191_tamz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private Button startButton;
    private Button scoresButton;
    private Button quitButton;

    private Button easyButton;
    private Button normalButton;
    private Button hardButton;

    private Button backButton;

    private TextView playerName;
    private TextView enterYourName;

    private ListView score;

    private void startGame(int goblinCount, String difficulty) {

        SharedPreferences.Editor editor = getSharedPreferences("sharedPreferences", MODE_PRIVATE).edit();
        editor.putString("name", playerName.getText().toString());
        editor.putInt("goblinCount", goblinCount);
        editor.putString("difficulty", difficulty);
        editor.apply();

        Intent intent = new Intent(Menu.this, MainActivity.class);
        startActivity(intent);
    }

    private void goBack() {
        easyButton.setVisibility(View.INVISIBLE);
        normalButton.setVisibility(View.INVISIBLE);
        hardButton.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        playerName.setVisibility(View.INVISIBLE);
        enterYourName.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);

        startButton.setVisibility(View.VISIBLE);
        scoresButton.setVisibility(View.VISIBLE);
        quitButton.setVisibility(View.VISIBLE);
    }

    private void initScore() {
        Database db = new Database(this);
        Cursor data = db.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add("Player: " + data.getString(1) + "\nGoblins killed: " + data.getString(2) + "\nDifficulty: " + data.getString(3));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        score.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                playerName.setText(prefs.getString("name", ""));

                easyButton.setVisibility(View.VISIBLE);
                normalButton.setVisibility(View.VISIBLE);
                hardButton.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
                playerName.setVisibility(View.VISIBLE);
                enterYourName.setVisibility(View.VISIBLE);

                startButton.setVisibility(View.INVISIBLE);
                scoresButton.setVisibility(View.INVISIBLE);
                quitButton.setVisibility(View.INVISIBLE);
            }
        });

        scoresButton = findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setVisibility(View.INVISIBLE);
                scoresButton.setVisibility(View.INVISIBLE);
                quitButton.setVisibility(View.INVISIBLE);

                backButton.setVisibility(View.VISIBLE);
                score.setVisibility(View.VISIBLE);
            }
        });

        quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        easyButton = findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(10, "Easy");
            }
        });

        normalButton = findViewById(R.id.normalButton);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(30, "Normal");
            }
        });

        hardButton = findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(50, "Hard");
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        playerName = findViewById(R.id.playerName);
        enterYourName = findViewById(R.id.enterYourName);

        score = findViewById(R.id.score);
        initScore();

        goBack();
    }

    @Override
    public void onBackPressed() {
        if (true) {}
        else {
            super.onBackPressed();
        }
    }
}
