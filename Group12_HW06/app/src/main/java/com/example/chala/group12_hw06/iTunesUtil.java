package com.example.chala.group12_hw06;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by chala on 2/22/2017.
 */

public class iTunesUtil {
    static public class iTunesJSONParser{
        static ArrayList<iTunes> parseTunes(String in) throws JSONException{
            ArrayList<iTunes> its=new ArrayList<iTunes>();
            JSONObject root=new JSONObject(in);
            JSONObject feed=root.getJSONObject("feed");
            JSONArray iTunesJSONArray=feed.getJSONArray("entry");

            for(int i=0;i<iTunesJSONArray.length();i++){
                JSONObject iTunesObject=iTunesJSONArray.getJSONObject(i);
                iTunes it=iTunes.createTunes(iTunesObject);
                its.add(it);
            }
            return its;
        }
    }
}
