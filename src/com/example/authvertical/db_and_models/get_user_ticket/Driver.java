
package com.example.authvertical.db_and_models.get_user_ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("issueDate")
    @Expose
    private long issueDate;
    @SerializedName("dateAdded")
    @Expose
    private long dateAdded;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("citizen")
    @Expose
    private String citizen;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("expiryDate")
    @Expose
    private long expiryDate;
    @SerializedName("bcAddress")
    @Expose
    private String bcAddress;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(long issueDate) {
        this.issueDate = issueDate;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBcAddress() {
        return bcAddress;
    }

    public void setBcAddress(String bcAddress) {
        this.bcAddress = bcAddress;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
