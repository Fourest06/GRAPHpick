package com.example.graphpick;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        ArrayList<String> dataF = intent.getStringArrayListExtra("dataF");
        ArrayList<String> dataS = intent.getStringArrayListExtra("dataS");

        EditText xAxisData1 = findViewById(R.id.xAxisData1);
        EditText xAxisData2 = findViewById(R.id.xAxisData2);
        EditText xAxisData3 = findViewById(R.id.xAxisData3);
        EditText xAxisData4 = findViewById(R.id.xAxisData4);
        EditText xAxisData5 = findViewById(R.id.xAxisData5);

        if (dataF != null && !dataF.isEmpty()) {
            xAxisData1.setText(dataF.size() > 0 ? dataF.get(0) : "");
            xAxisData2.setText(dataF.size() > 1 ? dataF.get(1) : "");
            xAxisData3.setText(dataF.size() > 2 ? dataF.get(2) : "");
            xAxisData4.setText(dataF.size() > 3 ? dataF.get(3) : "");
            xAxisData5.setText(dataF.size() > 4 ? dataF.get(4) : "");
        }

        EditText yAxisData1 = findViewById(R.id.yAxisData1);
        EditText yAxisData2 = findViewById(R.id.yAxisData2);
        EditText yAxisData3 = findViewById(R.id.yAxisData3);
        EditText yAxisData4 = findViewById(R.id.yAxisData4);
        EditText yAxisData5 = findViewById(R.id.yAxisData5);

        if (dataS != null && !dataS.isEmpty()) {
            yAxisData1.setText(dataS.size() > 0 ? dataS.get(0) : "");
            yAxisData2.setText(dataS.size() > 1 ? dataS.get(1) : "");
            yAxisData3.setText(dataS.size() > 2 ? dataS.get(2) : "");
            yAxisData4.setText(dataS.size() > 3 ? dataS.get(3) : "");
            yAxisData5.setText(dataS.size() > 4 ? dataS.get(4) : "");
        }

        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        Button circleButton = findViewById(R.id.circleButton);
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> updatedDataF = new ArrayList<>();
                ArrayList<String> updatedDataS = new ArrayList<>();

                String xAxisValue1 = xAxisData1.getText().toString();
                String yAxisValue1 = yAxisData1.getText().toString();
                String xAxisValue2 = xAxisData2.getText().toString();
                String yAxisValue2 = yAxisData2.getText().toString();
                String xAxisValue3 = xAxisData3.getText().toString();
                String yAxisValue3 = yAxisData3.getText().toString();
                String xAxisValue4 = xAxisData4.getText().toString();
                String yAxisValue4 = yAxisData4.getText().toString();
                String xAxisValue5 = xAxisData5.getText().toString();
                String yAxisValue5 = yAxisData5.getText().toString();

                if ((!xAxisValue1.isEmpty() && yAxisValue1.isEmpty())
                        || (!xAxisValue2.isEmpty() && yAxisValue2.isEmpty())
                        || (!xAxisValue3.isEmpty() && yAxisValue3.isEmpty())
                        || (!xAxisValue4.isEmpty() && yAxisValue4.isEmpty())
                        || (!xAxisValue5.isEmpty() && yAxisValue5.isEmpty())
                ) {
                    Toast.makeText(Edit.this, "Error: Y-axis value should not be empty when X-axis has a value.", Toast.LENGTH_SHORT).show();
                    return;
                }

                updatedDataF.add(xAxisValue1);
                updatedDataF.add(xAxisValue2);
                updatedDataF.add(xAxisValue3);
                updatedDataF.add(xAxisValue4);
                updatedDataF.add(xAxisValue5);

                updatedDataS.add(yAxisValue1);
                updatedDataS.add(yAxisValue2);
                updatedDataS.add(yAxisValue3);
                updatedDataS.add(yAxisValue4);
                updatedDataS.add(yAxisValue5);

                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("updatedDataF", updatedDataF);
                resultIntent.putStringArrayListExtra("updatedDataS", updatedDataS);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}