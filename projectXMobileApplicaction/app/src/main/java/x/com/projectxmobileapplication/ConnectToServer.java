package x.com.projectxmobileapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectToServer implements Runnable{
    private DataInputStream in;
    private DataOutputStream out;
    private boolean Processing;
    private Context context;
    private Socket socket;
    private SharedPreferences preferences;
    @Override
    public void run() {
        try
        {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            socket = new Socket("192.168.43.175",8060);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Processing = true;
            while(Processing)
            {
                String Command = preferences.getString("Command","");
                switch (Command)
                {
                    case "LOGIN":
                        ProcessCommand(Command);
                        break;
                    case  "GET_SESSIONS":
                        ProcessCommand(Command);
                        break;
                    case "GET_SESSION_CURRENT_DATA":
                        ProcessGetCurrentQuestionCommand(Command);
                        break;
                    case "QUIT":
                        sendCommand(Command);
                        Processing = false;
                        try {
                            in.close();
                            out.close();
                            socket.close();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                        break;
                }
            }
        }
        catch (Exception ex) { }
    }
    private void ProcessCommand(String Command)
    {
        sendCommand(Command);
        String result = readResponse();
        preferences.edit().putString("Result",result).commit();
        preferences.edit().putBoolean("ResultEmpty",false).commit();
        preferences.edit().putString("Command", "").commit();
    }
    private void ProcessGetCurrentQuestionCommand(String Command)
    {
        sendCommand(Command);
        sendCommand(preferences.getString("sessionIndex","0"));
        preferences.edit().putString("Command", "").commit();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean SessionAlive = preferences.getBoolean("SessionAlive",true);
                while (SessionAlive)
                {
                    SessionAlive = preferences.getBoolean("SessionAlive",true);
                    String data = readResponse();
                    preferences.edit().putString("Result",data).commit();
                    preferences.edit().putBoolean("ResultEmpty",false).commit();
                }
            }
        }).start();
    }


    protected void sendCommand(String message)
    {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String readResponse()
    {
        String response  = "";
        try
        {
            response = in.readUTF();
        }
        catch (Exception ex)
        {
            response = ex.toString();
        }
        return response;
    }
    public void setContext(Context context)
    {
        this.context = context;
    }
}
