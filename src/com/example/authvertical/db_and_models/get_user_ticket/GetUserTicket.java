
package com.example.authvertical.db_and_models.get_user_ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class GetUserTicket {

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
    private Citizen citizen;
    @SerializedName("driver")
    @Expose
    private Driver driver;
    @SerializedName("violation")
    @Expose
    private Violation violation;
    @SerializedName("policeSystemUser")
    @Expose
    private String policeSystemUser;
    @SerializedName("violationDateTime")
    @Expose
    private long violationDateTime;
    @SerializedName("vehicleRegistrationNumber")
    @Expose
    private String vehicleRegistrationNumber;
    @SerializedName("violationLocation")
    @Expose
    private String violationLocation;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("dueDate")
    @Expose
    private long dueDate;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Date getDate() {
        Timestamp ts = new Timestamp(getViolationDateTime());
        Date date = new Date(ts.getTime());
        return date;
    }
    public String getFormatedDate() {
        SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd");
        return fmtOut.format(getDate());
    }

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

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public String getPoliceSystemUser() {
        return policeSystemUser;
    }

    public void setPoliceSystemUser(String policeSystemUser) {
        this.policeSystemUser = policeSystemUser;
    }

    public long getViolationDateTime() {
        return violationDateTime;
    }

    public void setViolationDateTime(long violationDateTime) {
        this.violationDateTime = violationDateTime;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getViolationLocation() {
        return violationLocation;
    }

    public void setViolationLocation(String violationLocation) {
        this.violationLocation = violationLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
