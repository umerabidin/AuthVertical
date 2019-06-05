package com.example.authvertical.utils;

import java.util.ArrayList;

public class DropDownItems {
    public static DropDownItems instance = null;
    ArrayList<Gender> genders;
    ArrayList<Martial> martialStatuses;
    ArrayList<BloodGroup> bloodGroups;

    public synchronized static DropDownItems getInstance() {
        if (instance == null) {
            instance = new DropDownItems();
        }
        return instance;
    }

    public ArrayList<Gender> getGenders() {
//        if (genders == null)
        genders = new ArrayList<> ();

        genders.add ( new Gender( "1" , "Male" ) );
        genders.add ( new Gender( "1" , "Female" ) );
        genders.add ( new Gender( "1" , "Other" ) );
        return genders;
    }

    public ArrayList<Martial> getMartialStatuses() {
//        if (martialStatuses == null)
        martialStatuses = new ArrayList<> ();
        martialStatuses.add ( new Martial( "2" , "Single" ) );
        martialStatuses.add ( new Martial( "2" , "Married" ) );
        martialStatuses.add ( new Martial( "2" , "Widowed" ) );
        martialStatuses.add ( new Martial( "2" , "Divorced" ) );
        return martialStatuses;
    }

    public ArrayList<BloodGroup> getBloodGroups() {
//        if (bloodGroups == null)
        bloodGroups = new ArrayList<> ();
        bloodGroups.add ( new BloodGroup( "3" , "A+" ) );
        bloodGroups.add ( new BloodGroup( "3" , "A-" ) );
        bloodGroups.add ( new BloodGroup( "3" , "AB+" ) );
        bloodGroups.add ( new BloodGroup( "3" , "AB-" ) );
        bloodGroups.add ( new BloodGroup( "3" , "B+" ) );
        bloodGroups.add ( new BloodGroup( "3" , "A-" ) );
        bloodGroups.add ( new BloodGroup( "3" , "O+" ) );
        bloodGroups.add ( new BloodGroup( "3" , "O-" ) );
        return bloodGroups;
    }


}
