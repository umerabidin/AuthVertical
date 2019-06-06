
package com.example.authvertical.db_and_models.get_police_challan_account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPayChallanAccount {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("citizen")
    @Expose
    private String citizen;
    @SerializedName("branch")
    @Expose
    private Branch branch;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountType")
    @Expose
    private String accountType;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("__v")
    @Expose
    private Integer v;

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
