package com.example.c196carolreid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parts")
public class Part {

    @PrimaryKey(autoGenerate = true)
    private int partID;
    private String partName;
    private double partPrice;
    private int productID;

    public Part(int partID, String partName, double partPrice, int productID) {
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.productID = productID;
    }

    public Part() {
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
