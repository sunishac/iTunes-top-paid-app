package com.example.chala.group12_hw06;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements iTunesAsyncTask.IData {
    ProgressDialog pd;
    ArrayList<iTunes> ins=new ArrayList<iTunes>();
    ListView lv;
    iTunesAdapter adapter;
    String favName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd= new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.show();
        lv=(ListView) findViewById(R.id.list);
        new iTunesAsyncTask(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.refresh_id:
                new iTunesAsyncTask(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
                break;

            case R.id.favourites_id:
                ArrayList<iTunes> fav=new ArrayList<iTunes>();
                //Dhenuka
                int i=0;
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                if(ins.size()>0 && i<25) {
                    int s = sharedPref.getInt(ins.get(i).getName(), -1);
                    if (s != -1) {
                        ins.get(i).setStar(s);
                    }
                }
                    for (int j=0;j<ins.size();j++) {
                    if(ins.get(j).getStar()==1){
                        fav.add(ins.get(j));
                    }
                }
                Log.d("demo","fav is "+fav);
                Intent intent = new Intent(MainActivity.this,Favourites.class);
                intent.putExtra("myList",fav);
                startActivityForResult(intent,1);
                break;
            case R.id.sort_increasingly_id:
                Collections.sort(ins, new Comparator<iTunes>() {
                    @Override
                    public int compare(iTunes itune1, iTunes itune2)
                    {
                        return  itune1.price.compareTo(itune2.price);
                    }
                });
                adapter=new iTunesAdapter(MainActivity.this,R.layout.itunes_list_view,ins);
                lv.setAdapter(adapter);
                break;
            case R.id.sort_decreasingly_id:
                Collections.sort(ins, new Comparator<iTunes>() {
                    @Override
                    public int compare(iTunes itune1, iTunes itune2)
                    {
                        return  itune2.price.compareTo(itune1.price);
                    }
                });
                adapter=new iTunesAdapter(MainActivity.this,R.layout.itunes_list_view,ins);
                lv.setAdapter(adapter);
                break;

        }

        return true;
    }

    @Override
    public void topApps(ArrayList<iTunes> iTunes) {
        ins=iTunes;
        pd.dismiss();
        lv.setVisibility(View.VISIBLE);
        Log.d("demo","retrived is : "+ins);
      	adapter=new iTunesAdapter(MainActivity.this,R.layout.itunes_list_view,ins);
            lv.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("demo","myonActivityResult");
        if (requestCode == 1) {
            Log.d("demo","resultcode");
            ArrayList<iTunes> x=new ArrayList<iTunes>();
            if(resultCode == RESULT_OK){

                Log.d("demo","resultok");
                if(data.getExtras().containsKey("fav")) {
                    x = (ArrayList<iTunes>) data.getExtras().getSerializable("fav");
                }
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        
                for(int j=0;j<x.size();j++) {

                    for (int i = 0; ins.size() > 0 && i < 25; i++) {
                 
                        if (ins.get(i).getName().equals(x.get(j).getName())) {
                            ins.get(i).setStar(x.get(j).getStar());
                        }
                    }
                }

                adapter=new iTunesAdapter(MainActivity.this,R.layout.itunes_list_view,ins);
                lv.setAdapter(adapter);
                adapter.setNotifyOnChange(true);
            }
            if (resultCode == RESULT_CANCELED) {
                Log.d("demo","result_cancelled");
            }
        }
    }
}
