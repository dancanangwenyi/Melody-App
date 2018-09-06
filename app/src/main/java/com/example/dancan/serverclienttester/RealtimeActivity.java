package com.example.dancan.serverclienttester;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RealtimeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView replyFromServer;
    private TextView connectionError;
    private EditText messageToBeSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);
        connectionError = findViewById(R.id.textView);
        replyFromServer = findViewById(R.id.serverReply);
        messageToBeSent = findViewById(R.id.clientMessage);
        Button button = findViewById(R.id.buttonSend);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSend:
                sendMessage(messageToBeSent.getText().toString());
                messageToBeSent.setText("");
                break;
        }

    }

    private void sendMessage(final String msg) {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.v("Tcp", "Clicked the button");
                    Socket s = new Socket("196.47.239.112", 4448);
                    Log.v("Tcp", "Got the Socket and host address");
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(msg);

                    InputStreamReader reader = new InputStreamReader(s.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    final String message = bufferedReader.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String fromServer = replyFromServer.getText().toString();
                            if (message.trim().length() != 0) {

                                String reply = fromServer + "\nReply from server: " + message;
                                replyFromServer.setText(reply);
                            }
                        }
                    }); //end of handler runnable

                    oos.close();
                    s.close();

                } catch (final UnknownHostException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String error = e.toString();
                            connectionError.setText(error);
                        }
                    });
                    Log.v("Tcp", e.toString());
                } catch (final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String error = e.toString();
                            connectionError.setText(error);
                        }
                    });
                    Log.v("Tcp", e.toString());
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String error = e.toString();
                            connectionError.setText(error);
                        }
                    });
                    Log.v("Tcp", e.toString());
                }
            }
        });

        thread.start();
    }
}
