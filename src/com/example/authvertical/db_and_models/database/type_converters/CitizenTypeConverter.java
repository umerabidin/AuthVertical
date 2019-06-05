package com.example.authvertical.db_and_models.database.type_converters;

import android.arch.persistence.room.TypeConverter;

import com.example.authvertical.db_and_models.login_entity.LoginCitizen;
import com.google.gson.Gson;

public class CitizenTypeConverter {

    @TypeConverter
    public static String fromRoc(LoginCitizen geometry) {
        if (geometry == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(geometry);

    }

    @TypeConverter
    public static LoginCitizen toGeometry(String rocString) {
        if (rocString == null) {
            return (null);
        }
        return new Gson().fromJson(rocString, LoginCitizen.class);
    }

}
