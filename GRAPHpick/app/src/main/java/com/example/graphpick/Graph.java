package com.example.graphpick;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph extends AppCompatActivity {

    BottomNavigationView nav;
    private static final int GALLERY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_graph);

        Intent intent = getIntent();
        ArrayList<String> dataF = intent.getStringArrayListExtra("dataF");
        ArrayList<String> dataS = intent.getStringArrayListExtra("dataS");
        Log.d("ExtractedDataF", Arrays.toString(dataF.toArray()));
        Log.d("ExtractedDataS", Arrays.toString(dataS.toArray()));

        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        ImageButton bar = findViewById(R.id.barButton);
        ImageButton line = findViewById(R.id.lineButton);
        ImageButton pie = findViewById(R.id.pieButton);

        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent barIntent = new Intent(Graph.this, Bar.class);
                barIntent.putStringArrayListExtra("dataF", dataF);
                barIntent.putStringArrayListExtra("dataS", dataS);
                startActivity(barIntent);
            }
        });

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lineIntent = new Intent(Graph.this, Line.class);
                lineIntent.putStringArrayListExtra("dataF", dataF);
                lineIntent.putStringArrayListExtra("dataS", dataS);
                startActivity(lineIntent);
            }
        });

        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pieIntent = new Intent(Graph.this, Pie.class);
                pieIntent.putStringArrayListExtra("dataF", dataF);
                pieIntent.putStringArrayListExtra("dataS", dataS);
                startActivity(pieIntent);
            }
        });

        nav = findViewById(R.id.nav);
        nav.getMenu().setGroupCheckable(0, true, true);
        nav.getMenu().setGroupCheckable(0, false, true);
        nav.setSelectedItemId(R.id.dummy);

        nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Upload:
                        openGallery();
                        return true;
                    case R.id.Settings:
                        startActivity(new Intent(getApplicationContext(), Instruction.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.About:
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Camera:
                        startActivity(new Intent(getApplicationContext(), Cam.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent galleryIntent = new Intent(this, Gallery.class);
            galleryIntent.setData(data.getData());
            startActivity(galleryIntent);
        }
    }
}