package theticks.s2t.actions;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.charts.TextChart;

public class DefaultAction implements IAction {
    private String text;

    public DefaultAction(String text) {
        this.text = text;
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        return new TextChart(text);
    }
}
