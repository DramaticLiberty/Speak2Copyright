package theticks.s2t.charts;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import theticks.s2t.ChartPoint;
import theticks.s2t.R;


public class MapChart extends SimpleTextChart {

    private List<ChartPoint> studiesByCountry;
    private Map<String, List<String>> data;

    public MapChart(Map<String, List<String>> data) {
        this.data = data;
        this.layout_id = R.layout.fragment_map_chart;
    }

    @Override
    protected View viewSetup(View v) {
        WebView webView = (WebView) v.findViewById(R.id.web_view);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("file:///android_asset/studies-by-country.html");
        return v;
    }

    public class WebAppInterface {
        @JavascriptInterface
        public int getNumberOfCountries() {
            return data.get("country").size();
        }

        @JavascriptInterface
        public String getCountry(int i) {
            return data.get("country").get(i);
        }

        @JavascriptInterface
        public int getStudies(int i) {
            return Integer.parseInt(data.get("number_of_studies").get(i));
        }
    }
}
