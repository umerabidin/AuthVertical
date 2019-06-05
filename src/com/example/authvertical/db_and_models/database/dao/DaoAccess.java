package com.example.authvertical.db_and_models.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.authvertical.db_and_models.login_entity.LoginEntity;


/**
 * Created by Muhammad Umer Abidin on 6/12/17.
 */

@Dao
public interface DaoAccess {


    @Insert
    long storeUser(LoginEntity loginEntity);

    @Update
    int updateUser(LoginEntity loginEntity);


    @Query("Select * from login where email_address==:email_address")
    LoginEntity getUser(String email_address);
}
