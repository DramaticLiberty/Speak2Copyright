package theticks.s2t.charts;

import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import theticks.s2t.R;


public class BubbleChart extends SimpleTextChart{
    private BubbleChartView chart;
    private Map<String, List<String>> data;

    public BubbleChart(Map<String, List<String>> data) {
        this.data = data;
        this.layout_id = R.layout.fragment_bubble_chart;
    }

    @Override
    protected View viewSetup(View v) {
        chart = (BubbleChartView) v.findViewById(R.id.bubble_chart);
        chart.setBubbleChartData(this.getData());
        chart.setZoomEnabled(true);
        return v;
    }

    public BubbleChartData getData() {
        List<BubbleValue> chartValues = new ArrayList<BubbleValue>();

        for (int i = 0; i < data.get("year").size(); i++) {
            int year = Integer.parseInt(data.get("year").get(i));
            int numberOfStudies = Integer.parseInt(data.get("number_of_studies").get(i));
            BubbleValue bubbleValue = new BubbleValue(year, numberOfStudies, numberOfStudies);
            bubbleValue.setColor(ChartUtils.pickColor());
            chartValues.add(bubbleValue);
        }

        BubbleChartData bubbleData = new BubbleChartData(chartValues);
        bubbleData.setHasLabels(true);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Year");
        axisY.setName("Number of Studies");
        bubbleData.setAxisXBottom(axisX);
        bubbleData.setAxisYLeft(axisY);
        bubbleData.setValueLabelBackgroundEnabled(false);
        bubbleData.setBubbleScale((float)0.5);
        return bubbleData;

    }
}
