package theticks.s2t.actions;

import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;

public interface IAction {
    public IChart execute(DatabaseAccess databaseAccess);
}
