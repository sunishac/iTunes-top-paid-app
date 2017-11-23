package com.example.chala.group12_hw06;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by chala on 2/22/2017.
 */

public class iTunes implements Serializable {
    String name,image,price;
    int star;

    public iTunes() {
        this.name = name;
        this.image = image;
        this.price = price;
        this.star = 0;
    }

    public static iTunes createTunes(JSONObject js) throws JSONException {
        iTunes is=new iTunes();
        JSONObject nm=js.getJSONObject("im:name");
        is.setName(nm.getString("label"));
        JSONArray im=js.getJSONArray("im:image");
        JSONObject ig=im.getJSONObject(0);
        is.setImage(ig.getString("label"));
        JSONObject p=js.getJSONObject("im:price");
        is.setPrice(p.getString("label"));
        return is;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "iTunes{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                ", star=" + star +
                '}';
    }
}
