
package com.example.authvertical.db_and_models.login_entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.authvertical.db_and_models.database.type_converters.PermissionsTypeConverter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "role")
public class Role {
    @TypeConverters({PermissionsTypeConverter.class})
    @SerializedName("permissions")
    @ColumnInfo(name = "permissions")
    private List<String> permissions = null;
    @SerializedName("dateAdded")
    @ColumnInfo(name = "dateAdded")
    private long dateAdded;

    @NonNull
    @PrimaryKey
    @SerializedName("_id")
    @ColumnInfo(name = "_id")
    private String id;
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("bcPermissions")
    @ColumnInfo(name = "bcPermissions")
    private String bcPermissions;

    @TypeConverters({PermissionsTypeConverter.class})
    @SerializedName("streamPermissions")
    @ColumnInfo(name = "streamPermissions")
    private List<String> streamPermissions;

    @SerializedName("__v")
    @ColumnInfo(name = "__v")
    private Integer v;

    public List<String> getPermissions() {
        if (permissions == null)
            permissions = new ArrayList<>();
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBcPermissions() {
        return bcPermissions;
    }

    public void setBcPermissions(String bcPermissions) {
        this.bcPermissions = bcPermissions;
    }

    public List<String> getStreamPermissions() {
        if (streamPermissions == null)
            streamPermissions = new ArrayList<>();
        return streamPermissions;
    }

    public void setStreamPermissions(List<String> streamPermissions) {
        this.streamPermissions = streamPermissions;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
