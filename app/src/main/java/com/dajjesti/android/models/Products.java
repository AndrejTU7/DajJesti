package com.dajjesti.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrej on 14.5.2016..
 */
public class Products implements Parcelable {

    /**
     * id : 1
     * name : Mjesana pizza
     * description : Najbolja jebacka mjesana u gradu
     * pricelist : [{"id":421,"price":55.23,"type":"Jumbo"},{"id":213,"price":35.23,"type":"Velika"}]
     * ingredients : ["sir","sunka","gljive","paradajz"]
     * category : 2
     */

    private int id;
    private String name;
    private String description;
    private int category;
    /**
     * id : 421
     * price : 55.23
     * type : Jumbo
     */

    private List<Price> pricelist;
    private List<String> ingredients;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<Price> getPricelist() {
        return pricelist;
    }

    public void setPricelist(List<Price> pricelist) {
        this.pricelist = pricelist;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static class Price {
        private int id;
        private double price;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.category);
        dest.writeList(this.pricelist);
        dest.writeStringList(this.ingredients);
    }

    public Products() {
    }

    protected Products(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.category = in.readInt();
        this.pricelist = new ArrayList<Price>();
        in.readList(this.pricelist, Price.class.getClassLoader());
        this.ingredients = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel source) {
            return new Products(source);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}
