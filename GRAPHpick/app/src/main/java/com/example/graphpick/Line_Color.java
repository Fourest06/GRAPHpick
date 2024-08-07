package com.example.graphpick;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Line_Color extends AppCompatActivity {

    ImageButton color1, color2, color3, color4, color5, color6, color7, color8,
            color9, color10, color11, color12, color13, color14, color15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_color);

        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        color1 = findViewById(R.id.imageButton1);
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#161313";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color2 = findViewById(R.id.imageButton2);
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#810707";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color3 = findViewById(R.id.imageButton3);
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#E70707";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color4 = findViewById(R.id.imageButton4);
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#825A0B";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color5 = findViewById(R.id.imageButton5);
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#FF8A00";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color6 = findViewById(R.id.imageButton6);
        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#FFF200";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color7 = findViewById(R.id.imageButton7);
        color7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#DF2092";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color8 = findViewById(R.id.imageButton8);
        color8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#24B817";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color9 = findViewById(R.id.imageButton9);
        color9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#76FF46";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color10 = findViewById(R.id.imageButton10);
        color10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#951CA0";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color11 = findViewById(R.id.imageButton11);
        color11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#03B4FF";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color12 = findViewById(R.id.imageButton12);
        color12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#78ECF3";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color13 = findViewById(R.id.imageButton13);
        color13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#747778";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color14 = findViewById(R.id.imageButton14);
        color14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#564CB9";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color15 = findViewById(R.id.imageButton15);
        color15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColors = "#0961B3";
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}