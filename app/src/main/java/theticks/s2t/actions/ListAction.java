package theticks.s2t.actions;

import java.util.List;
import java.util.Map;

import theticks.s2t.Constants;
import theticks.s2t.DatabaseAccess;
import theticks.s2t.IChart;
import theticks.s2t.charts.TextChart;
import theticks.s2t.charts.ListChart;

public class ListAction implements IAction{

    private String industry;
    private List<String> supportingData;
    private int actionType;
    private String target;

    public ListAction(int actionType, String target) {
        this.actionType = actionType;
        this.target = target;
    }

    @Override
    public IChart execute(DatabaseAccess databaseAccess) {
        switch (actionType) {
            case Constants.SHOULD_UPDATE:
                if (target == null){
                    String update_copyright = "SELECT s.title AS title, s.url as url FROM " + Constants.TABLE_STUDIES + " s where s.title != '' and abs(s.year) != 0 order by year desc limit 3";
                    return new ListChart("Probably yes.", "Here are some recent studies that show why:", databaseAccess.executeSQL(update_copyright));
                }
                else {
                    String update_industry = "SELECT s.title AS title, s.url as url FROM " + Constants.TABLE_STUDIES +
                            " s JOIN " + Constants.TABLE_STUDIES_TO_INDUSTRIES + " si ON s.id=si.study_id JOIN " + Constants.TABLE_INDUSTRIES +
                            " i on i.id=si.industry_id where i.name=upper('" + target + "') and s.title !='' and abs(s.year) != 0 order by year desc limit 3";
                    Map<String, List<String>> results = databaseAccess.executeSQL(update_industry);
                    if (results.keySet().size() < 3)
                        return new ListChart("Probably no.", "There are no studies on this topic", null);
                    else
                        return new ListChart("Probably yes.", "Here are some recent studies that show why:", results);
                }

            case Constants.HAD_BEEN_STUDIED:
                String hasBeenStudied = "SELECT s.title AS title, s.url as url FROM " + Constants.TABLE_STUDIES + " s JOIN " +
                        Constants.TABLE_STUDIES_TO_COUNTRIES + " ON s.id=study_id JOIN " + Constants.TABLE_COUNTRIES + "c ON c.id=country_id" +
                        " WHERE c.name = upper('" + target + "') or c.name = 'WORLDWIDE' where s.title != '' and abs(s.year) != 0 order by year desc limit 3";
                Map<String, List<String>> results = databaseAccess.executeSQL(hasBeenStudied);
                if (results.keySet().size() < 3)
                    return new ListChart("No.", "There are no studies yet", null);
                else
                    return new ListChart("Yes.", "Here are the most recent studies", results);

        }
        return new TextChart(Constants.DEFAULT);
    }
}
