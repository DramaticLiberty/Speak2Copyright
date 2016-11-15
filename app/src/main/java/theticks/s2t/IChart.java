package theticks.s2t;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mihai Balint on 11/15/16.
 */

public interface IChart {
    View createView(LayoutInflater inflater, ViewGroup parent);
    View convertView(View convertView, ViewGroup parent);
    void setDatabaseAccess(DatabaseAccess databaseAccess);
}
