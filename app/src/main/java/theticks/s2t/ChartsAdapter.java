package theticks.s2t;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import theticks.s2t.charts.MapChart;
import theticks.s2t.charts.PieChart;
import theticks.s2t.charts.SimpleTextChart;

/**
 * Created by Mihai Balint on 11/15/16.
 */

public class ChartsAdapter extends BaseAdapter {

    private List<IChart> charts;
    private Map<Class, Integer> chartTypes;
    private Context context;
    private DatabaseAccess databaseAccess;

    public ChartsAdapter(Context context) {
        this.chartTypes = new HashMap<Class, Integer>();
        this.charts = new ArrayList<>();
        this.context = context;

        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
    }

    public void append(IChart chart) {
        chart.setDatabaseAccess(databaseAccess);
        charts.add(chart);
        if (!chartTypes.containsKey(chart.getClass()))
            chartTypes.put(chart.getClass(), chartTypes.size());
        notifyDataSetChanged();
    }

    public void append2() {
//        append(new MapChart());
//        append(new PieChart());
//        append(new SimpleTextChart());
    }

    @Override
    public int getCount() {
        return charts.size();
    }

    @Override
    public Object getItem(int position) {
        return charts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IChart c = (IChart) getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return c.createView(inflater, parent);
        }
        return c.convertView(convertView, parent);
    }

    @Override
    public int getViewTypeCount() {
        return Math.max(1, chartTypes.size());
    }

    @Override
    public int getItemViewType(int position) {
        return chartTypes.get(charts.get(position).getClass());
    }
}
