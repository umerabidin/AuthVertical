
package com.example.authvertical.db_and_models.login_entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.authvertical.db_and_models.database.type_converters.UserTypeConverter;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "login")
public class LoginEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email_address")
    @SerializedName("email_address")
    private String email_address;

    @ColumnInfo(name = "token")
    @SerializedName("token")
    private String token;

    @ColumnInfo(name = "expirate")
    @SerializedName("expirate")
    private long expirate;

    @ColumnInfo(name = "role")
    @SerializedName("role")
    private String role;
    @ColumnInfo(name = "user_role")
    @SerializedName("user_role")
    private String user_role;

    @TypeConverters({UserTypeConverter.class})
    @ColumnInfo(name = "user")
    @SerializedName("user")
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpirate() {
        return expirate;
    }

    public void setExpirate(long expirate) {
        this.expirate = expirate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @NonNull
    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(@NonNull String email_address) {
        this.email_address = email_address;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
