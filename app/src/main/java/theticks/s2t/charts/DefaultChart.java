package theticks.s2t.charts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.R;

public class DefaultChart extends SimpleTextChart {

    public DefaultChart() {
        super.layout_id = R.layout.fragment_default_text;
    }
}
