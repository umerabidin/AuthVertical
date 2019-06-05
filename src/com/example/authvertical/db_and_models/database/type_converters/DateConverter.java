package com.example.authvertical.db_and_models.database.type_converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Muhammad Umer on 02.03.2019.
 */

public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
