
package com.example.authvertical.db_and_models.violations_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Violation {

    @SerializedName("dateAdded")
    @Expose
    private long dateAdded;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fine")
    @Expose
    private Integer fine;
    @SerializedName("__v")
    @Expose
    private Integer v;

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

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "{" +
                "dateAdded=" + dateAdded +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fine=" + fine +
                ", v=" + v +
                '}';
    }
}
