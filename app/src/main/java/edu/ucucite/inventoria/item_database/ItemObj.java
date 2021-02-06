package edu.ucucite.inventoria.item_database;

import java.io.Serializable;

public class ItemObj implements Serializable {
    private int item_id;
    private String item_name;
    private String item_barcode_str;
    private int item_count;
    private int item_low_count;
    private int price;

    public ItemObj(int item_id, String item_name, String item_barcode_str, int item_count, int item_low_count, int price) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_barcode_str = item_barcode_str;
        this.item_count = item_count;
        this.item_low_count = item_low_count;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemObj{" +
                "item_id=" + item_id +
                ", item_name='" + item_name + '\'' +
                ", item_barcode_str='" + item_barcode_str + '\'' +
                ", item_count=" + item_count +
                ", item_low_count=" + item_low_count +
                '}';
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public int getItem_low_count() {
        return item_low_count;
    }

    public void setItem_low_count(int item_low_count) {
        this.item_low_count = item_low_count;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_barcode_str() {
        return item_barcode_str;
    }

    public void setItem_barcode_str(String item_barcode_str) {
        this.item_barcode_str = item_barcode_str;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
