package com.example.authvertical.db_and_models.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.authvertical.db_and_models.database.dao.DaoAccess;
import com.example.authvertical.db_and_models.login_entity.LoginCitizen;
import com.example.authvertical.db_and_models.login_entity.LoginEntity;
import com.example.authvertical.db_and_models.login_entity.Role;
import com.example.authvertical.db_and_models.login_entity.User;


/**
 * Created by Muhammad Umer Abidin on 6/12/17.
 */

@Database(entities = {LoginEntity.class,
        User.class,
        LoginCitizen.class,
        Role.class},
        version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;


    public abstract DaoAccess getDao();


    public synchronized static AppDataBase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class,
                    "auth_vertical")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
