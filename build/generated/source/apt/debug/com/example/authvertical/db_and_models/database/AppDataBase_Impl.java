package com.example.authvertical.db_and_models.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.example.authvertical.db_and_models.database.dao.DaoAccess;
import com.example.authvertical.db_and_models.database.dao.DaoAccess_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDataBase_Impl extends AppDataBase {
  private volatile DaoAccess _daoAccess;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `login` (`email_address` TEXT NOT NULL, `token` TEXT, `expirate` INTEGER NOT NULL, `role` TEXT, `user_role` TEXT, `user` TEXT, PRIMARY KEY(`email_address`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user` (`dateAdded` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `_id` TEXT NOT NULL, `citizen` TEXT, `role` TEXT, `bcAddress` TEXT, `password` TEXT, `__v` INTEGER NOT NULL, PRIMARY KEY(`_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `citizen` (`dateAdded` INTEGER NOT NULL, `smsVerified` INTEGER NOT NULL, `emailVerified` INTEGER NOT NULL, `_id` TEXT NOT NULL, `firstName` TEXT, `lastName` TEXT, `dob` INTEGER NOT NULL, `gender` TEXT, `fatherNin` TEXT, `motherNin` TEXT, `phone` TEXT, `email` TEXT, `password` TEXT, `bloodGroup` TEXT, `maritalStatus` TEXT, `bcAddress` TEXT, `permanentAddress` TEXT, `currentAddress` TEXT, `__v` INTEGER NOT NULL, `photo` TEXT, PRIMARY KEY(`_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `role` (`permissions` TEXT, `dateAdded` INTEGER NOT NULL, `_id` TEXT NOT NULL, `name` TEXT, `bcPermissions` TEXT, `streamPermissions` TEXT, `__v` INTEGER, PRIMARY KEY(`_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"fd629e101857edaa946559b2c1400f76\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `login`");
        _db.execSQL("DROP TABLE IF EXISTS `user`");
        _db.execSQL("DROP TABLE IF EXISTS `citizen`");
        _db.execSQL("DROP TABLE IF EXISTS `role`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsLogin = new HashMap<String, TableInfo.Column>(6);
        _columnsLogin.put("email_address", new TableInfo.Column("email_address", "TEXT", true, 1));
        _columnsLogin.put("token", new TableInfo.Column("token", "TEXT", false, 0));
        _columnsLogin.put("expirate", new TableInfo.Column("expirate", "INTEGER", true, 0));
        _columnsLogin.put("role", new TableInfo.Column("role", "TEXT", false, 0));
        _columnsLogin.put("user_role", new TableInfo.Column("user_role", "TEXT", false, 0));
        _columnsLogin.put("user", new TableInfo.Column("user", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLogin = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLogin = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLogin = new TableInfo("login", _columnsLogin, _foreignKeysLogin, _indicesLogin);
        final TableInfo _existingLogin = TableInfo.read(_db, "login");
        if (! _infoLogin.equals(_existingLogin)) {
          throw new IllegalStateException("Migration didn't properly handle login(com.example.authvertical.db_and_models.login_entity.LoginEntity).\n"
                  + " Expected:\n" + _infoLogin + "\n"
                  + " Found:\n" + _existingLogin);
        }
        final HashMap<String, TableInfo.Column> _columnsUser = new HashMap<String, TableInfo.Column>(8);
        _columnsUser.put("dateAdded", new TableInfo.Column("dateAdded", "INTEGER", true, 0));
        _columnsUser.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0));
        _columnsUser.put("_id", new TableInfo.Column("_id", "TEXT", true, 1));
        _columnsUser.put("citizen", new TableInfo.Column("citizen", "TEXT", false, 0));
        _columnsUser.put("role", new TableInfo.Column("role", "TEXT", false, 0));
        _columnsUser.put("bcAddress", new TableInfo.Column("bcAddress", "TEXT", false, 0));
        _columnsUser.put("password", new TableInfo.Column("password", "TEXT", false, 0));
        _columnsUser.put("__v", new TableInfo.Column("__v", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUser = new TableInfo("user", _columnsUser, _foreignKeysUser, _indicesUser);
        final TableInfo _existingUser = TableInfo.read(_db, "user");
        if (! _infoUser.equals(_existingUser)) {
          throw new IllegalStateException("Migration didn't properly handle user(com.example.authvertical.db_and_models.login_entity.User).\n"
                  + " Expected:\n" + _infoUser + "\n"
                  + " Found:\n" + _existingUser);
        }
        final HashMap<String, TableInfo.Column> _columnsCitizen = new HashMap<String, TableInfo.Column>(20);
        _columnsCitizen.put("dateAdded", new TableInfo.Column("dateAdded", "INTEGER", true, 0));
        _columnsCitizen.put("smsVerified", new TableInfo.Column("smsVerified", "INTEGER", true, 0));
        _columnsCitizen.put("emailVerified", new TableInfo.Column("emailVerified", "INTEGER", true, 0));
        _columnsCitizen.put("_id", new TableInfo.Column("_id", "TEXT", true, 1));
        _columnsCitizen.put("firstName", new TableInfo.Column("firstName", "TEXT", false, 0));
        _columnsCitizen.put("lastName", new TableInfo.Column("lastName", "TEXT", false, 0));
        _columnsCitizen.put("dob", new TableInfo.Column("dob", "INTEGER", true, 0));
        _columnsCitizen.put("gender", new TableInfo.Column("gender", "TEXT", false, 0));
        _columnsCitizen.put("fatherNin", new TableInfo.Column("fatherNin", "TEXT", false, 0));
        _columnsCitizen.put("motherNin", new TableInfo.Column("motherNin", "TEXT", false, 0));
        _columnsCitizen.put("phone", new TableInfo.Column("phone", "TEXT", false, 0));
        _columnsCitizen.put("email", new TableInfo.Column("email", "TEXT", false, 0));
        _columnsCitizen.put("password", new TableInfo.Column("password", "TEXT", false, 0));
        _columnsCitizen.put("bloodGroup", new TableInfo.Column("bloodGroup", "TEXT", false, 0));
        _columnsCitizen.put("maritalStatus", new TableInfo.Column("maritalStatus", "TEXT", false, 0));
        _columnsCitizen.put("bcAddress", new TableInfo.Column("bcAddress", "TEXT", false, 0));
        _columnsCitizen.put("permanentAddress", new TableInfo.Column("permanentAddress", "TEXT", false, 0));
        _columnsCitizen.put("currentAddress", new TableInfo.Column("currentAddress", "TEXT", false, 0));
        _columnsCitizen.put("__v", new TableInfo.Column("__v", "INTEGER", true, 0));
        _columnsCitizen.put("photo", new TableInfo.Column("photo", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCitizen = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCitizen = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCitizen = new TableInfo("citizen", _columnsCitizen, _foreignKeysCitizen, _indicesCitizen);
        final TableInfo _existingCitizen = TableInfo.read(_db, "citizen");
        if (! _infoCitizen.equals(_existingCitizen)) {
          throw new IllegalStateException("Migration didn't properly handle citizen(com.example.authvertical.db_and_models.login_entity.LoginCitizen).\n"
                  + " Expected:\n" + _infoCitizen + "\n"
                  + " Found:\n" + _existingCitizen);
        }
        final HashMap<String, TableInfo.Column> _columnsRole = new HashMap<String, TableInfo.Column>(7);
        _columnsRole.put("permissions", new TableInfo.Column("permissions", "TEXT", false, 0));
        _columnsRole.put("dateAdded", new TableInfo.Column("dateAdded", "INTEGER", true, 0));
        _columnsRole.put("_id", new TableInfo.Column("_id", "TEXT", true, 1));
        _columnsRole.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsRole.put("bcPermissions", new TableInfo.Column("bcPermissions", "TEXT", false, 0));
        _columnsRole.put("streamPermissions", new TableInfo.Column("streamPermissions", "TEXT", false, 0));
        _columnsRole.put("__v", new TableInfo.Column("__v", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRole = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRole = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRole = new TableInfo("role", _columnsRole, _foreignKeysRole, _indicesRole);
        final TableInfo _existingRole = TableInfo.read(_db, "role");
        if (! _infoRole.equals(_existingRole)) {
          throw new IllegalStateException("Migration didn't properly handle role(com.example.authvertical.db_and_models.login_entity.Role).\n"
                  + " Expected:\n" + _infoRole + "\n"
                  + " Found:\n" + _existingRole);
        }
      }
    }, "fd629e101857edaa946559b2c1400f76", "e66a1a24cf7be7c880735e508673a402");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "login","user","citizen","role");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `login`");
      _db.execSQL("DELETE FROM `user`");
      _db.execSQL("DELETE FROM `citizen`");
      _db.execSQL("DELETE FROM `role`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public DaoAccess getDao() {
    if (_daoAccess != null) {
      return _daoAccess;
    } else {
      synchronized(this) {
        if(_daoAccess == null) {
          _daoAccess = new DaoAccess_Impl(this);
        }
        return _daoAccess;
      }
    }
  }
}
