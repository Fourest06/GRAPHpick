package com.example.graphpick;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.MetricAffectingSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Instruction extends AppCompatActivity {

    BottomNavigationView nav;
    TextView textView, textView1, textView2, textView3;
    private static final int GALLERY_REQUEST_CODE = 2;
    private boolean isSettingIconClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_instruction);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        String originalText = "Choose or Take a Picture: \n\nA. To snap a picture with the app, click the camera icon.\n\nB. Click the capture button with the camera of your smartphone centered on the data table that needs to be graphed.\n\nC. If shooting a picture is not what you want, choose an image from your device's gallery by hitting the upload icon.\n\nD. Navigate through your gallery until you choose the picture you want to use to graph.";
        String wordToColor1 = "Choose or Take a Picture:";
        String originalText1 = "Data Selection:\n\nA. After you've taken or selected an image, highlight the data you would like to include on your graph.\n\nB. When you have finished selecting the data, click done.";
        String wordToColor2 = "Data Selection:";
        String originalText2 = "Graph Selection:\n\nA. You will select the type of graph you want to use to represent your data, such as a bar, line, or pie, once the data selection is complete.";
        String wordToColor3 = "Graph Selection:";
        String originalText3 = "Editing and Exporting Graphs:\n\nA. The graph you choose to represent your data will appear after the selection of graphs is complete.\n\nB. To add some elements to your graph and make it easier to interpret, tap the plus button.\n\nC. If you wish to alter the graph's color, tap the color palette button.\n\nD. To export the graph, tap the save as button.";
        String wordToColor4 = "Editing and Exporting Graphs:";
        String hexColor1 = "#246166";

        int startIndex1 = originalText.indexOf(wordToColor1);
        int startIndex2 = originalText1.indexOf(wordToColor2);
        int startIndex3 = originalText2.indexOf(wordToColor3);
        int startIndex4 = originalText3.indexOf(wordToColor4);

        SpannableString spannableString = new SpannableString(originalText);
        SpannableString spannableString1 = new SpannableString(originalText1);
        SpannableString spannableString2 = new SpannableString(originalText2);
        SpannableString spannableString3 = new SpannableString(originalText3);

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

        spannableString.setSpan(boldSpan, startIndex1, startIndex1 + wordToColor1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(boldSpan, startIndex2, startIndex2 + wordToColor2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(boldSpan, startIndex3, startIndex3 + wordToColor3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString3.setSpan(boldSpan, startIndex4, startIndex4 + wordToColor4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor(hexColor1));
        spannableString.setSpan(colorSpan1, startIndex1, startIndex1 + wordToColor1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor(hexColor1));
        spannableString1.setSpan(colorSpan2, startIndex2, startIndex2 + wordToColor2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan3 = new ForegroundColorSpan(Color.parseColor(hexColor1));
        spannableString2.setSpan(colorSpan3, startIndex3, startIndex3 + wordToColor3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan4 = new ForegroundColorSpan(Color.parseColor(hexColor1));
        spannableString3.setSpan(colorSpan4, startIndex4, startIndex4 + wordToColor4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Typeface montBold = Typeface.createFromAsset(getAssets(), "montbold.otf");
        spannableString.setSpan(new Instruction.CustomTypefaceSpan("", montBold), startIndex1, startIndex1 + wordToColor1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new Instruction.CustomTypefaceSpan("", montBold), startIndex2, startIndex2 + wordToColor2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new Instruction.CustomTypefaceSpan("", montBold), startIndex3, startIndex3 + wordToColor3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString3.setSpan(new Instruction.CustomTypefaceSpan("", montBold), startIndex4, startIndex4 + wordToColor4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView1.setText(spannableString1);
        textView2.setText(spannableString2);
        textView3.setText(spannableString3);

        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.Settings);
        updateSettingIcon(nav.getMenu().findItem(R.id.Settings));
        nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.About:
                        resetSettingIcon();
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Upload:
                        resetSettingIcon();
                        openGallery();
                        return true;
                    case R.id.Camera:
                        resetSettingIcon();
                        startActivity(new Intent(getApplicationContext(), Cam.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void resetSettingIcon() {
        isSettingIconClicked = false;
        updateSettingIcon(nav.getMenu().findItem(R.id.Settings));
    }

    private void updateSettingIcon(MenuItem item) {
        if (isSettingIconClicked) {
            item.setIcon(R.drawable.finstruction2);
        } else {
            item.setIcon(R.drawable.finstruction1);
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
