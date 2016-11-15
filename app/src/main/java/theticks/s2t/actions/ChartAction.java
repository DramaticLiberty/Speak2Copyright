package theticks.s2t.actions;

import theticks.s2t.Constants;
import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.charts.BarChart;
import theticks.s2t.charts.DefaultChart;
import theticks.s2t.charts.MapChart;
import theticks.s2t.charts.PieChart;

public class ChartAction implements IAction {
    private IChart chart;
    private int chartType;

    public ChartAction(int chartType) {
        this.chartType = chartType;
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        switch (chartType) {
            case Constants.GROUPED_INDUSTRIES:
                String sql_industries = "SELECT name as country, count(*) as number_of_studies FROM " + Constants.TABLE_STUDIES_TO_INDUSTRIES +
                        " JOIN " + Constants.TABLE_INDUSTRIES + " i ON industry_id=i.id GROUP BY industry_id;";
                return new PieChart(databaseAccess.executeSQL(sql_industries));
            case Constants.GROUPED_YEAR:
                String sql_year = "SELECT year as year, count(*) as number_of_studies FROM " + Constants.TABLE_STUDIES +
                        " WHERE year != '' AND ABS(year) > 2000 GROUP BY year;";
                return new BarChart(databaseAccess.executeSQL(sql_year));
        }
        return new DefaultChart();
    }
}
