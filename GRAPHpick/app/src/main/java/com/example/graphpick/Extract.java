package com.example.graphpick;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import java.util.Arrays;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.graphics.Bitmap;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import android.util.Log;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.OutputStream;

public class Extract extends AppCompatActivity {

    public String DATA_PATH;
    public final String language = "eng";
    private static final String TAG = "Extract.java";
    BottomNavigationView nav;
    private int currentRequestCode;
    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int DATA_F_REQUEST_CODE = 3;
    private static final int DATA_S_REQUEST_CODE = 4;
    private Uri imageUri;
    private List<String> extractedDataF = new ArrayList<>();
    private List<String> extractedDataS = new ArrayList<>();
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_extract);

        Log.d("Hello", "Hi");
        extractedDataF = new ArrayList<>();
        extractedDataS = new ArrayList<>();
        imageView1 = findViewById(R.id.imageView1);
        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        Button dataF = findViewById(R.id.dataf);
        Button dataS = findViewById(R.id.datas);
        Button graph = findViewById(R.id.circleButton);
        DATA_PATH = getFilesDir().getAbsolutePath() + "/GRAPHpick/";

        String[] paths = new String[]{DATA_PATH, DATA_PATH + "tessdata/"};
        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) { // Check if the directory doesn't exist
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on internal data directory failed");
                    return; // You should return immediately if directory creation fails.
                } else {
                    Log.v(TAG, "Created directory " + path + " on internal data directory");
                }
            } else {
                Log.v(TAG, "Directory " + path + " already exists on internal data directory");
            }

            if (!(new File(DATA_PATH + "tessdata/" + language + ".traineddata")).exists()) {
                try {
                    AssetManager assetManager = getAssets();
                    InputStream in = assetManager.open("tessdata/" + language + ".traineddata");
                    OutputStream out = new FileOutputStream(DATA_PATH + "tessdata/" + language + ".traineddata");

                    // Transfer bytes from 'in' to 'out'
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    Log.v(TAG, "Copied " + language + " traineddata");
                } catch (IOException e) {
                    Log.e(TAG, "Was unable to copy " + language + " traineddata: " + e.toString());
                }
            }
        }

        String imageUriString = getIntent().getStringExtra("imageUri");
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            imageView1.setImageURI(imageUri);
            imageView1.bringToFront();
        }

        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dataF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRequestCode = DATA_F_REQUEST_CODE;
                startImageCrop();
            }
        });

        dataS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRequestCode = DATA_S_REQUEST_CODE;
                startImageCrop();
            }
        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DataF Size", String.valueOf(extractedDataF.size()));
                Log.d("DataS Size", String.valueOf(extractedDataS.size()));
                if (!extractedDataF.isEmpty() && !extractedDataS.isEmpty()) {
                    if (extractedDataF.size() == extractedDataS.size()) {
                        Intent mainMenuIntent = new Intent(Extract.this, Graph.class);
                        mainMenuIntent.putStringArrayListExtra("dataF", (ArrayList<String>) extractedDataF);
                        mainMenuIntent.putStringArrayListExtra("dataS", (ArrayList<String>) extractedDataS);
                        startActivity(mainMenuIntent);
                    } else {
                        Toast.makeText(Extract.this, "Data size is not equal. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Extract.this, "Please extract data first.", Toast.LENGTH_SHORT).show();
                }
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

    private void startImageCrop() {
        Log.d("startImageCrop", "startImageCrop called");
        if (currentRequestCode == DATA_F_REQUEST_CODE) {
            extractedDataF.clear();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.OFF)
                    .setActivityTitle("Crop Image")
                    .start(this);
        } else if (currentRequestCode == DATA_S_REQUEST_CODE) {
            extractedDataS.clear();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.OFF)
                    .setActivityTitle("Crop Image")
                    .start(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "onActivityResult called " + currentRequestCode );

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent galleryIntent = new Intent(this, Gallery.class);
            galleryIntent.setData(data.getData());
            startActivity(galleryIntent);
        }
        else if (resultCode == RESULT_OK && (currentRequestCode  == DATA_F_REQUEST_CODE || currentRequestCode  == DATA_S_REQUEST_CODE)) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (result != null && result.getUri() != null) {
                Uri croppedImageUri = result.getUri();

                TessBaseAPI tessBaseAPI = new TessBaseAPI();
                tessBaseAPI.setDebug(true);
                tessBaseAPI.init(DATA_PATH, language);
                tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);

                try {
                    Bitmap bitmap = getBitmapFromUri(croppedImageUri);
                    if (bitmap != null) {
                        File imageFile = convertBitmapToFile(bitmap);
                        tessBaseAPI.setImage(imageFile);
                        String extractedText = tessBaseAPI.getUTF8Text().trim();
                        if (!extractedText.isEmpty()) {
                            String[] lines = extractedText.split("\n");
                            processAndDisplayData(lines, extractedDataF, extractedDataS);
                        } else {
                            Log.d("OCR Result", "No text found in the image");
                            Toast.makeText(Extract.this, "No text found in the image", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace(); // Log the exception for debugging
                    Toast.makeText(Extract.this, "Error processing the image", Toast.LENGTH_SHORT).show();
                }
                finally {
                    tessBaseAPI.end(); // Make sure to end the TessBaseAPI
                }
            } else {
                Log.e("onActivityResult", "Invalid result or URI");
                Toast.makeText(Extract.this, "Error in image cropping", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File convertBitmapToFile(Bitmap bitmap) throws IOException {
        Log.d("convertBitmapToFile", "convertBitmapToFile");
        File cacheDir = getCacheDir();
        File imageFile = new File(cacheDir, "image_for_ocr.jpg");

        FileOutputStream fos = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();

        return imageFile;
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        Log.d("getBitmapFromUri", "getBitmapFromUri");
        try {
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void processAndDisplayData(String[] lines, List<String> extractedDataListF, List<String> extractedDataListS) {
        for (String line : lines) {
            String cleanedValue = cleanValue(line);
            if (cleanedValue != null && !cleanedValue.isEmpty()) {
                if (currentRequestCode == DATA_F_REQUEST_CODE) {
                    if (extractedDataListF.size() + 1 <= 5) {
                        extractedDataListF.add(cleanedValue);
                        Log.d("ExtractedDataF", cleanedValue);
                    } else {
                        Toast.makeText(Extract.this, "Data size exceeds the limit of 5. Try Again.", Toast.LENGTH_SHORT).show();
                    }
                } else if (currentRequestCode == DATA_S_REQUEST_CODE) {
                    if (extractedDataListS.size() + 1 <= 5) {
                        extractedDataListS.add(cleanedValue);
                        Log.d("ExtractedDataS", cleanedValue);
                    } else {
                        Toast.makeText(Extract.this, "Data size exceeds the limit of 5. Try Again.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            } else {
                Toast.makeText(Extract.this, "Error: Null value detected.", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private String cleanValue(String value) {
        if (value != null) {
            String cleanedValue = value.trim().replaceAll("[^a-zA-Z0-9._-]+", "");
            return isNumeric(cleanedValue) ? cleanedValue : value;
        } else {
            return null;
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

}