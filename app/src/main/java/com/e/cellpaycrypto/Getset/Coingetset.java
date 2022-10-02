package com.e.cellpaycrypto.Getset;

public class Coingetset {
    String Id, Title, Cost, Image;

    public Coingetset(String id, String title, String cost, String image) {
        Id = id;
        Title = title;
        Cost = cost;
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
