package theticks.s2t.charts;

import android.view.View;
import android.widget.TextView;

import theticks.s2t.R;

public class TextChart extends SimpleTextChart {

    private String text;

    public TextChart(String text) {
        this.text = text;
        super.layout_id = R.layout.fragment_default_text;
    }

    @Override
    protected View viewSetup(View v) {
        TextView element = (TextView) v.findViewById(R.id.default_text);
        element.setText(text);
        return v;
    }
}
