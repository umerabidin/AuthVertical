package com.example.authvertical.db_and_models.database.type_converters;//package com.purelogics.sampleuareu.db.database.type_converters;
//
//import android.arch.persistence.room.TypeConverter;
//
//import com.example.seharfyp.database.entity.Result;
//import com.google.gson.Gson;
//
//public class LocationResultConverter {
//
//
//    @TypeConverter
//    public static String fromRoc(Result geometry) {
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
//    public static Result toGeometry(String rocString) {
//        if (rocString == null) {
//            return (null);
//        }
//        return new Gson().fromJson(rocString, Result.class);
//    }
//
//}
