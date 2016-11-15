package theticks.s2t.charts;

import android.view.View;
import android.widget.TextView;

import theticks.s2t.R;
import theticks.s2t.SpeakCopy;

/**
 * Created by Mihai Balint on 11/16/16.
 */

public class LandingSuggestions extends SimpleTextChart implements View.OnClickListener {
    private SpeakCopy activity;

    private int[] ids = {R.id.sugest1, R.id.sugest2, R.id.sugest3};
    private static final String[] texts = {
            "What is copyright?",
            "Show studies grouped by industry",
            "Should music copyright be updated?"};

    public LandingSuggestions(SpeakCopy activity) {
        super(R.layout.fragment_landing_suggestions);
        this.activity = activity;
    }

    @Override
    protected View viewSetup(View v) {
        for(int i=0;i<ids.length;i++) {
            TextView tv = (TextView)v.findViewById(ids[i]);
            tv.setText(texts[i]);
            tv.setOnClickListener(this);
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<ids.length;i++) {
            if (v.getId() == ids[i])
                activity.processQuery(texts[i]);
         }
    }
}
