package com.example.graphpick;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.provider.MediaStore;
import android.net.Uri;
import androidx.activity.result.ActivityResultLauncher;

public class MainMenu extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Button button1, button3, button4;
    private static final int REQUEST_SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_menu);

        button1 = (Button) findViewById(R.id.camera);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera();
            }
        });

        Button button2 = findViewById(R.id.upload);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        button3 = (Button) findViewById(R.id.settings);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings();
            }
        });

        button4 = (Button) findViewById(R.id.about);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Intent displayImageIntent = new Intent(this, Gallery.class);
            displayImageIntent.setData(selectedImageUri);
            startActivity(displayImageIntent);
        }
    }

    public void Camera() {
        Intent intent = new Intent(this, Cam.class);
        startActivity(intent);
    }

    public void Settings() {
        Intent intent = new Intent(this, Instruction.class);
        startActivity(intent);
    }

    public void About() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

}