package com.example.authvertical.utils.drawer_items;

import java.util.ArrayList;

public class TabsItems {

    ArrayList<String> tabs;


    public ArrayList<String> getTabs(int position) {
        tabs = new ArrayList<>();


//        roles.add(new UserRoles("1", "Civic"));
//        roles.add(new UserRoles("2", "Police System"));
//        roles.add(new UserRoles("3", "Medical System"));
//        roles.add(new UserRoles("4", "Retailer System"));
//        roles.add(new UserRoles("5", "CitizenInfo Portal"));
        if (position == 1) {
            tabs.add("Add System User");
            tabs.add("Add CitizenInfo");
            tabs.add("View CitizenInfo");
            tabs.add("Birth Certificate");
            tabs.add("Marriage Certificate");
            tabs.add("Death Certificate");

        } else if (position == 2) {
            tabs.add("Add New Officer");
            tabs.add("Verify CitizenInfo");
            tabs.add("Issue Challan");
        } else if (position == 3) {

        } else if (position == 4) {

        } else if (position == 5) {
            tabs.add("Add Doctor");
            tabs.add("Add Patient Visit Info");
            tabs.add("View Patient History");
        }
//        if (position == 1) {
//            tabs.add ( "Add Doctor" );
//            tabs.add ( "Add Patient Visit Info" );
//            tabs.add ( "View Patient History" );
//        } else if (position == 2) {
//            tabs.add ( "Add New Officer" );
//            tabs.add ( "Issue Challan" );
//        } else if (position == 3) {
//            tabs.add ( "Add CitizenInfo" );
//        }
        return tabs;
    }


}
