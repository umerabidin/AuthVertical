package com.example.authvertical.db_and_models.database.type_converters;//package com.purelogics.sampleuareu.db.database.type_converters;
//
//
//import android.arch.persistence.room.TypeConverter;
//import android.location.Location;
//
//import com.google.gson.Gson;
//
//public class LocationConverter {
//
//
//    @TypeConverter
//    public static String fromRoc(Location geometry) {
//        if (geometry == null) {
//            return null;
//        }
//
//        Gson gson = new Gson();
//        return gson.toJson(geometry);
//
//    }
//
//    @TypeConverter
//    public static Location toGeometry(String rocString) {
//        if (rocString == null) {
//            return (null);
//        }
//        return new Gson().fromJson(rocString, Location.class);
//    }
//
//}
