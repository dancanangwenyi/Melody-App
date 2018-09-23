package com.example.dancan.serverclienttester;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        Button buttonA = findViewById(R.id.buttonA);
        buttonA.setOnClickListener(this);
        Button buttonB = findViewById(R.id.buttonB);
        buttonB.setOnClickListener(this);
        Button buttonC = findViewById(R.id.buttonC);
        buttonC.setOnClickListener(this);
        Button buttonD = findViewById(R.id.buttonD);
        buttonD.setOnClickListener(this);
        Button buttonE = findViewById(R.id.buttonE);
        buttonE.setOnClickListener(this);
        Button buttonF = findViewById(R.id.buttonF);
        buttonF.setOnClickListener(this);
        Button buttonG = findViewById(R.id.buttonG);
        buttonG.setOnClickListener(this);
        Button buttonH = findViewById(R.id.buttonH);
        buttonH.setOnClickListener(this);
        Button buttonI = findViewById(R.id.buttonI);
        buttonI.setOnClickListener(this);
        Button buttonJ = findViewById(R.id.buttonJ);
        buttonJ.setOnClickListener(this);
        Button buttonK = findViewById(R.id.buttonK);
        buttonK.setOnClickListener(this);
        Button buttonL = findViewById(R.id.buttonL);
        buttonL.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) throws NullPointerException {
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        switch (view.getId()) {
            case R.id.buttonSend:
                sendMessage(messageToBeSent.getText().toString());
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonA:
                sendMessage("A" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonB:
                sendMessage("B" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonC:
                sendMessage("C" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonD:
                sendMessage("D" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonE:
                sendMessage("E" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonF:
                sendMessage("F" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonG:
                sendMessage("G" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonH:
                sendMessage("H" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonI:
                sendMessage("I" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonJ:
                sendMessage("J" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonK:
                sendMessage("K" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
            case R.id.buttonL:
                sendMessage("L" + currentVolume + "255");
                messageToBeSent.setText("");
                connectionError.setText("");
                break;
        }
    }
    private void sendMessage(final String msg) {
        final String mes = msg.trim();
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.v("Tcp", "Clicked the send button");
                    Socket s = new Socket("196.42.78.98", 4448);
                    Log.v("Tcp", "Got the Socket and host address");
                    DataOutputStream oos = new DataOutputStream(s.getOutputStream());
                    oos.writeUTF(mes);
                    oos.flush();

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
                                replyFromServer.setTextColor(Color.GREEN);
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
                            connectionError.setTextColor(Color.RED);
                            connectionError.setText(error);
                        }
                    });
                    Log.v("Tcp", e.toString());
                } catch (final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String error = e.toString();
                            connectionError.setTextColor(Color.RED);
                            connectionError.setText(error);
                        }
                    });
                    Log.v("Tcp", e.toString());
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String error = e.toString();
                            connectionError.setTextColor(Color.RED);
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
