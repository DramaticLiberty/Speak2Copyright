package theticks.s2t.charts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import theticks.s2t.ChartPoint;
import theticks.s2t.R;

/**
 * Created by rpadurariu on 15.11.2016.
 */

public class PieChart  extends SimpleTextChart {

    private PieChartView chart;
    private List<ChartPoint> studies;

    public PieChart() {
        this.layout_id = R.layout.fragment_pie_chart;
    }

    @Override
    protected View viewSetup(View v) {
        studies = databaseAccess.getNumberOfStudiesByCountry();

        chart = (PieChartView) v.findViewById(R.id.pie_chart);
        chart.setPieChartData(this.getData());
        return v;
    }

    public PieChartData getData() {
        PieChartData data;
        List<SliceValue> chartValues = new ArrayList<SliceValue>();

        for(ChartPoint value: this.studies) {
            SliceValue sliceValue = new SliceValue(value.getValue(), ChartUtils.pickColor());
//            sliceValue.setLabel(value.getName() + ": " + value.getValue());
            chartValues.add(sliceValue);
        }

        data = new PieChartData(chartValues);
        data.setHasLabels(true);
        data.setHasCenterCircle(true);

        return data;
    }
}
