
package com.example.authvertical.db_and_models.get_citizen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCitizenEntity {

    @SerializedName("issueDate")
    @Expose
    private long issueDate;
    @SerializedName("dateAdded")
    @Expose
    private long dateAdded;
    @SerializedName("fingerprints")
    @Expose
    private String fingerprints;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("citizen")
    @Expose
    private CitizenInfo citizen;
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
    private int v;

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

    public String getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(String fingerprints) {
        this.fingerprints = fingerprints;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CitizenInfo getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenInfo citizen) {
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

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}
