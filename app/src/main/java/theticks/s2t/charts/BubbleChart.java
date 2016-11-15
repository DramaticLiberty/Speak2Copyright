package theticks.s2t.charts;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import theticks.s2t.R;

/**
 * Created by rpadurariu on 15.11.2016.
 */

public class BubbleChart extends SimpleTextChart{


    private BubbleChartView chart;

    public BubbleChart() {
        this.layout_id = R.layout.fragment_bubble_chart;
    }

    @Override
    protected View viewSetup(View v) {
        chart = (BubbleChartView) v.findViewById(R.id.bubble_chart);
        chart.setBubbleChartData(this.getData());
        return v;
    }

    public BubbleChartData getData() {
        BubbleChartData data;
        List<BubbleValue> chartValues = new ArrayList<BubbleValue>();

        for(int i=0; i<10; i++) {
            float v = (float) Math.random() * 100;
            float a = (float) Math.random() * 1000;
            BubbleValue bubbleValue = new BubbleValue(v, a, a);
            bubbleValue.setColor(ChartUtils.pickColor());
            chartValues.add(bubbleValue);
        }

        data = new BubbleChartData(chartValues);
        data.setHasLabels(true);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Year");
        axisY.setName("Number of Studies");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setValueLabelBackgroundEnabled(false);
        return data;

    }
}
