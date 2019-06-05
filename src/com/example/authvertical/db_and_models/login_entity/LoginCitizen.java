
package com.example.authvertical.db_and_models.login_entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "citizen")
public class LoginCitizen {

    //    @SerializedName("fingerprints")
//    @Expose
//    private String fingerprints;
    @SerializedName("dateAdded")
    @ColumnInfo(name = "dateAdded")
    private long dateAdded;
    @SerializedName("smsVerified")
    @ColumnInfo(name = "smsVerified")
    private boolean smsVerified;
    @SerializedName("emailVerified")
    @ColumnInfo(name = "emailVerified")
    private boolean emailVerified;

    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    @ColumnInfo(name = "_id")
    private String id;
    @SerializedName("firstName")
    @ColumnInfo(name = "firstName")
    private String firstName;
    @SerializedName("lastName")
    @ColumnInfo(name = "lastName")
    private String lastName;
    @SerializedName("dob")
    @ColumnInfo(name = "dob")
    private long dob;
    @SerializedName("gender")
    @ColumnInfo(name = "gender")
    private String gender;
    @SerializedName("fatherNin")
    @ColumnInfo(name = "fatherNin")
    private String fatherNin;
    @SerializedName("motherNin")
    @ColumnInfo(name = "motherNin")
    private String motherNin;
    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    private String phone;
    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;
    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;
    @SerializedName("bloodGroup")
    @ColumnInfo(name = "bloodGroup")
    private String bloodGroup;
    @SerializedName("maritalStatus")
    @ColumnInfo(name = "maritalStatus")
    private String maritalStatus;
    @SerializedName("bcAddress")
    @ColumnInfo(name = "bcAddress")
    private String bcAddress;
    @SerializedName("permanentAddress")
    @ColumnInfo(name = "permanentAddress")
    private String permanentAddress;
    @SerializedName("currentAddress")
    @ColumnInfo(name = "currentAddress")
    private String currentAddress;
    @SerializedName("__v")
    @ColumnInfo(name = "__v")
    private int v;
    @ColumnInfo(name = "photo")
    @SerializedName("photo")
    private String photo;


    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isSmsVerified() {
        return smsVerified;
    }

    public void setSmsVerified(boolean smsVerified) {
        this.smsVerified = smsVerified;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherNin() {
        return fatherNin;
    }

    public void setFatherNin(String fatherNin) {
        this.fatherNin = fatherNin;
    }

    public String getMotherNin() {
        return motherNin;
    }

    public void setMotherNin(String motherNin) {
        this.motherNin = motherNin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBcAddress() {
        return bcAddress;
    }

    public void setBcAddress(String bcAddress) {
        this.bcAddress = bcAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
