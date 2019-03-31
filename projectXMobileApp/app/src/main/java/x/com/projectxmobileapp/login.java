package x.com.projectxmobileapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class login extends AppCompatActivity {
    private LayoutInflater inflater;
    private OkHttpClient client;
    private boolean JustJoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inflater = LayoutInflater.from(this);
        JustJoin = true;
        client = new OkHttpClient();
        start();
    }


    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            /*
            webSocket.send("Hello, it's SSaurel !");
            webSocket.send("What's up ?");
            webSocket.send(ByteString.decodeHex("deadbeef"));
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
            */
            webSocket.send("JOIN_GAME_SESSION:::::::::-::::::::::::::::-:::::::::::-:::::-::::" + 0 + ":::::::::-::::::::::::::::-:::::::::::-:::::-::::" + 1);
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output(text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output(bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }
    }
    private void start() {
        Request request = new Request.Builder().url("ws://192.168.43.175:8080/projectXTomCatServer/serverendpoint").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);

        client.dispatcher().executorService().shutdown();
    }

    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //output.setText(output.getText().toString() + "\n\n" + txt);
                //Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG).show();
                if(txt.startsWith(":::::::::-::::::::::::::::-:::::::::::-:::::-::::"))
                {
                    String[] messagePassed = txt.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::");
                    if (messagePassed[1] == "SessionFull") {
                        Toast.makeText(getApplicationContext(),"Session Full.",Toast.LENGTH_LONG).show();
                    }
                    else if (messagePassed[1] == "RemainingTime") {
                        Toast.makeText(getApplicationContext(),messagePassed[2],Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (JustJoin == true) {
                            String QuestionColor = "#6a5acd";
                            if (messagePassed[0].equals("science")) {
                                QuestionColor = "#800080";
                            }
                            else if (messagePassed[0].equals("General")) {
                                QuestionColor = "#4b0082";
                            }
                            else if (messagePassed[0].equals("History")) {
                                QuestionColor = "#228B22";
                            }
                            else if (messagePassed[0].equals("Economics")) {
                                QuestionColor = "#DC143C";
                            }
                            else if (messagePassed[0].equals("science")) {
                                QuestionColor = "#C71585";
                            }
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_example);
                            View viewMyLayout = inflater.inflate(R.layout.question, null);
                            TextView textView = (TextView)viewMyLayout.findViewById(R.id.title);
                            textView.setText(messagePassed[1]);
                            textView.setTextColor(Color.parseColor(QuestionColor));
                            TextView question = (TextView)viewMyLayout.findViewById(R.id.question);
                            question.setText(messagePassed[2]);
                            question.setTextColor(Color.parseColor(QuestionColor));
                            TextView clue = (TextView)viewMyLayout.findViewById(R.id.clue);
                            clue.setTextColor(Color.parseColor(QuestionColor));
                            ImageView clueImage = (ImageView)viewMyLayout.findViewById(R.id.clueImage);
                            new DownloadImageTask(clueImage)
                                    .execute("http://192.168.43.175:8060/Images/" +messagePassed[4]);
                            final String imageUrl = messagePassed[1];
                            clueImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),imageUrl,Toast.LENGTH_LONG).show();
                                }
                            });
                            LinearLayout boxes = (LinearLayout)viewMyLayout.findViewById(R.id.boxesLayout);

                            View box = inflater.inflate(R.layout.box, null);
                            boxes.addView(box);

                            View line = inflater.inflate(R.layout.line, null);
                            boxes.addView(line);
                            linearLayout.addView(viewMyLayout);

                            View space = inflater.inflate(R.layout.space, null);
                            linearLayout.addView(space);
                            Toast.makeText(getApplicationContext(),QuestionColor,Toast.LENGTH_LONG).show();
                            JustJoin = false;
                        }
                    }
                }
                else
                {
                    if(txt.contains(":::::::::-::::::::::::::::-:::::::::::-:::::-::::"))
                    {
                        try
                        {
                            String[] messagePassed = txt.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::");
                            String QuestionColor = "#6a5acd";
                            if (messagePassed[0].equals("science")) {
                                QuestionColor = "#800080";
                            }
                            else if (messagePassed[0].equals("General")) {
                                QuestionColor = "#4b0082";
                            }
                            else if (messagePassed[0].equals("History")) {
                                QuestionColor = "#228B22";
                            }
                            else if (messagePassed[0].equals("Economics")) {
                                QuestionColor = "#DC143C";
                            }
                            else if (messagePassed[0].equals("science")) {
                                QuestionColor = "#C71585";
                            }
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_example);
                            View viewMyLayout = inflater.inflate(R.layout.question, null);
                            TextView textView = (TextView)viewMyLayout.findViewById(R.id.title);
                            textView.setText(messagePassed[0]);
                            textView.setTextColor(Color.parseColor(QuestionColor));
                            TextView question = (TextView)viewMyLayout.findViewById(R.id.question);
                            question.setTextColor(Color.parseColor(QuestionColor));
                            TextView clue = (TextView)viewMyLayout.findViewById(R.id.clue);
                            clue.setTextColor(Color.parseColor(QuestionColor));
                            question.setText(messagePassed[1]);
                            ImageView clueImage = (ImageView)viewMyLayout.findViewById(R.id.clueImage);
                            new DownloadImageTask(clueImage)
                                    .execute("http://192.168.43.175:8060/Images/" +messagePassed[3]);
                            final String imageUrl = messagePassed[0];
                            clueImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),imageUrl,Toast.LENGTH_LONG).show();
                                }
                            });
                            LinearLayout boxes = (LinearLayout)viewMyLayout.findViewById(R.id.boxesLayout);
                            LinearLayout bo = (LinearLayout)inflater.inflate(R.layout.linearlayout,null);
                            LinearLayout bo2 = (LinearLayout)inflater.inflate(R.layout.linearlayout,null);
                            for(int i = 0; i < Integer.parseInt(messagePassed[2]); i++)
                            {
                                if(i < 9)
                                {
                                    View box = inflater.inflate(R.layout.box, null);
                                    bo.addView(box);
                                }
                                else
                                {
                                    View box = inflater.inflate(R.layout.box, null);
                                    bo2.addView(box);
                                }

                            }
                            boxes.addView(bo);
                            boxes.addView(bo2);
                            //View line = inflater.inflate(R.layout.line, null);
                            //boxes.addView(line);
                            linearLayout.addView(viewMyLayout);

                            View space = inflater.inflate(R.layout.space, null);
                            linearLayout.addView(space);
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                            Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        //Copy the exact spliter for correct answer
                        if (txt.contains("::::::::-::::::::::::::-:::::::::::-:::::-::::")) {
                            String[] answerTokens = txt.split("::::::::-::::::::::::::-:::::::::::-:::::-::::");
                            View answerPointsLay = inflater.inflate(R.layout.answer_and_points,null);
                            TextView answer = (TextView)answerPointsLay.findViewById(R.id.answer);
                            answer.setText("\t" + answerTokens[0]);
                            TextView points = (TextView)answerPointsLay.findViewById(R.id.points);
                            String pointsStr = answerTokens[1] + " Points";
                            points.setText("\t"+ pointsStr.replace("\n"," "));
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_example);
                            linearLayout.addView(answerPointsLay);
                        }
                        else {
                            if (txt.contains(":::::::-::::::::-:::::::::::-:::::::-::::")) {
                                String[] answerTokens = txt.split(":::::::-::::::::-:::::::::::-:::::::-::::");
                                //Correct Answer - green answerTokens[1]
                                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_example);
                                View correctAnswer = inflater.inflate(R.layout.correct_answer,null);
                                TextView correctAnawer = (TextView)correctAnswer.findViewById(R.id.textView);
                                correctAnawer.setText(answerTokens[1]);
                                linearLayout.addView(correctAnswer);
                            }
                            else if (txt.contains(":::::-::::::::-:::::::-:::::::-::::::")){
                                // Somebody join and left the session - txt.split(":::::-::::::::-:::::::-:::::::-::::::")[1]
                                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_example);
                                View correctAnswer = inflater.inflate(R.layout.whitetext,null);
                                TextView correctAnawer = (TextView)correctAnswer.findViewById(R.id.textView);
                                correctAnawer.setText(txt.split(":::::-::::::::-:::::::-:::::::-::::::")[1]);
                                linearLayout.addView(correctAnswer);
                            }
                            else {
                                // txt - Random message
                                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_example);
                                View correctAnswer = inflater.inflate(R.layout.red_text,null);
                                TextView correctAnawer = (TextView)correctAnswer.findViewById(R.id.textView);
                                correctAnawer.setText(txt);
                                linearLayout.addView(correctAnswer);
                            }
                        }
                    }
                }
            }
        });
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
