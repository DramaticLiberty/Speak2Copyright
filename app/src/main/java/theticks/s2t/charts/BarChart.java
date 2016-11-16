package theticks.s2t.charts;

import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import theticks.s2t.R;


public class BarChart extends SimpleTextChart {
    private ColumnChartView chart;
    private Map<String, List<String>> data;

    public BarChart(Map<String, List<String>> data) {
        this.data = data;
        this.layout_id = R.layout.fragment_bar_chart;
    }

    @Override
    protected View viewSetup(View v) {
        chart = (ColumnChartView) v.findViewById(R.id.bar_chart);
        chart.setColumnChartData(this.getData());
        chart.setZoomEnabled(true);
        return v;
    }

    public ColumnChartData getData() {
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < data.get("year").size(); i++) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < 1; ++j) {
                values.add(new SubcolumnValue(Integer.parseInt(data.get("number_of_studies").get(i)), ChartUtils.pickColor()));
            }
            axisValues.add(new AxisValue(i).setLabel(data.get("year").get(i)));
            columns.add(new Column(values));
        }

        ColumnChartData barData = new ColumnChartData(columns);
        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Year");
        axisY.setName("Number of Studies");
        barData.setAxisXBottom(axisX);
        barData.setAxisYLeft(axisY);
        barData.setValueLabelBackgroundEnabled(false);
        return barData;

    }
}
