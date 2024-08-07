package com.example.graphpick;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.CheckBox;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Bar_Elements extends AppCompatActivity {

    private static final String PREFS_NAME = "FbarPrefs";
    private static final String CHECKBOX_STATE_KEY_1 = "checkboxState1_fbar";
    private static final String CHECKBOX_STATE_KEY_2 = "checkboxState2_fbar";
    private static final String CHECKBOX_STATE_KEY_3 = "checkboxState3_fbar";
    private static final String CHECKBOX_STATE_KEY_4 = "checkboxState4_fbar";
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_elements);

        Log.d("f1", "onCreate: Activity created");
        checkBox1 = findViewById(R.id.f1check);
        checkBox2 = findViewById(R.id.f2check);
        checkBox3 = findViewById(R.id.f3check);
        checkBox4 = findViewById(R.id.f4check);
        loadCheckboxState();

        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressed = true;
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        Button circleButton = findViewById(R.id.circleButton);
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressed = false;
                saveCheckboxState();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("checkbox1State", checkBox1.isChecked());
                resultIntent.putExtra("checkbox2State", checkBox2.isChecked());
                resultIntent.putExtra("checkbox3State", checkBox3.isChecked());
                resultIntent.putExtra("checkbox4State", checkBox4.isChecked());
                setResult(RESULT_OK, resultIntent);

                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCheckboxState();
    }

    private void clearCheckboxState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(CHECKBOX_STATE_KEY_1);
        editor.remove(CHECKBOX_STATE_KEY_2);
        editor.remove(CHECKBOX_STATE_KEY_3);
        editor.remove(CHECKBOX_STATE_KEY_4);
        editor.apply();
    }

    private void saveCheckboxState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CHECKBOX_STATE_KEY_1, checkBox1.isChecked());
        editor.putBoolean(CHECKBOX_STATE_KEY_2, checkBox2.isChecked());
        editor.putBoolean(CHECKBOX_STATE_KEY_3, checkBox3.isChecked());
        editor.putBoolean(CHECKBOX_STATE_KEY_4, checkBox4.isChecked());
        editor.apply();
    }

    private void loadCheckboxState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isChecked1 = prefs.getBoolean(CHECKBOX_STATE_KEY_1, checkBox1.isChecked());
        boolean isChecked2 = prefs.getBoolean(CHECKBOX_STATE_KEY_2, checkBox2.isChecked());
        boolean isChecked3 = prefs.getBoolean(CHECKBOX_STATE_KEY_3, checkBox3.isChecked());
        boolean isChecked4 = prefs.getBoolean(CHECKBOX_STATE_KEY_4, checkBox4.isChecked());
        checkBox1.setChecked(isChecked1);
        checkBox2.setChecked(isChecked2);
        checkBox3.setChecked(isChecked3);
        checkBox4.setChecked(isChecked4);
    }

}