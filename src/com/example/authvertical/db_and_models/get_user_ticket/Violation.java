
package com.example.authvertical.db_and_models.get_user_ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Violation {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fine")
    @Expose
    private Integer fine;

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

}
