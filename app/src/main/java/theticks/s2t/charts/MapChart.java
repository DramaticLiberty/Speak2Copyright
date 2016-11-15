package theticks.s2t.charts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.List;

import theticks.s2t.ChartPoint;
import theticks.s2t.R;

/**
 * Created by Mihai Balint on 11/15/16.
 */

public class MapChart extends SimpleText {

    private WebView webView;
    private List<ChartPoint> studiesByCountry;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent) {
        View v = inflater.inflate(R.layout.fragment_map_chart, parent, false);
        studiesByCountry = databaseAccess.getNumberOfStudiesByCountry();

        this.filterCountries();

        webView = (WebView) v.findViewById(R.id.web_view);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("file:///android_asset/studies-by-country.html");

        return v;
    }

    @Override
    public View convertView(View convertView, ViewGroup parent) {
        // TODO convert this
        return convertView;
    }

    private void filterCountries() {
        for (int i = 0; i < studiesByCountry.size(); i++) {
            String name = studiesByCountry.get(i).getName();
            if (name.matches(".*\\d+.*")) {
                studiesByCountry.remove(i);
                i--;
            } else if (name.contains("NOT STATED")) {
                studiesByCountry.remove(i);
                i--;
            }
        }
    }

    public class WebAppInterface {
        @JavascriptInterface
        public int getNumberOfCountries() {
            return studiesByCountry.size();
        }

        @JavascriptInterface
        public String getCountry(int i) {
            return studiesByCountry.get(i).getName();
        }

        @JavascriptInterface
        public int getStudies(int i) {
            return studiesByCountry.get(i).getValue();
        }
    }
}


