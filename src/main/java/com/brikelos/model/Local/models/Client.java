package com.brikelos.model.Local.models;

public class Client {

    public Client(int id, String name, String phone, double moneySpent, boolean deleted) {
        this.id         = id;
        this.name       = name;
        this.phone      = phone;
        this.moneySpent = moneySpent;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public double getMoneySpent() {
        return this.moneySpent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private int    id;
    private String name;
    private String phone;
    private double moneySpent;
    private boolean deleted;
}
