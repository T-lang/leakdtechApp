package com.leakdtech.maintenanceapp.Utils;

/**
 * Created by LYB OJO on 10/3/2017.
 */

public class Bids {
    private String bid;
    private String bidAmount;
    private String user_id;
    private String date_created;

    public Bids() {
    }

    public Bids(String bid, String bidAmount, String user_id, String date_created) {

        this.bid = bid;
        this.bidAmount = bidAmount;
        this.user_id = user_id;
        this.date_created = date_created;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Bids{" +
                "bid='" + bid + '\'' +
                ", bidAmount='" + bidAmount + '\'' +
                ", user_id='" + user_id + '\'' +
                ", date_created='" + date_created + '\'' +
                '}';
    }
}
