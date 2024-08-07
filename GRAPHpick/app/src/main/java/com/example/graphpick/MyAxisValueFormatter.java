package com.example.graphpick;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class MyAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {

    private final ArrayList<String> labels;

    public MyAxisValueFormatter(ArrayList<String> labels) {
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // Check if the value is a valid index for your labels
        int index = (int) value;
        if (index >= 0 && index < labels.size()) {
            return labels.get(index);
        } else {
            return ""; // Return an empty string for invalid indices
        }
    }
}
