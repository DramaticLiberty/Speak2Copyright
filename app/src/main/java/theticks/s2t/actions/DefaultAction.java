package theticks.s2t.actions;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.charts.DefaultChart;

public class DefaultAction implements IAction {
    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        return new DefaultChart();
    }
}
