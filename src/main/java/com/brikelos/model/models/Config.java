package com.brikelos.model.models;

public class Config {

    public Config(double moneyAlert) {
        this.moneyAlert = moneyAlert;
    }


    public double getMoneyAlert() {
        return moneyAlert;
    }

    public void setMoneyAlert(double newMoney) {
        this.moneyAlert = newMoney;
    }

    private double moneyAlert;

}
