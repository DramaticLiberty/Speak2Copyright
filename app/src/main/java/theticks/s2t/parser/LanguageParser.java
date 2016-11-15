package theticks.s2t.parser;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import theticks.s2t.Constants;
import theticks.s2t.actions.ChartAction;
import theticks.s2t.actions.DefaultAction;
import theticks.s2t.actions.IAction;
import theticks.s2t.actions.ListAction;
import theticks.s2t.actions.MapAction;
import theticks.s2t.actions.NavigateAction;

public class LanguageParser {

    private final List<String> stopWords;
    private final Map<String, String[]> mappings;
    private Context context;

    public LanguageParser(Context context) {
        this.context = context;
        this.stopWords = readStopwords();
        this.mappings = readMappings();
    }

    public List<String> readStopwords() {
        List<String> stopwords = new ArrayList<String>();
        try {
            InputStream resourceAsStream = context.getResources().getAssets().open("stopwords");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            try {
                String stopword;
                while ((stopword = reader.readLine()) != null) {
                    stopwords.add(stopword.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopwords;
    }

    public Map<String, String[]> readMappings() {
        Map<String, String[]> mappings = new HashMap<String, String[]>();
        try {
            InputStream resourceAsStream = context.getResources().getAssets().open("mapping");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String mapping;
            while ((mapping = reader.readLine()) != null) {
                String[] split = mapping.split(":");
                String[] values;
                if (split.length > 1){
                    values = split[1].split(",");
                }
                else
                    values = new String[0];
                mappings.put(split[0], values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mappings;
    }

    public List<String> filter(String phrase) {
        List<String> filteredTokens = new ArrayList<String>();
        String[] tokens = phrase.split(" ");
        for (String token : tokens) {
            if (!stopWords.contains(token))
                filteredTokens.add(token);
        }
        return filteredTokens;
    }

    private String popToken(List<String> tokens) {
        String action = tokens.get(0);
        tokens.remove(action);
        return action;
    }

    /* use Levenshtein distance to compare strings */
    public static int distance(String a, String b) {
        a = a.toLowerCase(); b = b.toLowerCase();
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    private String industryMapping(String industryToken) {
        for (String key: mappings.keySet()){
            if (distance(industryToken, key) <= 3) return key;
            for (String mapping: mappings.get(key)) {
                if (distance(industryToken, mapping) <= 3) return key;
            }
        }
        return null;
    }

    public IAction parse(String phrase) {
        phrase = phrase.toLowerCase();
        phrase = phrase.replace("?", "");
        List<String> tokens = filter(phrase);
        if (tokens.isEmpty()) return new DefaultAction();
            String action = popToken(tokens);
        if (distance(action, Constants.WHAT) < 2) {
            String what = popToken(tokens);
            // What is copyright?
            if (what.equalsIgnoreCase("copyright"))
                return new NavigateAction("https://en.wikipedia.org/wiki/Copyright");
        } else if (distance(action, Constants.SHOULD) < 2 || tokens.contains("updated")) {
            String what = popToken(tokens);
            // Should copyright be updated/abolished/etc? -> return all studies
            if (what.equalsIgnoreCase("copyright")) return new ListAction(Constants.SHOULD_UPDATE, null);
            else {
                // Should {industry} copyright be updated/abolished/etc? -> Return studies by industry
                int depth = 1;
                while (depth <= Constants.SEARCH_DEPTH) {
                    String industry = industryMapping(what);
                    if (industry != null)
                        return new ListAction(Constants.SHOULD_UPDATE, industry);
                    what += popToken(tokens);
                    depth ++;
                }
            }
//        } else if (distance(action, Constants.HAS) < 2 || tokens.contains("studied") || tokens.contains("started") || tokens.contains("are")) {
//            // Are there any studies in {country}? -> return copyright studies for that country
//            while (tokens.size() > 0) {
//                String token = popToken(tokens);
//                if (distance(token, "studied") <= 2 || distance(token, "studies") <= 2) {
//                    if (tokens.size() > 0) {
//                        String where = popToken(tokens);
//                        return new ListAction(Constants.HAD_BEEN_STUDIED, where);
//                    }
//                }
//            }
        } else if (distance(action, Constants.SHOW) < 2)  {
            // Show me copyright studies grouped by {country}? -> return studies grouped by item
            if (tokens.contains("country"))
                return new MapAction();
            else if (tokens.contains("industry"))
                return new ChartAction(Constants.GROUPED_INDUSTRIES);
            else if (tokens.contains("year"))
                return new ChartAction(Constants.GROUPED_YEAR);
        }
        return new DefaultAction();
    }

}
