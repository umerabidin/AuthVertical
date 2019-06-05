package com.example.authvertical.db_and_models.database.type_converters;//package com.purelogics.sampleuareu.db.database.type_converters;
//
//import android.arch.persistence.room.TypeConverter;
//
//import com.example.seharfyp.database.entity.Geometry;
//import com.google.gson.Gson;
//
//public class GeometryConverter {
//
//
//    @TypeConverter
//    public static String fromRoc(Geometry geometry) {
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
//    public static Geometry toGeometry(String rocString) {
//        if (rocString == null) {
//            return (null);
//        }
//        return new Gson().fromJson(rocString, Geometry.class);
//    }
//
//}
