package theticks.s2t.charts;

import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import theticks.s2t.R;


public class PieChart  extends SimpleTextChart {

    private PieChartView chart;
    private Map<String, List<String>> data;

    public PieChart(Map<String, List<String>> data) {
        this.data = data;
        this.layout_id = R.layout.fragment_pie_chart;
    }

    @Override
    protected View viewSetup(View v) {
        chart = (PieChartView) v.findViewById(R.id.pie_chart);
        chart.setPieChartData(this.getData());
        return v;
    }

    public PieChartData getData() {
        List<SliceValue> chartValues = new ArrayList<SliceValue>();

        for (int i = 0; i < data.get("country").size(); i++) {
            int numberOfStudies = Integer.parseInt(data.get("number_of_studies").get(i));
            SliceValue sliceValue = new SliceValue(numberOfStudies, ChartUtils.pickColor());
            sliceValue.setLabel(data.get("country").get(i)+":"+numberOfStudies);
            chartValues.add(sliceValue);
        }
        PieChartData pieData = new PieChartData(chartValues);
//        pieData.setHasLabels(true);
        pieData.setHasCenterCircle(false);
        return pieData;
    }
}
