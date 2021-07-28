package com.brikelos.controller;

import com.brikelos.model.models.Purchase;
import com.brikelos.view.JPurchase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePurchase implements ActionListener {
    public DeletePurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public void actionPerformed(ActionEvent e) {



    }


    private Purchase purchase;
}
