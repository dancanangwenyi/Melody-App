package com.example.dancan.serverclienttester;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    private TextView connectionInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        connectionInfo = findViewById(R.id.connInfo);
        ArrayList<String> items = new ArrayList<>();
        items.add("tune 01");
        items.add("tune 02");
        items.add("tune 03");
        items.add("tune 04");
        items.add("tune 05");
        items.add("tune 06");
        items.add("tune 07");
        items.add("tune 08");
        items.add("tune 09");
        items.add("tune 10");
        items.add("tune 11");
        items.add("tune 12");
        items.add("tune 13");
        items.add("tune 14");
        items.add("tune 15");
        items.add("tune 16");
        items.add("tune 17");
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = findViewById(R.id.playlist);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object entry = adapterView.getItemAtPosition(i);
                sendMessage(entry.toString());
            }
        });
    }

    private void sendMessage(final String msg) {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.v("Tcp", "Clicked the button");
                    Socket s = new Socket("196.24.183.81", 4448);
                    Log.v("Tcp", "Got the Socket and host address");
                    DataOutputStream oos = new DataOutputStream(s.getOutputStream());
                    oos.writeUTF(msg);
                    oos.flush();
                    //oos.close();

                    InputStreamReader reader = new InputStreamReader(s.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    final String message = bufferedReader.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ///String fromServer = connectionInfo.getText().toString();
                            if (message.trim().length() != 0) {

                                String reply = "\nReply from server: " + message;
                                connectionInfo.setTextColor(Color.GREEN);
                                connectionInfo.setText(reply);
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
                            connectionInfo.setTextColor(Color.RED);
                            connectionInfo.setText(error);
                        }
                    });
                    Log.v("Tcp", e.toString());
                } catch (final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String error = e.toString();
                            connectionInfo.setText(error);
                            connectionInfo.setTextColor(Color.RED);
                        }
                    });
                    Log.v("Tcp", e.toString());
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String error = e.toString();
                            connectionInfo.setTextColor(Color.RED);
                            connectionInfo.setText(error);
                        }
                    });
                    Log.v("Tcp", e.toString());
                }
            }
        });

        thread.start();
    }
}
