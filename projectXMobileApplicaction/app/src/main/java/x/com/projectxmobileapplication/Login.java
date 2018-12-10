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
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private SharedPreferences preferences;
    private TextView textView;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = (TextView)findViewById(R.id.hello);
        login = (Button)findViewById(R.id.button);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString("Command", "").commit();
        preferences.edit().putBoolean("ResultEmpty",true).commit();
        preferences.edit().putString("Result","Empty").commit();

        ConnectToServer connect = new ConnectToServer();
        connect.setContext(getApplicationContext());
        new Thread(connect).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                preferences.edit().putString("Command", "LOGIN").commit();
                new results().execute();
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
                Intent intent = new Intent(getApplicationContext(),Sessions.class);
                startActivity(intent);
            }
        }
    }
}
