package theticks.s2t.charts;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import theticks.s2t.R;


public class PieChart  extends SimpleTextChart {

    private PieChartView chart;
    private Map<String, List<String>> data;
    private TextView selectedSlice;

    private List<Integer> colors = new ArrayList<>();


    public PieChart(Map<String, List<String>> data) {
        this.data = data;
        this.layout_id = R.layout.fragment_pie_chart;
        colors.add(Color.parseColor("#c62828"));
        colors.add(Color.parseColor("#ad1457"));
        colors.add(Color.parseColor("#6a1b9a"));
        colors.add(Color.parseColor("#4527a0"));
        colors.add(Color.parseColor("#283593"));
        colors.add(Color.parseColor("#1565c0"));
        colors.add(Color.parseColor("#0277bd"));
        colors.add(Color.parseColor("#00838f"));
    }

    @Override
    protected View viewSetup(View v) {
        chart = (PieChartView) v.findViewById(R.id.pie_chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        chart.setPieChartData(this.getData());
        chart.setValueSelectionEnabled(true);
        selectedSlice = (TextView) v.findViewById(R.id.selected_slice);
        return v;
    }

    private int allStudies() {
        int sum = 0;
        for (int i = 0; i < data.get("country").size(); i++) {
            sum += Integer.parseInt(data.get("number_of_studies").get(i));
        }
        return sum;
    }

    public PieChartData getData() {
        List<SliceValue> chartValues = new ArrayList<SliceValue>();

        int studies = allStudies();
        for (int i = 0; i < data.get("country").size(); i++) {
            float numberOfStudies = Float.parseFloat(data.get("number_of_studies").get(i));
            float percentStudies = numberOfStudies/studies * 100;
            SliceValue sliceValue = new SliceValue(percentStudies, colors.get(i));
            sliceValue.setLabel(Integer.toString((int)percentStudies)+ '%');
            chartValues.add(sliceValue);
        }
        PieChartData pieData = new PieChartData(chartValues);
        pieData.setHasLabels(true);
        pieData.setValueLabelBackgroundEnabled(false);
        pieData.setHasCenterCircle(false);
        return pieData;
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        private SliceValue value;

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            String name = data.get("country").get(arcIndex);
            if(this.value != null) {
                selectedSlice.setText("");
            }
            this.value = value;
            selectedSlice.setText(name);
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub
            selectedSlice.setText("");
        }
    }
}
