package theticks.s2t;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressLint("SetJavaScriptEnabled")
public class SpeakCopy extends AppCompatActivity {
    public static final int REQ_CODE_SPEECH_INPUT = 110;
    private TextView output_text;
    public static final String CHARTS_PATH = "file:///android_asset/";

    WebView webView;
    private List<StudiesByCountry> studiesByCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_copy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        output_text = (TextView) findViewById(R.id.output_text);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        studiesByCountry = databaseAccess.getNumberOfStudiesByCountry();
        this.filterCountries();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        webView = (WebView) findViewById(R.id.web_view);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("file:///android_asset/studies-by-country.html");

        fab.setOnClickListener(new SpeakAfterButton(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    output_text.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speak_copy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void filterCountries() {
        for(int i=0; i<studiesByCountry.size(); i++) {
            String name= studiesByCountry.get(i).getCountryName();
            if (name.matches(".*\\d+.*")) {
                studiesByCountry.remove(i);
                i--;
            }
            else if(name.contains("NOT STATED")) {
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
            return studiesByCountry.get(i).getCountryName();
        }

        @JavascriptInterface
        public int getStudies(int i) {
            return studiesByCountry.get(i).getNumberOfStudies();
        }
    }
}
