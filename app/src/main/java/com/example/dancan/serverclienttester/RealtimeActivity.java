package com.example.dancan.serverclienttester;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RealtimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        final TextView tView = findViewById(R.id.textView);
        final Button button = findViewById(R.id.buttonSend);
        //To ensure better performance this line must be removed
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Log.v("Tcp", "Clicked the button");
                    Socket s = new Socket("197.239.161.75", 4447);
                    Log.v("Tcp", "Got the Socket and host address");
                    OutputStream out = s.getOutputStream();
                    PrintWriter output = new PrintWriter(out);
                    output.println("Hello Mr Pi!!");
                    out.close();

                } catch (UnknownHostException e) {
                    tView.setText(e.toString());
                    Log.v("Tcp", e.toString());
                } catch (IOException e) {
                    tView.setText(e.toString());
                    Log.v("Tcp", e.toString());
                } catch (Exception e) {
                    tView.setText(e.toString());

                }
            }
        });
    }
}
