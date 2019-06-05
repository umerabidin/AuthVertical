package com.example.authvertical.db_and_models.database.type_converters;

import android.arch.persistence.room.TypeConverter;

import com.example.authvertical.db_and_models.login_entity.User;
import com.google.gson.Gson;

public class UserTypeConverter {


    @TypeConverter
    public static String fromRoc(User geometry) {
        if (geometry == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(geometry);

    }

    @TypeConverter
    public static User toGeometry(String rocString) {
        if (rocString == null) {
            return (null);
        }
        return new Gson().fromJson(rocString, User.class);
    }

}
