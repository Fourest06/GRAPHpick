package com.example.graphpick;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.inputmethod.InputMethodManager;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.graphics.Color;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.widget.Toast;
import com.github.mikephil.charting.components.YAxis;

public class Bar extends AppCompatActivity {

    BottomNavigationView nav;
    private static final int GALLERY_REQUEST_CODE = 4;
    private BarChart barChart;
    ArrayList<BarEntry> entries1 = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    private View darkOverlay;
    private static final int F1_REQUEST_CODE = 1;
    private static final int F2_REQUEST_CODE = 2;
    private static final int F4_REQUEST_CODE = 5;
    private ImageButton f1Button, f2Button, f3Button, f4Button;
    private ArrayList<String> dataF = new ArrayList<>();
    private ArrayList<String> dataS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        ImageButton arrowLeftButton = findViewById(R.id.backButton);
        Intent intent = getIntent();
        dataF = intent.getStringArrayListExtra("dataF");
        dataS = intent.getStringArrayListExtra("dataS");
        Log.d("ExtractedDataF", Arrays.toString(dataF.toArray()));
        Log.d("ExtractedDataS", Arrays.toString(dataS.toArray()));

        f1Button = findViewById(R.id.f1Button);
        f1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDarkOverlay();
                f1Button.setImageResource(R.drawable.fa1);
                Intent intent = new Intent(Bar.this, Bar_Elements.class);
                startActivityForResult(intent, F1_REQUEST_CODE);
            }
        });

        f2Button = findViewById(R.id.f2Button);
        f2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDarkOverlay();
                f2Button.setImageResource(R.drawable.fa2);
                Intent intent = new Intent(Bar.this, Bar_Color.class);
                startActivityForResult(intent, F2_REQUEST_CODE);
            }
        });

        f3Button = findViewById(R.id.f3Button);
        f3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportChart();
            }
        });

        arrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        f4Button = findViewById(R.id.f4Button);
        f4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDarkOverlay();
                f4Button.setImageResource(R.drawable.fa4);
                Intent intent = new Intent(Bar.this, Edit.class);
                intent.putStringArrayListExtra("dataF", dataF);
                intent.putStringArrayListExtra("dataS", dataS);
                startActivityForResult(intent, F4_REQUEST_CODE);
            }
        });

        for (int i = 0; i < dataF.size(); i++) {
            if (isFloat(dataS.get(i))) {
                float valueS = Float.parseFloat(dataS.get(i));
                entries1.add(new BarEntry(i, valueS, dataF.get(i)));
                labels.add(dataF.get(i));
            } else {
                Log.e("DataConversion", "Error converting value at index " + i);
            }
        }

        BarDataSet dataSet = new BarDataSet(entries1, null);
        dataSet.setColors(
                Color.parseColor("#146C94"),
                Color.parseColor("#19A7CE"),
                Color.parseColor("#AFD3E2"),
                Color.parseColor("#C6E6F3"),
                Color.parseColor("#EBF9FF")
        );
        dataSet.setStackLabels(new String[]{"Data F", "Data S"});
        dataSet.setDrawValues(false);
        BarData barData = new BarData(dataSet);
        barChart = findViewById(R.id.barChart);

        barChart.setData(barData);
        barChart.setDrawValueAboveBar(false);
        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        YAxis leftYAxis = barChart.getAxisLeft();
        leftYAxis.setDrawGridLines(true);
        leftYAxis.setDrawAxisLine(false);
        leftYAxis.setAxisMinimum(0f);

        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);

        barChart.getLegend().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.setExtraBottomOffset(150f);
        barChart.invalidate();

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

    private void exportChart() {
        try {
            View rootView = getWindow().getDecorView().getRootView();
            Bitmap fullScreenBitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(fullScreenBitmap);
            rootView.draw(canvas);

            int centerX = fullScreenBitmap.getWidth() / 2;
            int startY = calculateStartY();
            int endY = calculateEndY();
            int captureWidth = 1500;
            int captureHeight = fullScreenBitmap.getHeight() - startY - endY;
            int cropLeft = Math.max(centerX - (captureWidth / 2), 0);
            int cropTop = startY;
            int cropRight = Math.min(centerX + (captureWidth / 2), fullScreenBitmap.getWidth());
            int cropBottom = Math.min(cropTop + captureHeight, fullScreenBitmap.getHeight() - endY);

            Bitmap croppedBitmap = Bitmap.createBitmap(fullScreenBitmap, cropLeft, cropTop, cropRight - cropLeft, cropBottom - cropTop);
            saveChartImage(croppedBitmap);

            Toast.makeText(this, "Image captured successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ExportChart", "Error capturing image: " + e.getMessage());
            Toast.makeText(this, "Error capturing image", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculateStartY() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenHeight = displayMetrics.heightPixels;

        if (screenHeight >= 1920) {
            return 680;
        }
        else {
            return 400;
        }
    }

    private int calculateEndY() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenHeight = displayMetrics.heightPixels;

        if (screenHeight >= 1920) {
            return 250;
        }
        else {
            return 200;
        }
    }

    private void saveChartImage(Bitmap chartBitmap) throws IOException {
        try {
            File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "bar_chart_" + timeStamp + ".jpeg";

            File imageFile = new File(picturesDir, fileName);

            FileOutputStream fos = new FileOutputStream(imageFile);
            chartBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);

            MediaScannerConnection.scanFile(
                    this,
                    new String[]{imageFile.getAbsolutePath()},
                    null,
                    (path, uri) -> {
                        Log.i("ExportChart", "Scanned " + path + ":");
                        Log.i("ExportChart", "-> uri=" + uri);
                    });

            Intent galleryIntent = new Intent(Intent.ACTION_VIEW);
            galleryIntent.setDataAndType(contentUri, "image/jpeg");
            galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    galleryIntent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.flogo1)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .setBigContentTitle("Bar Graph")
                            .bigText("Image captured successfully"))
                    .setColor(Color.parseColor("#2C9CA6"))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.notify(1,builder.build());

        } catch (Exception e) {
            Log.e("ExportChart", "Error capturing image: " + e.getMessage());
        }
    }

    private void addDarkOverlay() {
        if (darkOverlay == null) {
            darkOverlay = new View(this);
            darkOverlay.setBackgroundColor(Color.parseColor("#10FFFFFF"));
            darkOverlay.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            getWindow().addContentView(darkOverlay, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            darkOverlay.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    removeDarkOverlay();
                    return true;
                }
            });
        }
    }

    private void removeDarkOverlay() {
        if (darkOverlay != null) {
            ((ViewGroup) darkOverlay.getParent()).removeView(darkOverlay);
            darkOverlay = null;
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
        if (requestCode == F1_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                removeDarkOverlay();
                f1Button.setImageResource(R.drawable.f1);
            }
        }
        if (requestCode == F2_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                removeDarkOverlay();
                f2Button.setImageResource(R.drawable.f2);
            }
        }
        if (requestCode == F4_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                removeDarkOverlay();
                f4Button.setImageResource(R.drawable.f4);
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent galleryIntent = new Intent(this, Gallery.class);
            galleryIntent.setData(data.getData());
            startActivity(galleryIntent);
        }
        if (requestCode == F1_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean checkbox1State = data.getBooleanExtra("checkbox1State", false);
            boolean checkbox2State = data.getBooleanExtra("checkbox2State", false);
            boolean checkbox3State = data.getBooleanExtra("checkbox3State", false);
            boolean checkbox4State = data.getBooleanExtra("checkbox4State", false);

            EditText chartTitleEditText = findViewById(R.id.chartTitleEditText);
            EditText axisTitle1 = findViewById(R.id.firstEditText);
            EditText axisTitle2 = findViewById(R.id.secondEditText);
            chartTitleEditText.setText("");
            axisTitle1.setText("");
            axisTitle2.setText("");

            if (checkbox1State) {
                axisTitle1.setVisibility(View.VISIBLE);
                axisTitle2.setVisibility(View.VISIBLE);
            } else {
                axisTitle1.setVisibility(View.INVISIBLE);
                axisTitle2.setVisibility(View.INVISIBLE);
            }
            if (checkbox2State) {
                chartTitleEditText.setVisibility(View.VISIBLE);
            } else {
                chartTitleEditText.setVisibility(View.INVISIBLE);
            }
            if (checkbox3State) {
                updateBarChart1(true);
            } else {
                updateBarChart1(false);
            }
            if (checkbox4State) {
                updateBarChart2(true);
            } else {
                updateBarChart2(false);
            }
            removeDarkOverlay();
            f1Button.setImageResource(R.drawable.f1);
        }
        if (requestCode == F2_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] selectedColors = data.getStringArrayExtra("selectedColors");
            updateBarChart(selectedColors);
            removeDarkOverlay();
            f2Button.setImageResource(R.drawable.f2);
        }
        if (requestCode == F4_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> updatedDataF = data.getStringArrayListExtra("updatedDataF");
            ArrayList<String> updatedDataS = data.getStringArrayListExtra("updatedDataS");
            removeDarkOverlay();
            f4Button.setImageResource(R.drawable.f4);
            dataF = updatedDataF;
            dataS = updatedDataS;
            updateBarChartWithData(updatedDataF, updatedDataS);
        }
    }

    private void updateBarChartWithData(ArrayList<String> updatedDataF, ArrayList<String> updatedDataS) {
        entries1.clear();
        labels.clear();

        for (int i = 0; i < updatedDataF.size(); i++) {
            if (isFloat(updatedDataS.get(i))) {
                float valueS = Float.parseFloat(updatedDataS.get(i));
                entries1.add(new BarEntry(i, valueS, updatedDataF.get(i)));
                labels.add(updatedDataF.get(i));
            } else {
                Log.e("DataConversion", "Error converting value at index " + i);
            }
        }

        BarDataSet dataSet = new BarDataSet(entries1, null);
        dataSet.setColors(
                Color.parseColor("#146C94"),
                Color.parseColor("#19A7CE"),
                Color.parseColor("#AFD3E2"),
                Color.parseColor("#C6E6F3"),
                Color.parseColor("#EBF9FF")
        );
        dataSet.setStackLabels(new String[]{"Data F", "Data S"});
        dataSet.setDrawValues(false);
        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);
        barChart.getLegend().setEnabled(false);
        barChart.invalidate();
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

    private boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void updateBarChart(String[] selectedColors) {
        barChart = findViewById(R.id.barChart);
        BarData barData = barChart.getData();
        BarDataSet dataSet = (BarDataSet) barData.getDataSetByIndex(0);

        ArrayList<Integer> colorList = new ArrayList<>();
        for (String color : selectedColors) {
            colorList.add(Color.parseColor(color));
        }
        dataSet.setColors(colorList);

        Legend legend = barChart.getLegend();
        List<LegendEntry> legendEntries = new ArrayList<>();
        List<BarEntry> dataSetEntries = dataSet.getValues();
        List<String> labels = getLabelsFromBarEntries(dataSetEntries);

        int loopSize = Math.min(colorList.size(), dataSetEntries.size());

        for (int i = 0; i < loopSize; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorList.get(i);
            entry.label = labels.get(i);
            entry.formSize = 8f;
            legendEntries.add(entry);
        }

        legend.setCustom(legendEntries);
        legend.setYOffset(-40f);
        legend.setXOffset(20f);
        legend.setTextSize(14f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        barChart.invalidate();
    }

    private List<String> getLabelsFromBarEntries(List<BarEntry> barEntries) {
        List<String> labels = new ArrayList<>();
        for (BarEntry barEntry : barEntries) {
            labels.add((String) barEntry.getData());
        }
        return labels;
    }

    private void updateBarChart1(boolean drawValues) {
        barChart = findViewById(R.id.barChart);
        BarData barData = barChart.getData();
        BarDataSet dataSet = (BarDataSet) barData.getDataSetByIndex(0);

        barChart.setDrawValueAboveBar(true);
        dataSet.setValueTextSize(15f);
        dataSet.setDrawValues(drawValues);

        if (drawValues) {
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (value == (int) value) {
                        return String.valueOf((int) value);
                    } else {
                        return String.valueOf(value);
                    }
                }
            });
        } else {
            dataSet.setValueFormatter(null);
        }

        barChart.invalidate();
    }

    private void updateBarChart2(boolean showLegend) {
        BarChart barChart = findViewById(R.id.barChart);

        Legend legend = barChart.getLegend();

        BarData barData = barChart.getData();
        BarDataSet dataSet = (BarDataSet) barData.getDataSetByIndex(0);
        List<Integer> dataSetColors = dataSet.getColors();

        List<LegendEntry> legendEntries = new ArrayList<>();
        int loopSize = Math.min(dataSetColors.size(), labels.size());

        for (int i = 0; i < loopSize; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = dataSetColors.get(i);
            entry.label = labels.get(i);
            entry.formSize = 8f;
            legendEntries.add(entry);
        }

        legend.setCustom(legendEntries);
        legend.setYOffset(-40f);
        legend.setXOffset(20f);
        legend.setTextSize(14f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setEnabled(showLegend);
        barChart.invalidate();
    }

}