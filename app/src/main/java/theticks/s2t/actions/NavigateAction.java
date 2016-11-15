package theticks.s2t.actions;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;

public class NavigateAction implements IAction{
    private String url;

    public NavigateAction(String url) {
        this.url = url;
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        return null;
    }
}
