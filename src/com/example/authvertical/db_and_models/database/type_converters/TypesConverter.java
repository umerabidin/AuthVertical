package com.example.authvertical.db_and_models.database.type_converters;//package com.purelogics.sampleuareu.db.database.type_converters;
//
//import android.arch.persistence.room.TypeConverter;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.Collections;
//import java.util.List;
//
//public class TypesConverter {
//
//
//    private static Gson gson = new Gson();
//
//    @TypeConverter
//    public static List<String> stringToList(String data) {
//        if (data == null) {
//            return Collections.emptyList();
//        }
//
//        Type listType = new TypeToken<List<String>>() {
//        }.getType();
//
//        return gson.fromJson(data, listType);
//    }
//
//    @TypeConverter
//    public static String ListToString(List<String> someObjects) {
//        return gson.toJson(someObjects);
//    }
//
//}
