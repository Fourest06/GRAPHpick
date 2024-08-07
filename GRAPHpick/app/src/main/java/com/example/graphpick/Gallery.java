package com.example.graphpick;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Gallery extends AppCompatActivity {

    BottomNavigationView nav;
    private static final int GALLERY_REQUEST_CODE = 2;
    ImageView imageView;
    Button nextButton;
    private Uri imageUri;
    private boolean isUploadIconClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_gallery);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView = findViewById(R.id.imageView1);
        nextButton = findViewById(R.id.circleButton);

        imageUri = getIntent().getData();
        if (imageUri != null) {
            imageView.setImageURI(imageUri);
            imageView.bringToFront();
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent extractIntent = new Intent(Gallery.this, Extract.class);
                if (imageUri != null) {
                    extractIntent.putExtra("imageUri", imageUri.toString());
                    startActivity(extractIntent);
                }
            }
        });

        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.Upload);
        updateUploadIcon(nav.getMenu().findItem(R.id.Upload));
        nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Upload:
                        openGallery();
                        return true;
                    case R.id.Settings:
                        resetGalleryIcon();
                        startActivity(new Intent(getApplicationContext(), Instruction.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.About:
                        resetGalleryIcon();
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Camera:
                        resetGalleryIcon();
                        startActivity(new Intent(getApplicationContext(), Cam.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void resetGalleryIcon() {
        isUploadIconClicked = false;
        updateUploadIcon(nav.getMenu().findItem(R.id.Upload));
    }

    private void updateUploadIcon(MenuItem item) {
        if (isUploadIconClicked) {
            item.setIcon(R.drawable.fupload2);
        } else {
            item.setIcon(R.drawable.fupload1);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}
