package theticks.s2t.charts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.R;


public class SimpleTextChart implements IChart {

    protected int layout_id = R.layout.fragment_simple_text;
    protected DatabaseAccess databaseAccess;

    @Deprecated()
    public SimpleTextChart() {
    }

    public SimpleTextChart(int layout_id) {
        this.layout_id = layout_id;
    }

    @Override
    public final void setDatabaseAccess(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    protected View viewSetup(View v) {
        return v;
    }

    private final View safeViewSetup(View v) {
        try {
            return viewSetup(v);
        } catch(Exception e) {
            e.printStackTrace();
            return v;
        }
    }

    @Override
    public final View createView(LayoutInflater inflater, ViewGroup parent) {
        return safeViewSetup(inflater.inflate(layout_id, null));
    }

    @Override
    public final View convertView(View convertView, ViewGroup parent) {
        return safeViewSetup(convertView);
    }
}
