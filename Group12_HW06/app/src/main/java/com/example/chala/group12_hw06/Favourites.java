package com.example.chala.group12_hw06;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {
    ArrayList<iTunes> newItunes = new ArrayList<iTunes>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        ArrayAdapter<iTunes> adapter2;
        if(getIntent().getExtras().containsKey("myList")) {
            newItunes = (ArrayList<iTunes>) getIntent().getExtras().getSerializable("myList");
        }
       /* SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Favourites.this);
        for(int i=0;i<newItunes.size();i++) {
            int s = sharedPref.getInt(newItunes.get(i).getName(), -1);
            if (s != -1) {
                newItunes.get(i).setStar(s);
            }
            *//*if(newItunes.get(i).getStar()==0){
                newItunes.remove(i);
            }*//*
        }*/

        Log.d("demo","new list is "+newItunes);
        ListView lv2 = (ListView) findViewById(R.id.lv3);
        adapter2 = new iTunesAdapter(Favourites.this,R.layout.itunes_list_view,newItunes);
        lv2.setAdapter(adapter2);
        adapter2.setNotifyOnChange(true);


        findViewById(R.id.finishFavID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("fav",newItunes);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
