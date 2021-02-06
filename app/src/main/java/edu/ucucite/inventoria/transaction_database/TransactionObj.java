package edu.ucucite.inventoria.transaction_database;

public class TransactionObj {
    private int trans_id;
    private String item_name;
    private String item_count;
    private String trans_type;
    private String date_time;
    private String trans_notes;

    public TransactionObj(int trans_id, String item_name, String item_count, String trans_type, String date_time, String trans_notes) {
        this.trans_id = trans_id;
        this.item_name = item_name;
        this.item_count = item_count;
        this.trans_type = trans_type;
        this.date_time = date_time;
        this.trans_notes = trans_notes;
    }

    @Override
    public String toString() {
        return "TransactionObj{" +
                "trans_id=" + trans_id +
                ", item_name='" + item_name + '\'' +
                ", item_count='" + item_count + '\'' +
                ", trans_type='" + trans_type + '\'' +
                ", date_time='" + date_time + '\'' +
                ", trans_notes='" + trans_notes + '\'' +
                '}';
    }

    public int getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(int trans_id) {
        this.trans_id = trans_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getTrans_notes() {
        return trans_notes;
    }

    public void setTrans_notes(String trans_notes) {
        this.trans_notes = trans_notes;
    }
}
