package theticks.s2t;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

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

public class ChartsAdapter implements ListAdapter {

    private List<IChart> charts;
    private Map<Class, Integer> chartTypes;
    private Context context;
    private DatabaseAccess databaseAccess;
    private final DataSetObservable mDataSetObservable;

    public ChartsAdapter(Context context) {
        this. mDataSetObservable = new DataSetObservable();
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
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        System.out.println("\ngetView " + position + "/" +getCount() + " " + convertView + " type = " + type+"\n");

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
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return chartTypes.get(charts.get(position).getClass());
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }


    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

}
