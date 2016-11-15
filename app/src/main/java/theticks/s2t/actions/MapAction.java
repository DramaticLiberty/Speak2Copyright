package theticks.s2t.actions;

import theticks.s2t.Constants;
import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.charts.MapChart;

public class MapAction implements IAction{
    private final String sql;

    public MapAction() {
        sql = "SELECT name as country, count(*) as number_of_studies FROM " + Constants.TABLE_STUDIES_TO_COUNTRIES +
                " JOIN " + Constants.TABLE_COUNTRIES + " c ON country_id=c.id GROUP BY country_id;";
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        return new MapChart(databaseAccess.executeSQL(sql));
    }
}
