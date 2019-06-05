package com.example.authvertical.db_and_models.database.type_converters;

import android.arch.persistence.room.TypeConverter;

import com.example.authvertical.db_and_models.login_entity.Role;
import com.google.gson.Gson;

public class RoleTypeConverter {

    @TypeConverter
    public static String fromRoc(Role geometry) {
        if (geometry == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(geometry);

    }

    @TypeConverter
    public static Role toGeometry(String rocString) {
        if (rocString == null) {
            return (null);
        }
        return new Gson().fromJson(rocString, Role.class);
    }

}
