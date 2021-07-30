package com.brikelos.model.Local.models;

public class Purchase {

    public Purchase(int id, int buyerID, String date, String title, String description, double price, boolean deleted) {
        this.id          = id;
        this.buyerID     = buyerID;
        this.date        = date;
        this.title       = title;
        this.description = description;
        this.price       = price;
        this.deleted     = deleted;
    }
    public Purchase(int buyerID, String date, String title, String description, double price, boolean deleted) {
        this.buyerID     = buyerID;
        this.date        = date;
        this.title       = title;
        this.description = description;
        this.price       = price;
        this.deleted     = deleted;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private int id;
    private String date;
    private String title;
    private String description;
    private int buyerID;
    private double price;
    private boolean deleted;
}
