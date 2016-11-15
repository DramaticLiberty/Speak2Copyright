package theticks.s2t;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import theticks.s2t.charts.MapChart;
import theticks.s2t.charts.PieChart;
import theticks.s2t.charts.SimpleText;

/**
 * Created by Mihai Balint on 11/15/16.
 */

public class ChartsAdapter extends BaseAdapter {

    private List<IChart> charts;
    private Context context;
    private DatabaseAccess databaseAccess;

    public ChartsAdapter(Context context) {
        this.charts = new ArrayList<>();
        this.context = context;

        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
    }

    public void append(IChart chart) {
        chart.setDatabaseAccess(databaseAccess);
        charts.add(chart);
        notifyDataSetChanged();
    }

    public void append2() {
        append(new MapChart());
        append(new PieChart());
        append(new SimpleText());
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
}
