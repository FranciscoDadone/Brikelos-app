package com.brikelos.model.models;

import com.brikelos.model.queries.ClientQueries;

public class Client {

    public Client(String name, String phone, double moneySpent) {
        this.name       = name;
        this.phone      = phone;
        this.moneySpent = moneySpent;
    }

    public Client(int id, String name, String phone, double moneySpent) {
        this.id         = id;
        this.name       = name;
        this.phone      = phone;
        this.moneySpent = moneySpent;
    }

    public Client(String name, String phone) {
        this.name  = name;
        this.phone = phone;
    }

    public Client(int id, String name, String phone) {
        this.id    = id;
        this.name  = name;
        this.phone = phone;
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
        return ClientQueries.getTotalSpent(this);
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

    private int    id;
    private String name;
    private String phone;
    private double moneySpent;
}
