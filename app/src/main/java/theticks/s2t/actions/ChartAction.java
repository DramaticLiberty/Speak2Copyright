package theticks.s2t.actions;

import theticks.s2t.Constants;
import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.charts.BarChart;
import theticks.s2t.charts.BubbleChart;
import theticks.s2t.charts.MapChart;
import theticks.s2t.charts.PieChart;

public class ChartAction implements IAction {
    private final String sql;
    private IChart chart;
    private int chartType;

    public ChartAction(int chartType) {
        this.chartType = chartType;
        switch (chartType) {
            case Constants.GROUPED_INDUSTRIES:
                sql = "SELECT name as country, count(*) as number_of_studies FROM " + Constants.TABLE_STUDIES_TO_INDUSTRIES +
                    " JOIN " + Constants.TABLE_INDUSTRIES + " i ON industry_id=i.id GROUP BY industry_id;";
                break;
            case Constants.GROUPED_YEAR:
                sql = "SELECT year as year, count(*) as number_of_studies FROM " + Constants.TABLE_STUDIES +
                        " WHERE year != '' AND ABS(year) > 1990 GROUP BY year;";
                break;
            default:
                sql = "";
        }
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        switch (chartType) {
            case Constants.GROUPED_INDUSTRIES:
                return new PieChart(databaseAccess.executeSQL(sql));
            case Constants.GROUPED_YEAR:
                return new BarChart(databaseAccess.executeSQL(sql));
        }
        return null;
    }
}
