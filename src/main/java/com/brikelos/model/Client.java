package com.brikelos.model;

public class Client {

    public Client(String name, int dni, String email, String phone, double moneySpent) {
        this.name       = name;
        this.dni        = dni;
        this.email      = email;
        this.phone      = phone;
        this.moneySpent = moneySpent;
    }

    public Client(int id, String name, int dni, String email, String phone, double moneySpent) {
        this.id         = id;
        this.name       = name;
        this.dni        = dni;
        this.email      = email;
        this.phone      = phone;
        this.moneySpent = moneySpent;
    }

    public Client(String name, int dni, String email, String phone) {
        this.name  = name;
        this.dni   = dni;
        this.email = email;
        this.phone = phone;
    }

    public Client(int id, String name, int dni, String email, String phone) {
        this.id    = id;
        this.name  = name;
        this.dni   = dni;
        this.email = email;
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

    public int getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }

    private int    id;
    private String name;
    private int    dni;
    private String email;
    private String phone;
    private double moneySpent;
}
