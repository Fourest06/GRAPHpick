package com.example.graphpick;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spannable;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.graphics.Typeface;
import android.text.style.StyleSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=2500;

    ImageView imageView;
    TextView textView, textView1;
    Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView1);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);

        Typeface montExtraBold = Typeface.createFromAsset(getAssets(), "montextrabold.otf");
        Typeface montSemiBold = Typeface.createFromAsset(getAssets(), "montsemibold.otf");

        SpannableString spannableString = new SpannableString("GRAPHpick");

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 5, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new CustomTypefaceSpan("", montExtraBold), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new CustomTypefaceSpan("", montSemiBold), 5, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);

        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        imageView.setAnimation(bottom);
        textView.setAnimation(top);
        textView1.setAnimation(top);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
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