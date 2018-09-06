package com.example.dancan.serverclienttester;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
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
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = findViewById(R.id.playlist);
        listView.setAdapter(arrayAdapter);
    }
}
