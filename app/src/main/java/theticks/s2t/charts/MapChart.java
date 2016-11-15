package theticks.s2t.charts;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

import theticks.s2t.ChartPoint;
import theticks.s2t.R;

/**
 * Created by Mihai Balint on 11/15/16.
 */

public class MapChart extends SimpleTextChart {

    private List<ChartPoint> studiesByCountry;

    public MapChart() {
        this.layout_id = R.layout.fragment_map_chart;
        studiesByCountry = new ArrayList<ChartPoint>();
    }

    @Override
    protected View viewSetup(View v) {
        this.filterCountries();

        WebView webView = (WebView) v.findViewById(R.id.web_view);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("file:///android_asset/studies-by-country.html");
        return v;
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
