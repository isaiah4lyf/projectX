package x.com.projectxmobileapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Sessions extends AppCompatActivity {
    private TextView textView;
    private Button login;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        textView = (TextView)findViewById(R.id.hello);
        login = (Button)findViewById(R.id.button);
        login.setEnabled(false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString("Command", "GET_SESSIONS").commit();
        new results().execute();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                preferences.edit().putString("Command", "JOIN_SESSION").commit();
                new joinSession().execute();
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
                login.setEnabled(true);
                textView.setText(result);
                preferences.edit().putBoolean("ResultEmpty",true).commit();
            }
        }
    }
    private class joinSession extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //if you want, start progress dialog here
        }

        @Override
        protected String doInBackground(String... urls) {
            String result = preferences.getString("Result", "Empty");
            boolean ResultEmpty = preferences.getBoolean("ResultEmpty", true);
            while (ResultEmpty == true) {
                ResultEmpty = preferences.getBoolean("ResultEmpty", true);
                result = preferences.getString("Result", "Empty");
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //if you started progress dialog dismiss it here
            boolean ResultEmpty = preferences.getBoolean("ResultEmpty", true);
            if (ResultEmpty == false) {
                if(result.equals("Session Full, Join Another session!!!!!"))
                {
                    textView.setText(result);
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),JoinSession.class);
                    intent.putExtra("Session","0");
                    startActivity(intent);
                }
                login.setEnabled(true);
            }
        }
    }
}
