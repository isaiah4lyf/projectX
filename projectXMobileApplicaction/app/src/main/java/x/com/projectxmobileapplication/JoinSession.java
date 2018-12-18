package x.com.projectxmobileapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class JoinSession extends AppCompatActivity {
    private TextView textView;
    private SharedPreferences preferences;
    private ScrollView sv;
    private EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  // You need to fix this
        Bundle extras = getIntent().getExtras();
        String session = extras.getString("Session");
        textView = (TextView)findViewById(R.id.hello);

        sv = (ScrollView)findViewById(R.id.scroll);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString("Command", "SESSION_JOINED").commit();
        preferences.edit().putString("sessionIndex", session).commit();
        preferences.edit().putBoolean("SessionAlive", true).commit();
        textView.setText(session);

        new results().execute();
        text = (EditText)findViewById(R.id.editText);
        text.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    textView.append(text.getText() + "\n");
                    preferences.edit().putString("SessionAnswer",text.getText().toString()).commit();
                    preferences.edit().putString("Command","SUBMIT_SESSION_ANSWER").commit();
                    return true;
                }
                return false;
            }
        });
    }
    private class results extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //if you want, start progress dialog here
        }
        @Override
        protected String doInBackground(String... urls) {
            String result = preferences.getString("Result","Empty");
            boolean ResultEmpty = preferences.getBoolean("ResultEmpty",true);
            while (ResultEmpty == true) {
                ResultEmpty = preferences.getBoolean("ResultEmpty",true);
                result = preferences.getString("Result","Empty");
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //if you started progress dialog dismiss it here
            boolean ResultEmpty = preferences.getBoolean("ResultEmpty",true);
            if(ResultEmpty == false)
            {
                textView.append(result + "\n");
                sv.scrollTo(0, sv.getBottom());
                preferences.edit().putBoolean("ResultEmpty",true).commit();
                new results().execute();
            }
        }
    }
}
