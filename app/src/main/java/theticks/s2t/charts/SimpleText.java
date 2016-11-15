package theticks.s2t.charts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.R;

/**
 * Created by Mihai Balint on 11/15/16.
 */

public class SimpleText implements IChart {

    protected DatabaseAccess databaseAccess;

    @Override
    public void setDatabaseAccess(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.fragment_simple_text, parent, false);
    }

    @Override
    public View convertView(View convertView, ViewGroup parent) {
        return convertView;
    }

}
