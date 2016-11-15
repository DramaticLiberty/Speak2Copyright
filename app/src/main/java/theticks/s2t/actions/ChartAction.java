package theticks.s2t.actions;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;

public class ChartAction implements IAction {
    private int chartType;

    public ChartAction(int chartType) {
        this.chartType = chartType;
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        return null;
    }
}
