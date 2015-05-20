package edu.washington.humzam.quizdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by humzamangrio on 5/18/15.
 */
public class Preferences extends PreferencesActivity {

    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//        setContentView(R.layout.preferences_layout);

//        EditText urlField = (EditText) findViewById(R.id.url_edittext);
//        final String url = urlField.getText().toString();
//
//        EditText intervalField = (EditText) findViewById(R.id.interval_edittext);
//        final int interval = Integer.parseInt(intervalField.getText().toString());
//
//        submit = (Button) findViewById(R.id.preferences_submit_btn);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToTopicList = new Intent(Preferences.this, MainActivity.class);
//                Bundle info = new Bundle();
//                info.putString("url", url);
//                info.putInt("interval", interval);
//                startActivity(goToTopicList);
//                finish();
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.topics:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
