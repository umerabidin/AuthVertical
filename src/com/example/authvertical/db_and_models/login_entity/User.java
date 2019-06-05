
package com.example.authvertical.db_and_models.login_entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.authvertical.db_and_models.database.type_converters.CitizenTypeConverter;
import com.example.authvertical.db_and_models.database.type_converters.RoleTypeConverter;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User {

    @ColumnInfo(name = "dateAdded")
    @SerializedName("dateAdded")
    private long dateAdded;

    @ColumnInfo(name = "isActive")
    @SerializedName("isActive")
    private boolean isActive;

//    @ColumnInfo(name = "fingerprints")
//    @SerializedName("fingerprints")
//    private String fingerprints;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    private String id;

    @TypeConverters({CitizenTypeConverter.class})
    @ColumnInfo(name = "citizen")
    @SerializedName("citizen")
    private LoginCitizen citizen;

    @TypeConverters({RoleTypeConverter.class})
    @ColumnInfo(name = "role")
    @SerializedName("role")
    private Role role;

    @ColumnInfo(name = "bcAddress")
    @SerializedName("bcAddress")
    private String bcAddress;

    @ColumnInfo(name = "password")
    @SerializedName("password")
    private String password;

    @ColumnInfo(name = "__v")
    @SerializedName("__v")
    private int v;


    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

//    public String getFingerprints() {
//        return fingerprints;
//    }
//
//    public void setFingerprints(String fingerprints) {
//        this.fingerprints = fingerprints;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoginCitizen getCitizen() {
        return citizen;
    }

    public void setCitizen(LoginCitizen citizen) {
        this.citizen = citizen;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getBcAddress() {
        return bcAddress;
    }

    public void setBcAddress(String bcAddress) {
        this.bcAddress = bcAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
