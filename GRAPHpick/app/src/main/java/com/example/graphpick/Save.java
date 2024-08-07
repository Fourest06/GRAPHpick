package com.example.graphpick;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Save extends AppCompatActivity {

    Button word, ppt, pdf, jpeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        word = findViewById(R.id.fimage1);
        ppt = findViewById(R.id.fimage2);
        pdf = findViewById(R.id.fimage3);
        jpeg = findViewById(R.id.fimage4);

        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
