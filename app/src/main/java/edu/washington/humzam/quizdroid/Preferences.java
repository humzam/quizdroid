package edu.washington.humzam.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by humzamangrio on 5/18/15.
 */
public class Preferences extends Activity {

    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.preferences_layout);

        submit = (Button) findViewById(R.id.preferences_submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToTopicList = new Intent(Preferences.this, MainActivity.class);
                startActivity(goToTopicList);
                finish();
            }
        });
    }
}
