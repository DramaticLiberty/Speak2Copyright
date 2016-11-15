package theticks.s2t.charts;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import theticks.s2t.R;


public class ListChart extends SimpleTextChart{
    private BubbleChartView chart;
    private String response;
    private String responseDetails;
    private Map<String, List<String>> data;

    public ListChart(String response, String responseDetails, Map<String, List<String>> data) {
        this.response = response;
        this.responseDetails = responseDetails;
        this.data = data;
        this.layout_id = R.layout.fragment_list;
    }

    @Override
    protected View viewSetup(View v) {
        updateElement(v, R.id.response, response);
        updateElement(v, R.id.responseDetails, responseDetails);
        updateElement(v, R.id.firstElement, "1. " + data.get("title").get(0));
        updateElement(v, R.id.secondElement, "2. " + data.get("title").get(1));
        updateElement(v, R.id.thirdElement, "3. " + data.get("title").get(2));
        return v;
    }

    private void updateElement(View v, int id, String text) {
        TextView element = (TextView) v.findViewById(id);
        element.setText(text);
        element.setVisibility(View.VISIBLE);
    }
}
