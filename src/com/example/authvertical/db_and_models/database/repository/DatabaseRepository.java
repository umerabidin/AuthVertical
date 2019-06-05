package com.example.authvertical.db_and_models.database.repository;

import android.content.Context;

import com.example.authvertical.db_and_models.database.AppDataBase;
import com.example.authvertical.db_and_models.login_entity.LoginEntity;


public class DatabaseRepository {


    private AppDataBase appDataBase;

    public DatabaseRepository(Context context) {
        appDataBase = AppDataBase.getAppDatabase(context);
    }

    public long storeUser(LoginEntity loginEntity) {
        return appDataBase.getDao().storeUser(loginEntity);
    }


    public LoginEntity getUser(String email) {
        return appDataBase.getDao().getUser(email);
    }
}
