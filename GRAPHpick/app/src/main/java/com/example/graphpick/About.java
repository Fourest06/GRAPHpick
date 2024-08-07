package com.example.graphpick;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.provider.MediaStore;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageButton;

public class About extends AppCompatActivity {

    BottomNavigationView nav;
    TextView textView;
    private static final int GALLERY_REQUEST_CODE = 2;
    private boolean isAboutIconClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView = findViewById(R.id.txtAbout);
        String originalText = "Welcome to GRAPHpick! We're a team of researchers and developers dedicated to transforming graph selection. Our mobile app, driven by cutting-edge OCR technology, simplifies analysis for professionals, students, and academics. Say goodbye to tedious workflows and embrace efficient, data-driven decisions with GRAPHpick. Explore the future of graph selection – join us on this exciting journey!";
        String hexColor1 = "#1F818A";

        SpannableString spannableString = new SpannableString(originalText);

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(hexColor1));
        Typeface montExtraBold = Typeface.createFromAsset(getAssets(), "montextrabold.otf");

        spannableString.setSpan(boldSpan,11,20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan,11,20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new About.CustomTypefaceSpan("", montExtraBold), 11, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);

        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.About);
        updateAboutIcon(nav.getMenu().findItem(R.id.About));
        nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Settings:
                        resetAboutIcon();
                        startActivity(new Intent(getApplicationContext(), Instruction.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Upload:
                        resetAboutIcon();
                        openGallery();
                        return true;
                    case R.id.Camera:
                        resetAboutIcon();
                        startActivity(new Intent(getApplicationContext(), Cam.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void resetAboutIcon() {
        isAboutIconClicked = false;
        updateAboutIcon(nav.getMenu().findItem(R.id.About));
    }

    private void updateAboutIcon(MenuItem item) {
        if (isAboutIconClicked) {
            item.setIcon(R.drawable.fabout2);
        } else {
            item.setIcon(R.drawable.fabout1);
        }
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

    private class CustomTypefaceSpan extends MetricAffectingSpan {
        private final Typeface typeface;

        public CustomTypefaceSpan(String family, Typeface typeface) {
            this.typeface = typeface;
        }

        @Override
        public void updateMeasureState(TextPaint p) {
            p.setTypeface(typeface);
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setTypeface(typeface);
        }
    }

}