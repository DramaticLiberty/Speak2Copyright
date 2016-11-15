package theticks.s2t.actions;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;

public class ListAction implements IAction{

    private String industry;

    public ListAction() {

    }

    public ListAction(String industry) {
        this.industry = industry;
    }
    @Override
    public IChart execute(DatabaseAccess databaseAccess) {

        return null;
    }
}
