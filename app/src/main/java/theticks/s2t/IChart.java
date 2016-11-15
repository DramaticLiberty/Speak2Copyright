package theticks.s2t;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IChart {
    View createView(LayoutInflater inflater, ViewGroup parent);
    View convertView(View convertView, ViewGroup parent);
    void setDatabaseAccess(DatabaseAccess databaseAccess);
}
