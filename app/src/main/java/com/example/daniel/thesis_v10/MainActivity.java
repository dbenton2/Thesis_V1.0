package com.example.daniel.thesis_v10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void tutorial(View view) {
        Intent intent = new Intent(this, beginTutorialActivity.class);
        startActivity(intent);
    }

    public void easy(View view){
        Intent intent = new Intent(this, easyGameActivity.class);
        startActivity(intent);
    }

    public void intermediate(View view){
        Intent intent = new Intent(this, intermediateGameActivity.class);
        startActivity(intent);
    }

    public void hard(View view){
        Intent intent = new Intent(this, hardGameActivity.class);
        startActivity(intent);
    }

    //public void multi(View view){
        //Intent intent = new Intent(this, multiplayerGameActivity.class);
        //startActivity(intent);
    //}
}