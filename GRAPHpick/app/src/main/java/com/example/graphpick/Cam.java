package com.example.graphpick;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.Manifest;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.widget.TextView;

public class Cam extends AppCompatActivity {

    BottomNavigationView nav;
    private static final int PERMISSION_CODE = 1234;
    private static final int CAPTURE_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 2;
    ImageButton camButton;
    Button nextButton;
    ImageView imageView;
    Uri image_uri;
    private boolean isCameraIconClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cam);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        camButton = findViewById(R.id.circleButton1);
        imageView = findViewById(R.id.imageView1);
        nextButton = findViewById(R.id.circleButton);

        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image_uri != null) {
                    Intent extractIntent = new Intent(Cam.this, Extract.class);
                    extractIntent.putExtra("imageUri", image_uri.toString());
                    startActivity(extractIntent);
                } else {
                    Toast.makeText(Cam.this, "Please capture an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.Camera);
        updateCameraIcon(nav.getMenu().findItem(R.id.Camera));
        nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.About:
                        resetCameraIcon();
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Upload:
                        resetCameraIcon();
                        openGallery();
                        return true;
                    case R.id.Settings:
                        resetCameraIcon();
                        startActivity(new Intent(getApplicationContext(), Instruction.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void updateCameraIcon(MenuItem item) {
        if (isCameraIconClicked) {
            item.setIcon(R.drawable.fcam2);
        } else {
            item.setIcon(R.drawable.fcam1);
        }
    }

    private void resetCameraIcon() {
        isCameraIconClicked = false;
        updateCameraIcon(nav.getMenu().findItem(R.id.Camera));
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camintent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(camintent, CAPTURE_CODE);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showCustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, findViewById(R.id.centeredLayout));

        ImageView logoImageView = layout.findViewById(R.id.logoImageView);
        TextView messageTextView = layout.findViewById(R.id.messageTextView);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 950);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_CODE) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(image_uri);
                showCustomToast();
            } else if (resultCode == RESULT_CANCELED) {
                image_uri = null;
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent galleryIntent = new Intent(this, Gallery.class);
            galleryIntent.setData(data.getData());
            startActivity(galleryIntent);
        }
    }

}