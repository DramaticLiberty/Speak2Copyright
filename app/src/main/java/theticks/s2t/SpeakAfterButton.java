package theticks.s2t;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.Locale;


public class SpeakAfterButton implements View.OnClickListener {

    private SpeakCopy activity;

    public SpeakAfterButton(SpeakCopy speak) {
        this.activity = speak;
    }

    @Override
    public void onClick(View view) {
        activity.append();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                activity.getString(R.string.speak_now));
        try {
            activity.startActivityForResult(intent, SpeakCopy.REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Snackbar.make(view, activity.getString(R.string.speak_na), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
