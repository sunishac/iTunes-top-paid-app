package com.example.chala.group12_hw06;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chala on 2/25/2017.
 */

public class iTunesAdapter extends ArrayAdapter<iTunes> {
    Context mcontext;
    List<iTunes> mdata;
    int mResouce;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;
    View vw;


    public iTunesAdapter(Context context, int resource, List<iTunes> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.mResouce=resource;
        this.mdata=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResouce,parent,false);
        }
        final ImageView st=(ImageView) convertView.findViewById(R.id.star_lv);
        builder=new AlertDialog.Builder(mcontext);
        builder.setTitle("Add to Favorites").setMessage("Are you sure that you want to add this App to favorites ?").setCancelable(false).setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo","Clicked No");
            }
        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mdata.get(position).setStar(1);
                Picasso.with(mcontext).load(R.drawable.blackstar).into(st);
            }
        });
        final AlertDialog simpleAlert=builder.create();

        builder2=new AlertDialog.Builder(mcontext);
        builder2.setTitle("Add to Favorites").setMessage("Are you sure that you want to remove this App from favorites ?").setCancelable(false).setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo","Clicked No");
            }
        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mdata.get(position).setStar(0);
                Picasso.with(mcontext).load(R.drawable.whitestar).into(st);
            }
        });
        final AlertDialog simpleAlert2=builder2.create();

        final iTunes tune= mdata.get(position);
        TextView label=(TextView) convertView.findViewById(R.id.textView_tv);
        label.setText(tune.getName().toString() + ". Price:  "+tune.getPrice().toString());
        ImageView mg=(ImageView) convertView.findViewById(R.id.imageView_lv);
        Picasso.with(mcontext).load(tune.getImage()).into(mg);
        if(tune.getStar()==0){
            Picasso.with(mcontext).load(R.drawable.whitestar).into(st);
        }else if(tune.getStar()==1){
            Picasso.with(mcontext).load(R.drawable.blackstar).into(st);
        }

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tune.getStar()==0){
                    simpleAlert.show();
                }else if(tune.getStar()==1){
                    simpleAlert2.show();
                }
                SharedPreferences  sharedPref = PreferenceManager.getDefaultSharedPreferences(mcontext);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(mdata.get(position).getName(),mdata.get(position).getStar());
                editor.commit();
            }
        });
        return convertView;
    }
}
