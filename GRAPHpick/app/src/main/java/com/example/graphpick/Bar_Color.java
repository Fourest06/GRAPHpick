package com.example.graphpick;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Bar_Color extends AppCompatActivity {

    ImageButton color1, color2, color3, color4, color5, color6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_color);

        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        color1 = findViewById(R.id.myImageButton1);
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] selectedColors = {
                        "#3C6255",
                        "#61876E",
                        "#A6BB8D",
                        "#EAE7B1",
                        "#FFFDD8"
                };
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color2 = findViewById(R.id.myImageButton2);
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] selectedColors = {
                        "#645CBB",
                        "#A084DC",
                        "#BFACE2",
                        "#EBC7E6",
                        "#FFDBFA"
                };
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color3 = findViewById(R.id.myImageButton3);
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] selectedColors = {
                        "#D21312",
                        "#ED2B2A",
                        "#F15A59",
                        "#FF8B8B",
                        "#FFC7C6"
                };
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color4 = findViewById(R.id.myImageButton4);
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] selectedColors = {
                        "#0D4C92",
                        "#59C1BD",
                        "#A0E4CB",
                        "#CFF5E7",
                        "#E4FFF5"
                };
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color5 = findViewById(R.id.myImageButton5);
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] selectedColors = {
                        "#146C94",
                        "#19A7CE",
                        "#AFD3E2",
                        "#C6E6F3",
                        "#EBF9FF"
                };
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        color6 = findViewById(R.id.myImageButton6);
        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] selectedColors = {
                        "#884A39",
                        "#C38154",
                        "#F9E0BB",
                        "#FFC26F",
                        "#FFE5BF"
                };
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColors", selectedColors);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}