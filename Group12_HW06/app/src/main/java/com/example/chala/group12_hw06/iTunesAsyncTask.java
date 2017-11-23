package com.example.chala.group12_hw06;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chala on 2/22/2017.
 */

public class iTunesAsyncTask extends AsyncTask<String,Void,ArrayList<iTunes>> {
    IData activity;

    public iTunesAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<iTunes> doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line=reader.readLine();
                while(line!=null){
                    sb.append(line);
                    line=reader.readLine();
                }
                return iTunesUtil.iTunesJSONParser.parseTunes(sb.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<iTunes> iTunes) {
        super.onPostExecute(iTunes);
        activity.topApps(iTunes);
    }

    public interface IData{
        public void topApps(ArrayList<iTunes> iTunes);
    }
}
