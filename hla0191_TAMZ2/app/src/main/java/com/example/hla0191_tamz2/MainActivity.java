package com.example.hla0191_tamz2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.hla0191_tamz2.MapImages.COIN;

public class MainActivity extends Activity {

    private TextView coinCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinCount = findViewById(R.id.coinCount);

        Current_Activity c = new Current_Activity(this);
    }

    public void tryPickCoin(int heroPos,int d, int[] level) {
        if(level[heroPos+d] == COIN.get()) {
            int x = Integer.parseInt(coinCount.getText().toString());
            x++;
            coinCount.setText(Integer.toString(x));
        }
    }
}
