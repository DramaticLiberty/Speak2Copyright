package theticks.s2t.actions;

import theticks.s2t.Constants;
import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.charts.MapChart;
import theticks.s2t.charts.PieChart;

public class ChartAction implements IAction {
    private final String sql;
    private IChart chart;

    public ChartAction(int chartType) {
        sql = "SELECT name as country, count(*) as number_of_studies FROM " + Constants.TABLE_STUDIES_TO_INDUSTRIES +
                " JOIN " + Constants.TABLE_INDUSTRIES + " i ON industry_id=i.id GROUP BY industry_id;";
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        return new PieChart(databaseAccess.executeSQL(sql));
    }
}
